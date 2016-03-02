package controllers

import models.{Customer, CustomerService}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class LoginController extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Customer.apply)(Customer.unapply)
  }

  def showForm() = Action { implicit request =>
    if (request.session.get("email").isDefined) {
      Ok(views.html.home(request.session.get("email").get))
    }
    else
      Ok(views.html.login(customerForm))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.login(formErrors))
      },
      customerData => {
        if(customerObj.getCustomer(customerData.email).isDefined) {
          val customer = customerObj.getCustomer(customerData.email).get
          if (customer.password == customerData.password)
            Redirect(routes.AccountInfoController.showForm()).withSession("email" -> customer.email)
          else
            Redirect(routes.LoginController.showForm()).flashing("error" -> "Login Failed")
        }
        else{
          Redirect(routes.LoginController.showForm()).flashing("error" -> "Email not found in Database")
        }
      }

    )
  }
}