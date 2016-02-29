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
class ChangeEmail extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "newEmail" -> nonEmptyText,
      "repeatEmail" -> nonEmptyText
    )
  }

  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.Login.showForm())
    else
      Ok(views.html.changeEmail(customerForm))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.changeEmail(formErrors))
      },
      customerData => {
        val customerexist = customerObj.getCustomer(customerData._1)
        if(customerData._1 != customerexist.email)
          Redirect(routes.ChangeEmail.showForm()).flashing("error" -> "Email not Found")
        else if(customerData._2 != customerData._3)
          Redirect(routes.ChangeEmail.showForm()).flashing("error"->"Email do not match.")
        else
          Redirect(routes.AccountInfo.showForm())
      }
    )
  }
}
