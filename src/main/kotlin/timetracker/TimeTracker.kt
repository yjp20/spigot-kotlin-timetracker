package timetracker

import java.text.DecimalFormat
import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader

@SpigotPlugin
class TimeTracker : JavaPlugin {
	constructor() : super()

	var playTimeMap: MutableMap<String, Long> = HashMap<String, Long>()
	var lastOnlineMap: MutableMap<String, Long> = HashMap<String, Long>()
	var lastCheckTime: Long = 0

	override fun onEnable() {
		logger.info("TimeTracker enabled")
		this.lastCheckTime = System.currentTimeMillis()
		this.load()
		this.getServer().getPluginManager().registerEvents(PlayerListener(this), this)
		this.getCommand("playtime")?.setExecutor(CommandPlayTime(this))
		this.getCommand("lastonline")?.setExecutor(CommandLastOnline(this))
	}

	override fun onDisable() {
		save()
	}

	fun load() {
		val config = this.getConfig()
		try {
			val v = config.getConfigurationSection("playtime")!!.getValues(false)
			this.playTimeMap = v as HashMap<String, Long>
		} catch (e: Exception) {
			logger.info("'playtime' doesn't exist in config or failed to parsed -- creating new object")
		}

		try {
			val v = config.getConfigurationSection("lastonline")!!.getValues(false)
			this.lastOnlineMap = v as HashMap<String, Long>
		} catch (e: Exception) {
			logger.info("'lastonline' doesn't exist in config or failed to parsed -- creating new object")
		}
	}

	fun update() {
		val now = System.currentTimeMillis()
		val diff = now - this.lastCheckTime
		for (player in this.getServer().getOnlinePlayers()) {
			val name = player.getName()
			val playerPlayTime = this.playTimeMap.get(name)
			this.playTimeMap.put(name, if (playerPlayTime != null) diff + playerPlayTime else diff)
			this.lastOnlineMap.put(name, now)
		}
		this.lastCheckTime += diff
	}

	fun save() {
		this.update()
		val config = this.getConfig()
		config.createSection("playtime", playTimeMap)
		config.createSection("lastonline", lastOnlineMap)
	}

	fun getPlayTimesString(): String {
		val sb = StringBuilder()
		for ((key, value) in this.playTimeMap) {
			sb.append(key + ": " + roundTime(value))
			sb.append("\n")
		}
		logger.info(sb.toString())
		return sb.toString()
	}

	fun getLastOnlineString(): String {
		val sb = StringBuilder()
		for ((key, value) in this.lastOnlineMap) {
			sb.append(key + ": " + roundTime(System.currentTimeMillis() - value) + " ago")
			sb.append("\n")
		}
		return sb.toString()
	}
}

val SECOND = 1000
val MINUTE = 60 * SECOND
val HOUR = 60 * MINUTE
val DAY = 24 * HOUR

fun roundTime(v: Long): String {
	val df = DecimalFormat("#.#")
	val d = v.toDouble()
	if (v >= DAY) return df.format(d / DAY) + " days"
	if (v >= HOUR) return df.format(d / HOUR) + " hours"
	if (v >= MINUTE) return df.format(d / MINUTE) + " minutes"
	if (v >= SECOND) return df.format(d / SECOND) + " seconds"
	return "."
}

