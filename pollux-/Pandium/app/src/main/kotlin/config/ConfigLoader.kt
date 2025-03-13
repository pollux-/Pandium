package config

import java.io.InputStream
import java.util.Properties

object ConfigLoader {
    private const val CONFIG_FILE = "/config.properties"
    private val properties: Properties = Properties()

    init {
        val inputStream: InputStream? = this::class.java.getResourceAsStream(CONFIG_FILE)
        inputStream?.use { properties.load(it) } ?: throw RuntimeException("Config file not found")
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}
