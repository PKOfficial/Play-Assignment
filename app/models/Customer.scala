package models

import scala.concurrent.Future

/**
  * Created by akash on 29/2/16.
  */
case class Customer(email:String,password:String)

trait CustomerServiceApi {

  def getCustomer(email:String): Customer
  def updateCustomerEmail(email:String): Boolean
  def updateCustomerPassword(email:String,password:String): Boolean

}

class CustomerService extends CustomerServiceApi{

  val listOfCustomer:List[Customer] = List(Customer("akash.sethi@knoldus.in","akash"),Customer("prabhat.kashyap@knoldus.in","prabhat"))

  override def getCustomer(email: String): Customer = {

    def local(list:List[Customer]):Customer = {
      list match{
        case head::tail if head.email == email => head
        case head::tail  => local(tail)
        case Nil => Customer("","")
      }
    }

    local(listOfCustomer)
  }

  override def updateCustomerEmail(email: String): Boolean = {

     true

  }

  override def updateCustomerPassword(email:String,password:String): Boolean = {

    true

  }
}