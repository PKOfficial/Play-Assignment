package controllers

import models.CustomerService
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
  * Created by akash on 29/2/16.
  */
class ForgetPassword extends Controller{

  val customerObj = new CustomerService
  val customerForm = Form {
    single(
      "email" -> nonEmptyText
    )
  }

  def showForm = Action{
    Ok("Here Show Forget")
  }

  def processForm = Action{ implicit request =>

    customerForm.bindFromRequest.fold(

      formErrors => { Redirect(routes.SignUp.showForm())},
      customerData => {
      val customerexist = customerObj.getCustomer(customerData)
        if(customerData != customerexist.email)
          Redirect(routes.SignUp.showForm())
        else
          Ok("Go to Login")
      })
  }
}
