package com.hideto0710.ebDocker.services

import play.api.libs.json.{ __, Reads, Writes }
import play.api.libs.functional.syntax._

case class AccountRequest(email: String, password: String)
case class AccountResponse(id: Long, email: String, createdAt: String)
case class AccountsResponse(accounts: List[AccountResponse], count: Int)

object AccountService {

  implicit val accountWrites: Writes[AccountResponse] = (
    (__ \ "id").write[Long] and
    (__ \ "email").write[String] and
    (__ \ "created_at").write[String]
  )(unlift(AccountResponse.unapply))

  implicit val accountReads: Reads[AccountRequest] = (
    (__ \ "email").read[String] and
    (__ \ "password").read[String]
  )(AccountRequest.apply _)

  implicit val accountsWrites: Writes[AccountsResponse] = (
    (__ \ "accounts").write[List[AccountResponse]] and
    (__ \ "count").write[Int]
  )(unlift(AccountsResponse.unapply))

}
