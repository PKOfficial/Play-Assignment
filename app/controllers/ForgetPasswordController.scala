package controllers

import models.CustomerService
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class ForgetPasswordController extends Controller{

  val customerObj = new CustomerService
  val customerForm = Form {
    single(
      "email" -> nonEmptyText
    )
  }

  def showForm = Action{ implicit request =>
    if (request.session.get("email").isDefined)
      Ok(views.html.home(request.session.get("email").get))
    else
      Ok(views.html.forgetpass(customerForm))
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => {
        BadRequest(views.html.forgetpass(formErrors))
      },
      customerData => {
        if(customerObj.getCustomer(customerData).isDefined) {
            Redirect(routes.LoginController.showForm()).flashing("success" -> "E-Mail has been sent with new Password")
        }
        else{
          Redirect(routes.ForgetPasswordController.showForm()).flashing("error" -> "Invalid Email")
        }
      })
  }
}
