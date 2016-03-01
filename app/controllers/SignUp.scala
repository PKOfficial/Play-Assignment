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
class SignUp extends Controller {

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )
  }

  def showForm = Action { implicit request =>
    if (request.session.get("email").isDefined)
      Redirect(routes.AccountInfo.showForm())
    else
      Ok(views.html.signup(customerForm))
  }

  def processForm = Action{ implicit request =>
    customerForm.bindFromRequest.fold(
      formErrors => {
        BadRequest(views.html.signup(formErrors))
      },
      customerData => {
        if(customerData._2 != customerData._3 )
        Redirect(routes.SignUp.showForm()).flashing("error"->"Password not matched")
        else
          Redirect(routes.Login.showForm())
      }
    )
  }

}
