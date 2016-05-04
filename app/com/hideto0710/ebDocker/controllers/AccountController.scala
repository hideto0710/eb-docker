package com.hideto0710.ebDocker.controllers

import com.google.inject.Inject
import com.hideto0710.ebDocker.models.Account
import com.hideto0710.ebDocker.services.AccountService._
import com.hideto0710.ebDocker.services.{ AccountRequest, AccountResponse, AccountsResponse }
import com.hideto0710.ebDocker.utils.Contexts
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.Future
import scala.util.control.Exception.allCatch
import scalikejdbc.DB

class AccountController @Inject() (contexts: Contexts) extends Controller {

  val defaultExecutor = contexts.defaultContext
  val selectExecutor = contexts.simpleDbLookUps
  val createExecutor = contexts.dbWriteOperations

  def index: Action[AnyContent] = Action {
    DB.readOnly { implicit session =>
      val a = Account.findAll().map(a => AccountResponse(a.id, a.email, a.createdAt.toString()))
      Ok(Json.toJson(AccountsResponse(a, a.size)))
    }
  }

  def create: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[AccountRequest].map(a => Future {
      DB.autoCommit { implicit session =>
        allCatch.either {
          Account.createWithAttributes('email -> a.email, 'password -> a.password)
        } match {
          case Right(i) => Ok(Json.obj("id" -> i.toString))
          case Left(e) => Conflict(Json.obj("id" -> e.getMessage))
        }
      }
    }(createExecutor)).recoverTotal(e => Future(BadRequest)(defaultExecutor))
  }

}
