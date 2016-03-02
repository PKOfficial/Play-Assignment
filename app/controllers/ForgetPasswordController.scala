package controllers

import models.{CustomerServices, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ForgetPasswordController extends Controller{

  val customerObj = new CustomerService

  /**
    * customerForm is an object of form
    * email
    * It will verify email
    */
  val customerForm = Form {
    single(
      "email" -> nonEmptyText.verifying("Invalid Email", data => CustomerServices.validateEmail(data))
    )
  }

  /**
  * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Home Page
  * if session is not present then it will goto forget password form
    */
  def showForm = Action{ implicit request =>
    if (request.session.get("email").isDefined)
      Ok(views.html.home(request.session.get("email").get))
    else
      Ok(views.html.forgetpass(customerForm))
  }

  /**
    * processForm is an Action taking request implicitly
    * It will process form and if form fails to validate then
    * It will return Bad Request with error form on forgetpass view otherwise
    * It will redirect to Login Page with message "Email has been sent with new Password"
    */
  def processForm = Action{ implicit request =>
    customerForm.bindFromRequest.fold(
      formErrors => {
        BadRequest(views.html.forgetpass(formErrors))
      },
      customerData => {
        Redirect(routes.LoginController.showForm()).flashing("success" -> "E-Mail has been sent with new Password")
      })
  }

}
