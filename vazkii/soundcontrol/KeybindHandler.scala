package vazkii.soundcontrol

import java.util.EnumSet

import org.lwjgl.input.Keyboard

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler
import cpw.mods.fml.common.TickType
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding

class KeybindHandler(key : KeyBinding) extends KeyHandler(Array(key), Array(true)) {
	
	def this() = this(new KeyBinding("SoundControl", Keyboard.KEY_F12))

	override def keyUp(tickType : EnumSet[TickType], key : KeyBinding, tickEnd : Boolean) {
		Minecraft.getMinecraft.sndManager.playSoundFX("random.button", 1F, 1F)
		Minecraft.getMinecraft.displayGuiScreen(new GuiSoundControl)
	}
	
	override def keyDown(tickType : EnumSet[TickType], key : KeyBinding, tickEnd : Boolean, repeat : Boolean) {}
	
	override def ticks() : EnumSet[TickType] = EnumSet.of(TickType.CLIENT)

	override def getLabel() : String = "SoundControl"
	
}