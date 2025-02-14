import io.github.cdimascio.dotenv.Dotenv

class Pandium(vararg d: Pair<String, String>) {
    private val configMap = mutableMapOf<String, String>()
    private val secretsMap = mutableMapOf<String, String>()
    private val contextMap = mutableMapOf<String, String>()

    fun getConfigs(): Map<String, String> = configMap.toMap()
    fun getSecrets(): Map<String, String> = secretsMap.toMap()
    fun getContext(): Map<String, String> = contextMap.toMap()

    companion object {
        fun fromEnv(): Pandium {
            val dotenv = Dotenv.configure().ignoreIfMissing().load()

            val pandium = Pandium()

            dotenv.entries().forEach { entry ->
                when {
                    entry.key.startsWith("PAN_CFG_") -> pandium.configMap[entry.key.substringAfter("PAN_CFG_").toCamelCase()] = entry.value
                    entry.key.startsWith("PAN_SEC_") -> pandium.secretsMap[entry.key.substringAfter("PAN_SEC_").toCamelCase()] = entry.value
                    entry.key.startsWith("PAN_CTX_") -> pandium.contextMap[entry.key.substringAfter("PAN_CTX_").toCamelCase()] = entry.value
                }
            }
            return pandium
        }

        private fun String.toCamelCase(): String {
            return this.lowercase().split("_").joinToString("") { it.replaceFirstChar { char -> char.uppercaseChar() } }.replaceFirstChar { it.lowercase() }
        }
    }
}
