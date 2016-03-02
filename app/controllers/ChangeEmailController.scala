package controllers

import models.{CustomerServices, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangeEmailController extends Controller {

  val customerObj = new CustomerService

  /**
    * customerForm is an object of form with tuples
    * email, email and repeatEmail
    * It will verify new Email and repeated Email
    */
  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText.verifying("Email Not found ", data => {CustomerServices.validateEmail(data)}),
      "newEmail" -> nonEmptyText,
      "repeatEmail" -> nonEmptyText
    ).verifying("Email do not match", data => {data._2 == data._3})
  }

  /**
    * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Change Email
    * if session is not present then it will goto login form
    */
  def showForm = Action{implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.LoginController.showForm())
    else
      Ok(views.html.changeEmail(customerForm, request.session.get("email").get))
  }

  /**
    * processForm is an Action taking request implicitly
    * It will process form and if form fails to validate then
    * It will return Bad Request with error form on ChangeEmail view otherwise
    * It will redirect to Home Page
    */
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
