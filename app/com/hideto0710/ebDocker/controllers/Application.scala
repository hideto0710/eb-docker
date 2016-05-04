package com.hideto0710.ebDocker.controllers

import com.google.inject.Inject
import com.hideto0710.ebDocker.models.Account
import com.hideto0710.ebDocker.services.{ AccountRequest, AccountResponse }
import com.hideto0710.ebDocker.services.AccountService._
import com.hideto0710.ebDocker.utils.Contexts
import org.slf4j.LoggerFactory
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.Future
import scalikejdbc.DB

class Application @Inject() (contexts: Contexts) extends Controller {

  private val logger = LoggerFactory.getLogger("com.hideto0710.ebDocker.controllers.Application")
  implicit val executor = contexts.simpleDbLookUps

  def auth: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[AccountRequest].map(a => Future {
      DB.readOnly { implicit session =>
        Account.authenticate(a.email, a.password) match {
          case Some(ac) => Ok(Json.toJson(AccountResponse(ac.id, ac.email, ac.createdAt.toString())))
          case None => NotFound
        }
      }
    }).recoverTotal(e => Future {
      logger.debug("Bad Request. Reason: {}", e.errors.toString())
      BadRequest
    })
  }

}
