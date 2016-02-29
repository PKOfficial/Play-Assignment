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
class Login extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Customer.apply)(Customer.unapply)
  }

  def showForm() = Action { implicit request =>
    Ok(views.html.login(customerForm))
  }


  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.login(formErrors))
      },
      customerData => {
        val customer = customerObj.getCustomer(customerData.email)
        if(customer.email == customerData.email && customer.password == customerData.password)
          Ok("Now Here")
        else
        Redirect(routes.Login.showForm()).flashing("error"->"Login Failed")
      }

    )
  }
}