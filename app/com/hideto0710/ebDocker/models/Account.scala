package com.hideto0710.ebDocker.models

import java.security.MessageDigest
import org.joda.time.DateTime
import scalikejdbc._
import skinny.orm
import skinny.orm._

case class Account(id: Long, email: String, password: String, createdAt: DateTime)

object Account extends SkinnyCRUDMapper[Account] {

  override def defaultAlias: orm.Alias[Account] = createAlias("a")

  override def extract(rs: WrappedResultSet, a: ResultName[Account]): Account = new Account(
    id = rs.get(a.id),
    email = rs.get(a.email),
    password = rs.get(a.password),
    createdAt = rs.get(a.createdAt)
  )

  override def createWithAttributes(parameters: (Symbol, Any)*)(implicit s: DBSession): Long = {
    val digestedParams = parameters.map {
      case ('password, v: String) => ('password, digestString(v))
      case other => other
    }
    super.createWithAttributes(digestedParams: _*)
  }

  def authenticate(email: String, password: String)(implicit s: DBSession): Option[Account] = {
    val hashedPassword = digestString(password)
    val a = Account.defaultAlias
    Account.where(sqls.eq(a.email, email).and.eq(a.password, hashedPassword)).apply().headOption
  }

  private def digestString(s: String): String = {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(s.getBytes)
    md.digest.foldLeft("") { (s, b) =>
      s + "%02x".format(if (b < 0) b + 256 else b)
    }
  }
}
