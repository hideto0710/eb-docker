package com.hideto0710.ebDocker.utils

import com.typesafe.config.ConfigFactory
import org.scalatest.{ Matchers, fixture }
import scalikejdbc.ConnectionPool
import scalikejdbc.scalatest.AutoRollback

trait TestDBSettings extends fixture.FlatSpec with Matchers with AutoRollback {

  def loadJDBCSettings() {
    val config = ConfigFactory.load()
    val url = config.getString("db.default.url")
    val user = config.getString("db.default.username")
    val password = config.getString("db.default.password")
    ConnectionPool.singleton(url, user, password)
  }

  loadJDBCSettings()

}
