package framework

import java.io.File

import com.typesafe.config.ConfigFactory

trait ConfigLoader {
  def parseCustomConfig(defaultConfig: String, filePath: String) = {
    val originalConfig = ConfigFactory.load(defaultConfig)
    val customConfig = new File(filePath)
    val mergedConfig = ConfigFactory.parseFile(customConfig).withFallback(originalConfig)
    mergedConfig
  }
}
