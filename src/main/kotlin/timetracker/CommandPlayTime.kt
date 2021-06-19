package timetracker

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.Command

class CommandPlayTime : CommandExecutor {
	val plugin: TimeTracker

	constructor(plugin: TimeTracker) {
		this.plugin = plugin
	}

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
		this.plugin.update()
		sender.sendMessage(this.plugin.getPlayTimesString())
		return false
	}
}


