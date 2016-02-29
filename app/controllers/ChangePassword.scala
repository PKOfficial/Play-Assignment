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
class ChangePassword extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )
  }

  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.Login.showForm())
    else
      Ok(views.html.changepass(customerForm))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.changepass(formErrors))
      },
      customerData => {
        val customerexist = customerObj.getCustomer(customerData._1)
        if(customerData._1 != customerexist.email || customerData._2 != customerexist.password)
          Redirect(routes.ChangePassword.showForm()).flashing("error"->"Invalid Email or Password")

        else if(customerData._3 != customerData._4 )
          Redirect(routes.ChangePassword.showForm()).flashing("error"->"Password not matched")
        else
          Ok("Go To Home")
      }
    )
  }

}
