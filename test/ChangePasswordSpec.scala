import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import play.api.mvc.RequestHeader
/**
  * Created by prabhat on 29/2/16.
  */
class ChangePasswordSpec extends Specification {

  "Change Password" should {

    "render the ChangePassword page" in new WithApplication {

      val change = route(FakeRequest(GET, "/changepass").withSession("email" -> "some@email.com")).get
      status(change) must equalTo(OK)
      contentType(change) must beSome.which(_ == "text/html")
      contentAsString(change) must contain("Change Password")
    }
    "Putting the Form using Valid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/changepassword").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in","oldPassword" ->"prabhat","newPassword" ->"akash","repeatPassword" -> "akash")).get
      status(login) must equalTo(303)
    }
    "Putting the Form using InValid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/changepassword").withFormUrlEncodedBody("email" -> "akash.sethiknoldus.in","oldPassword" ->"prabhat","newPassword" ->"akash","repeatPassword" -> "akash")).get
      status(login) must equalTo(303)
    }
    "Putting the Form using Empty Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/changepassword").withFormUrlEncodedBody("email" -> "","oldPassword" ->"","newPassword" ->"","repeatPassword" -> "")).get
      status(login) must equalTo(400)
    }
  }
}