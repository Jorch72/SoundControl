package vazkii.soundcontrol

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiSlot
import net.minecraft.client.renderer.Tessellator

class GuiSoundList(parent : GuiSoundControl) extends GuiSlot(Minecraft.getMinecraft, 250, parent.height, 16, parent.height - 48, 24) {

	override def getSize = SoundManager.allSounds.size
	
	override def getContentHeight = getSize * 24
	
	override def elementClicked(i : Int, b : Boolean) = parent.elementClicked(i)
	
	override def isSelected(i : Int) = parent.isSelected(i)
	
	override def drawSlot(i : Int, x : Int, y : Int, l : Int, tess : Tessellator) {
		val s = SoundManager.allSounds.apply(i)
		val font = Minecraft.getMinecraft.fontRenderer
		
		font.drawStringWithShadow(s, x + 3, y + 1, 0xFFFFFF)
		font.drawStringWithShadow("" + SoundManager.soundConfig.get(s).get, x + 6, y + 12, 0x777777)
	}
	
	override def drawBackground = parent.drawBackground(0)
}