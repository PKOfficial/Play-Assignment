package controllers

import models.{CustomerServices, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangeEmailController extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText.verifying("Email Not found ", data => {CustomerServices.validateEmail(data)}),
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
        //formErrors.errors.map
        BadRequest(views.html.changeEmail(formErrors,request.session.get("email").get))
      },
      customerData => {
        Redirect(routes.AccountInfoController.showForm())
      }
    )
  }
}
