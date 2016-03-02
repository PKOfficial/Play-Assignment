package controllers

import models.CustomerService
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ChangePasswordController extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )
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
      customerData => {
        if(customerObj.getCustomer(customerData._1).isDefined) {
          val customerExist = customerObj.getCustomer(customerData._1).get
          if (customerData._2 != customerExist.password)
            Redirect(routes.ChangePasswordController.showForm()).flashing("error" -> "Invalid Password")
          else if (customerData._3 != customerData._4)
            Redirect(routes.ChangePasswordController.showForm()).flashing("error" -> "Password not matched")
          else
            Redirect(routes.AccountInfoController.showForm())
        }
        else{
          Redirect(routes.ChangePasswordController.showForm()).flashing("error" -> "Invalid Email Provided")
        }
      }
    )
  }

}
