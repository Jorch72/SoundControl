package vazkii.soundcontrol

import cpw.mods.fml.client.registry.KeyBindingRegistry
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import cpw.mods.fml.common.event.FMLInitializationEvent

@Mod(modid = "SoundControl", name = "SoundControl", version = "1.0", modLanguage = "scala")
object SoundControl {

	@EventHandler
	def preInit(event : FMLPreInitializationEvent) {
		MinecraftForge.EVENT_BUS.register(SoundManager)
	}
	
	@EventHandler
	def init(event : FMLInitializationEvent) {
		KeyBindingRegistry.registerKeyBinding(new KeybindHandler)
	}
}