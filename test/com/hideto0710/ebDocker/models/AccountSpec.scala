package com.hideto0710.ebDocker.models

import scalikejdbc._
import com.hideto0710.ebDocker.utils.TestDBSettings

class AccountSpec extends TestDBSettings {

  val Mail = "test@gmail.com"
  val Password = "testPassword"
  val EncryptedPassword = "fd5cb51bafd60f6fdbedde6e62c473da6f247db271633e15919bab78a02ee9eb"

  override def fixture(implicit session: DBSession) {
    Account.deleteBy(sqls.eq(Account.column.email, Mail))
    Account.createWithAttributes('email -> Mail, 'password -> Password)
  }

  behavior of "Account"
  it should "create with encrypted password" in { implicit session =>
    Account
      .findAllBy(sqls.eq(Account.column.email, Mail))
      .head
      .password should equal(EncryptedPassword)
  }

}
