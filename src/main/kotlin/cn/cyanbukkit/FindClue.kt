package cn.cyanbukkit

import org.bukkit.command.Command
import org.bukkit.command.SimpleCommandMap
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class FindClue : JavaPlugin() {


    companion object {
        lateinit var instance: FindClue
    }

    private fun Command.register() {
        val pluginManagerClazz = instance.server.pluginManager.javaClass
        val field = pluginManagerClazz.getDeclaredField("commandMap")
        field.isAccessible = true
        val commandMap = field.get(instance.server.pluginManager) as SimpleCommandMap
        commandMap.register(this.name, this)
    }

    private fun Listener.register() {
        instance.server.pluginManager.registerEvents(this, instance)
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        GetCommand().register()
        AddItem().register()
        LocationManager.register()
    }
}