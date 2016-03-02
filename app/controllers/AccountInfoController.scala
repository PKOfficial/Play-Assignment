package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class AccountInfoController extends Controller {


  def showForm = Action { implicit request =>
    if (request.session.get("email").isEmpty) {
      Redirect(routes.LoginController.showForm())
    }
    else {
      Ok(views.html.home(request.session.get("email").get))
    }
  }

}
