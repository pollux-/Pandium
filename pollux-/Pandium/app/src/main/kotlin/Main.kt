import java.util.logging.Level
import java.util.logging.Logger
import io.github.cdimascio.dotenv.Dotenv

fun main() {
    val logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
    logger.level = Level.ALL
    val dotenv = Dotenv.configure().ignoreIfMissing().load()

    val pandium = Pandium.fromEnv()

    val context = pandium.getContext()
    val secrets = pandium.getSecrets()
    val configs = pandium.getConfigs()

    val runMode = context.get("runMode")

    println("This run is in mode: ${runMode}")

    logger.log(Level.INFO, "------------------------CONFIG------------------------")
    logger.log(Level.INFO, configs.toString())
    logger.log(Level.INFO, "------------------------SECRET------------------------")
    logger.log(Level.INFO, secrets.toString())
    logger.log(Level.INFO, "------------------------CONTEXT------------------------")
    logger.log(Level.INFO, context.toString())
    logger.log(Level.INFO, "------------------------ENV----------------------------")
    logger.log(Level.INFO, dotenv.entries().toString())
}
