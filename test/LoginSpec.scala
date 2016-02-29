import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

/**
  * Created by akash on 29/2/16.
  */
@RunWith(classOf[JUnitRunner])
class LoginSpec extends Specification {

  "Login" should {

    "render the login page" in new WithApplication {
      val home = route(FakeRequest(GET, "/login")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain("Login")
    }

    "Login the Form using Valid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in", "password" -> "akash")).get
      status(login) must equalTo(303)
    }

    "Login the Form using InValid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "aksh.sethi@knoldus.in", "password" -> "akash")).get
      status(login) must equalTo(303)
    }

    "Login the Form using Empty Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/submit").withFormUrlEncodedBody("email" -> "", "password" -> "")).get
      status(login) must equalTo(400)
    }

  }
}
