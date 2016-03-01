import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
/**
  * Created by prabhat on 29/2/16.
  */
class ForgetPasswordSpec extends Specification {

  "Forget Password" should {

    "render the ForgetPassword page" in new WithApplication {
      val signup = route(FakeRequest(GET, "/forget")).get
      status(signup) must equalTo(OK)
      contentType(signup) must beSome.which(_ == "text/html")
      contentAsString(signup) must contain("Forget Password")
    }

    "render the ForgetPassword page with Sessions" in new WithApplication {
      val signup = route(FakeRequest(GET, "/forget").withSession("email" -> "some@email.com")).get
      status(signup) must equalTo(OK)
      contentType(signup) must beSome.which(_ == "text/html")
      contentAsString(signup) must contain("Home Page")
    }

    "Putting the Form using Valid Fields" in new WithApplication {
      val login = route(FakeRequest(POST, "/forgetpass").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in")).get
      status(login) must equalTo(303)
    }

    "Putting the Form using InValid Fields" in new WithApplication {
      val login = route(FakeRequest(POST, "/forgetpass").withFormUrlEncodedBody("email" -> "akash.sethiknoldus.in")).get
      status(login) must equalTo(303)
    }

    "Putting the Form using Empty Fields" in new WithApplication {
      val login = route(FakeRequest(POST, "/forgetpass").withFormUrlEncodedBody("email" -> "")).get
      status(login) must equalTo(400)
    }
  }
}
