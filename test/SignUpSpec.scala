import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
/**
  * Created by akash on 29/2/16.
  */
class SignUpSpec extends Specification {

  "Sign Up" should {

    "render the SignUp page" in new WithApplication {
      val signup = route(FakeRequest(GET, "/signup")).get
      status(signup) must equalTo(OK)
      contentType(signup) must beSome.which(_ == "text/html")
      contentAsString(signup) must contain("Sign Up")
    }

    "render the SignUp page with session" in new WithApplication {
      val signup = route(FakeRequest(GET, "/signup").withSession("email" -> "some@email.com")).get
      status(signup) must equalTo(303)
    }

    "Putting the Form using Valid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in", "password" -> "akash","repeatPassword" -> "akash")).get
      status(login) must equalTo(303)
    }
    "Putting the Form using InValid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "akash.sethiknoldus.in", "password" -> "akash","repeatPassword" -> "akash")).get
      status(login) must equalTo(303)
    }
    "Putting the Form using Empty Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "", "password" -> "","" -> "akash")).get
      status(login) must equalTo(400)
    }
  }
}
