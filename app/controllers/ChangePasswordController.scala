package controllers

import models.{CustomerServices, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangePasswordController extends Controller {

  val customerObj = new CustomerService

  /**
    * customerForm is an object of form with tuples
    * email, oldPassword/Current Password, New Password that you want to add
    * and repeat password
    * It will verify new password and repeated password and also checks for
    * email to be valid
    */
  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText.verifying("Email Not found ", data => {CustomerServices.validateEmail(data)}),
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    ).verifying("Password is not matched with Email",data => {CustomerServices.validatePassword(data._1,data._2)}).verifying("Password Not Matched", data => {data._3 == data._4})
  }

  /**
    * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Change Password
    * if session is not present then it will goto login form
    */
  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.LoginController.showForm())
    else
      Ok(views.html.changepass(customerForm, request.session.get("email").get))
  }

  /**
    * processForm is an Action taking request implicitly
    * It will process form and if form fails to validate then
    * It will return Bad Request with error form on ChangePass view otherwise
    * It will redirect to Home Page
    */
  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.changepass(formErrors, request.session.get("email").get))
      },
      customerData => Redirect(routes.AccountInfoController.showForm())
    )
  }

}
