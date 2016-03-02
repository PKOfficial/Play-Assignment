package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class AccountInfoController extends Controller {

  /**
    * showForm is an Action taking request implicitly
    * If email session is present then it will redirect to Home Page
    * if session is not present then it will goto Login
    */
  def showForm = Action { implicit request =>
    if (request.session.get("email").isEmpty) {
      Redirect(routes.LoginController.showForm())
    }
    else {
      Ok(views.html.home(request.session.get("email").get))
    }
  }

}
