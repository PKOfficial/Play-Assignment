package models

/**
  * Created by prabhat on 2/3/16.
  */
object CustomerServices {

  def validatePassword(email:String, password:String):Boolean={
    val customerObj = new CustomerService
    if(validateEmail(email)) {
      val customerExist = customerObj.getCustomer(email).get
      if (password == customerExist.password) true
      else false
    }
    else false
  }

  def validateEmail(email:String): Boolean ={
    val customerObj = new CustomerService
    customerObj.getCustomer(email).isDefined
  }

}
