package vazkii.soundcontrol

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagFloat
import net.minecraftforge.client.event.sound.PlaySoundEffectEvent
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.client.event.sound.SoundLoadEvent
import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.EventPriority

object SoundManager {

	var soundConfig : Map[String, Float] = Map()
	var allSounds : List[String] = List()
	
	var loadedInitialConfig = false
	
	var listen = true
	
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	def soundRegistered(event : SoundLoadEvent) {
 		val field = event.manager.soundPoolSounds.getClass.getDeclaredFields.apply(1)
 		field.setAccessible(true)
 		val obj = field.get(event.manager.soundPoolSounds)
 		
 		obj match {
 			case map : java.util.Map[_, _] => {
 				for(s : String <- map.keySet.asInstanceOf[java.util.Set[String]].toArray(Array[String]())) {
 					allSounds :+= s
 					soundConfig += (s -> 1.0F)
 				}
 			} 
 			case _ => println("This shouldn't happen!")
 		}
	}
		
	@ForgeSubscribe
	def soundEffectPlayed(event : PlaySoundEffectEvent) {
		if(!loadedInitialConfig) {
			allSounds = allSounds.sorted
		
			loadConfig
			writeConfig
			
			loadedInitialConfig = true
		}
	}
	
	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	def soundPlayed(event : PlaySoundEvent) {
		if(!listen) {
			listen = true
			return
		}
		
		if(!soundConfig.contains(event.name))
			return
		
		event.result = null
		listen = false
		event.manager.playSound(event.name, event.x, event.y, event.z, event.volume * soundConfig.get(event.name).get, event.pitch)
	}
	
	def writeConfig {
		val f = new File(".", "soundcontrol.dat")
		if(!f.exists)
			f.createNewFile
			
		val cmp : NBTTagCompound = new NBTTagCompound
		
		for(s <- soundConfig.keySet)
			cmp.setFloat(s, soundConfig.get(s).get)
			
		CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f))
	}
	
	def loadConfig {
		val f = new File(".", "soundcontrol.dat")
		if(!f.exists)
			return
			
		soundConfig.empty
			
		val cmp = CompressedStreamTools.readCompressed(new FileInputStream(f))
		for(tag <- cmp.getTags.toArray(Array[NBTBase]())) {
			tag match {
				case tag_fl : NBTTagFloat => soundConfig += (tag_fl.getName -> tag_fl.data) 
				case _ => println("Found illegal tag: " + tag)
			}
		}
	}
}