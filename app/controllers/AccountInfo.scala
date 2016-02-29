package controllers

import play.api.mvc.{Action, Controller}

/**
  * Created by akash on 29/2/16.
  */
class AccountInfo extends Controller {

  def showForm = Action{

    Ok("Showing Account Info")
  }

}
