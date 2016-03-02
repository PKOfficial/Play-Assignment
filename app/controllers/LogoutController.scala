package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

/**
  * Created by akash on 29/2/16.
  */
class LogoutController extends Controller {

  def showForm = Action{ implicit request =>
    if (request.session.get("email").isEmpty)
      Redirect(routes.LoginController.showForm())
    else
      Redirect(routes.LoginController.showForm()).withNewSession
  }
}
