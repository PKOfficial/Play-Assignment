package controllers

import models.{CustomerServices, Customer, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class LoginController extends Controller {

  val customerObj = new CustomerService

  /**
    * customerForm is an object of form
    * email and Password
    * It will verify email and password
    */
  val customerForm = Form {
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Customer.apply)(Customer.unapply).verifying("Login Failed",data => {CustomerServices.validatePassword(data.email,data.password)})
  }

  /**
    * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Home Page
    * if session is not present then it will goto login form
    */
  def showForm() = Action { implicit request =>
    if (request.session.get("email").isDefined) {
      Ok(views.html.home(request.session.get("email").get))
    }
    else
      Ok(views.html.login(customerForm))
  }

  /**
    * processForm is an Action taking request implicitly
    * It will process form and if form fails to validate then
    * It will return Bad Request with error form on Login view otherwise
    * It will redirect to Home Page with session of email as Email of user
    */
  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.login(formErrors))
      },
      customerData => {
        Redirect(routes.AccountInfoController.showForm()).withSession("email" -> customer.email)
      }

    )
  }
}