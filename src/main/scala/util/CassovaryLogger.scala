package util
import java.util.logging

import com.twitter.logging.{Level, _}
import util.Env.{PRODUCTION, TEST}

sealed abstract class Env(val name: String)
object Env {
  case object TEST extends Env("test")
  case object PRODUCTION extends Env("production")
}

object CassovaryLogger {
  def setUp(env: Env) = {
    val factory = env match {
      case TEST => {
        LoggerFactory(
          node = "",
          level = Some(Level.OFF),
          handlers = List()
        )
      }
      case PRODUCTION => {
        LoggerFactory(
          node = "",
          level = Some(Level.INFO),
          handlers = List(
            FileHandler(
              filename = "production.log",
              rollPolicy = Policy.SigHup
            )
          )
        )
      }
    }

    factory()
  }
}
