package vazkii.soundcontrol

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen

class GuiSoundControl extends GuiScreen {

	var soundList : GuiSoundList = null
	var selected = 0
	
	var slider : GuiCustomSlider = null
	
	def elementClicked(i : Int) {
		selected = i
		if(slider != null)
			slider.sliderValue = SoundManager.soundConfig.get(SoundManager.allSounds.apply(selected)).get
	}
	
	def isSelected(i : Int) = i == selected
	
	override def initGui {
		soundList = new GuiSoundList(this)
		
		slider = new GuiCustomSlider(this, 0, 260, 100, "Volume: %f")
		slider.sliderValue = SoundManager.soundConfig.get(SoundManager.allSounds.apply(selected)).get
		
		buttonList.clear
		buttonList.add(slider)
		buttonList.add(new GuiButton(1, 260, 125, 70, 20, "Mute"))
		buttonList.add(new GuiButton(2, 260, 148, 45, 20, "0.25"))
		buttonList.add(new GuiButton(3, 310, 148, 45, 20, "0.5"))
		buttonList.add(new GuiButton(4, 360, 148, 45, 20, "0.75"))
		buttonList.add(new GuiButton(5, 335, 125, 70, 20, "Reset"))
		buttonList.add(new GuiButton(6, 5, height - 40, 100, 20, "Mute All"))
		buttonList.add(new GuiButton(7, 110, height - 40, 100, 20, "Reset All"))
		buttonList.add(new GuiButton(8, width / 2 - 100, height - 25, 200, 20, "Done"))
	}
	
	override def actionPerformed(button : GuiButton) {
		button.id match {
			case 1 | 2 | 3 | 4 | 5 => { // Sound Control
				val set = ((button.id - 1) * 0.25F)
				slider.sliderValue = set
				SoundManager.soundConfig += (SoundManager.allSounds.apply(selected) -> set)
			}
			case 6 | 7 if(GuiScreen.isShiftKeyDown) => { // Mass Sound Control
				val set = (button.id - 6)
				slider.sliderValue = set
				for(s <- SoundManager.allSounds)
					SoundManager.soundConfig += (s -> set)
			}
			case 8 => { // Done
				mc.displayGuiScreen(null)
				SoundManager.writeConfig
			}
			case _ =>
		}
	}
	
	override def keyTyped(c : Char, i : Int) {
		if(i == 1)
			SoundManager.writeConfig
		
		super.keyTyped(c, i)
	}
	
	override def updateScreen {
		if(slider != null)
			slider.sliderValue = SoundManager.soundConfig.get(SoundManager.allSounds.apply(selected)).get
	}
	
	override def drawScreen(mx : Int, my : Int, ticks : Float) {
		soundList.drawScreen(mx, my, ticks)
		if(!GuiScreen.isShiftKeyDown)
			fontRenderer.drawString("(Must hold SHIFT)", 60, height - 14, 0x777777)
		fontRenderer.drawStringWithShadow("Set volume for " + SoundManager.allSounds.apply(selected), 260, 85, 0x888888)
		drawCenteredString(fontRenderer, "Sound Controller", width / 2, 4, 0x0033FF)
		
		super.drawScreen(mx, my, ticks)
	}
	
}