package models

import scala.concurrent.Future

/**
  * Created by akash on 29/2/16.
  */
case class Customer(email:String,password:String)

trait CustomerServiceApi {

  def getCustomer(email:String): Option[Customer]

}

class CustomerService extends CustomerServiceApi{

  val listOfCustomer:List[Customer] = List(Customer("akash.sethi@knoldus.in","akash"),Customer("prabhat.kashyap@knoldus.in","prabhat"))

  override def getCustomer(email: String): Option[Customer] = {

    def local(list:List[Customer]):Option[Customer] = {
      list match{
        case head::tail if head.email == email => Some(head)
        case head::tail  => local(tail)
        case Nil => None
      }

    }
    local(listOfCustomer)
  }

}