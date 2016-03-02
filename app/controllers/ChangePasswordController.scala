package controllers

import models.{CustomerServices, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangePasswordController extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText.verifying("Email Not found ", data => {CustomerServices.validateEmail(data)}),
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    ).verifying("Password is not matched with Email",data => {CustomerServices.validatePassword(data._1,data._2)}).verifying("Password Not Matched", data => {data._3 == data._4})
  }

  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.LoginController.showForm())
    else
      Ok(views.html.changepass(customerForm, request.session.get("email").get))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.changepass(formErrors, request.session.get("email").get))
      },
      customerData => Redirect(routes.AccountInfoController.showForm())
    )
  }

}
