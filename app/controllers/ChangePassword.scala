package controllers

import models.CustomerService
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
  * Created by akash on 29/2/16.
  */
class ChangePassword extends Controller {

  val customerObj = new CustomerService

  val customerForm = Form {
    tuple(
      "email" -> nonEmptyText,
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )
  }

  def showForm = Action{
    Ok("Here Show ChangePassword")
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => { Redirect(routes.SignUp.showForm())},
      customerData => {
        val customerexist = customerObj.getCustomer(customerData._1)
        if(customerData._1 == customerexist.email)
          Redirect(routes.SignUp.showForm())
        else if(customerData._3 != customerData._4 )
          Redirect(routes.SignUp.showForm())
        else
          Ok("Go To Home")
      }
    )
  }

}
