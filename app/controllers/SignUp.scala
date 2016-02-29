package controllers

import models.Customer
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
  * Created by akash on 29/2/16.
  */
class SignUp extends Controller {

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )
  }

  def showForm = Action{

    Ok("Here SignUp")
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => { Redirect(routes.SignUp.showForm())},
      customerData => {
        if(customerData._2 == customerData._3 )
      Redirect(routes.SignUp.showForm())
        else
          Redirect(routes.SignUp.showForm())
  }
    )
  }
}
