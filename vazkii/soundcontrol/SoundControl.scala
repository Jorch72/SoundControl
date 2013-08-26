package vazkii.soundcontrol

import cpw.mods.fml.client.registry.KeyBindingRegistry
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.Init
import cpw.mods.fml.common.Mod.PreInit
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge

@Mod(modid = "SoundControl", name = "SoundControl", version = "1.0", modLanguage = "scala")
object SoundControl {

	@PreInit
	def preInit(event : FMLPreInitializationEvent) {
		MinecraftForge.EVENT_BUS.register(SoundManager)
	}
	
	@Init
	def init(event : FMLInitializationEvent) {
		KeyBindingRegistry.registerKeyBinding(new KeybindHandler)
	}
}