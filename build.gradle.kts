import kr.entree.spigradle.data.Load
import kr.entree.spigradle.kotlin.*

version = "1.17"

plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version "7.0.0"
	id("kr.entree.spigradle") version "2.2.4"
	id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

repositories {
	spigotmc()
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compileOnly(spigot("1.17"))
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(14))
	}
}

spigot {
	version = "1.17"
	description = "Time tracker plugin"
	load = Load.STARTUP
	commands {
		create("lastonline") {
			permission = "timetracker.lastonline"
		}
		create("playtime") {
			permission = "timetracker.playtime"
		}
	}
	permissions {
		create("timetracker.lastonline") {
			description = "Allows last online command"
			defaults = "true"
		}
		create("timetracker.playtime") {
			description = "Allows play time command"
			defaults = "true"
		}
	}
}
