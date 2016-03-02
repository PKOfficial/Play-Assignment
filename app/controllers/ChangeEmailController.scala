package controllers

import models.CustomerService
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangeEmailController extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "newEmail" -> nonEmptyText,
      "repeatEmail" -> nonEmptyText
    ).verifying("Email do not match", data => {data._2 == data._3})
  }

  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.LoginController.showForm())
    else
      Ok(views.html.changeEmail(customerForm, request.session.get("email").get))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.changeEmail(formErrors,request.session.get("email").get))
      },
      customerData => {
        if(customerObj.getCustomer(customerData._1).isDefined) {
          Redirect(routes.AccountInfoController.showForm())
        }
        else{
          Redirect(routes.ChangeEmailController.showForm()).flashing("error" -> "Email is not correct !!")
        }
      }
    )
  }
}
