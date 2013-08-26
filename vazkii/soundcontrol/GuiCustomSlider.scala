package vazkii.soundcontrol

import org.lwjgl.input.Mouse

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiSlider
import net.minecraft.client.settings.EnumOptions

class GuiCustomSlider(parent : GuiSoundControl, i : Int, x : Int, y : Int, constString : String) extends GuiSlider(i, x, y, EnumOptions.ANAGLYPH, constString, 0F) {

	override def mouseDragged(mc : Minecraft, mx : Int, my : Int) {
		super.mouseDragged(mc, mx, my)
		displayString = constString.format(sliderValue)
		SoundManager.soundConfig += (SoundManager.allSounds.apply(parent.selected) -> sliderValue)
		
		if(!Mouse.isButtonDown(0))
			dragging = false
	}
	
	override def mousePressed(mc : Minecraft, mx : Int, my : Int) : Boolean = {
		val retr = super.mousePressed(mc, mx, my)

		displayString = constString.format(sliderValue)
		SoundManager.soundConfig += (SoundManager.allSounds.apply(parent.selected) -> sliderValue)
		
		retr
	}
}