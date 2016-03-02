package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AnyContent, Action, Controller}
import scala.concurrent.Future
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class SignUpController extends Controller {

  /**
    * customerForm is an object of form with tuples
    * email, password and repeatPassword
    */
  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    ).verifying("Password Not Matched", data => {data._2 == data._3})
  }

  /**
    * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Home Page
    * if session is not present then it will goto signup
    *
    * @return
    */

  def showForm = Action { implicit request =>
    if (request.session.get("email").isDefined)
      Redirect(routes.AccountInfoController.showForm())
    else
      Ok(views.html.signup(customerForm))
  }

  def processForm = Action{ implicit request =>
    customerForm.bindFromRequest.fold(
      formErrors => {
        BadRequest(views.html.signup(formErrors))
      },
      customerData => {
        Redirect(routes.LoginController.showForm())
      }
    )
  }

}
