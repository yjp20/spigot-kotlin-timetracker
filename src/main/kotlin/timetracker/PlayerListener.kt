package timetracker

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerListener : Listener {
	val plugin: TimeTracker

	constructor (plugin: TimeTracker) {
		this.plugin = plugin
	}

	@EventHandler
	fun onJoin(e: PlayerJoinEvent) {
		this.plugin.update()
	}

	@EventHandler
	fun onQuit(e: PlayerQuitEvent) {
		this.plugin.update()
	}
}


