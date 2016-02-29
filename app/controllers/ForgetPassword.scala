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
class ForgetPassword extends Controller{

  val customerObj = new CustomerService
  val customerForm = Form {
    single(
      "email" -> nonEmptyText
    )
  }

  def showForm = Action{ implicit request =>
    Ok(views.html.forgetpass(customerForm))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.forgetpass(formErrors))
      },
      customerData => {
      val customerexist = customerObj.getCustomer(customerData)
        if(customerData != customerexist.email)
          Redirect(routes.ForgetPassword.showForm()).flashing("error"->"Invalid Email")
        else
          Ok("Go to Login")
      })
  }
}
