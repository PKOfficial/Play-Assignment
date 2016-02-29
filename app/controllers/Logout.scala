package controllers

import models.{CustomerService, Customer}
import play.api.data.Form
import play.api.data.Forms._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.i18n.Messages.Implicits._
import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.Play.current
import play.api.mvc.{ResponseHeader, Result, Controller, Action}

/**
  * Created by akash on 29/2/16.
  */
class Logout extends Controller {

  def showForm = Action{ implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.Login.showForm())
    else
      Redirect(routes.Login.showForm()).withNewSession
  }
}
