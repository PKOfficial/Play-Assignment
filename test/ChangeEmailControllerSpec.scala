import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import play.api.mvc.RequestHeader
/**
  * Created by prabhat on 29/2/16.
  */
class ChangeEmailControllerSpec extends Specification {

  "Change Password" should {

    "render the ChangePassword page without Sessions" in new WithApplication {
      val change = route(FakeRequest(GET, "/changeemail")).get
      status(change) must equalTo(303)
    }

    "render the ChangePassword page with Sessions" in new WithApplication {
      val change = route(FakeRequest(GET, "/changeemail").withSession("email" -> "some@email.com")).get
      status(change) must equalTo(OK)
      contentType(change) must beSome.which(_ == "text/html")
      contentAsString(change) must contain("Change Email")
    }

    "Putting the Form using Valid Fields" in new WithApplication {
      val login = route(FakeRequest(POST, "/changemail").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in","newEmail" ->"akash@gmail.com","repeatEmail" ->"akash@gmail.com")).get
      status(login) must equalTo(303)
    }

    "Putting the Form using InValid Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/changemail").withFormUrlEncodedBody("email" -> "akash.sethiknoldus.in","newEmail" ->"akash@gmail.com","repeatEmail" ->"akash@gmail.com")).get
      status(login) must equalTo(303)
    }

    "Putting the Form with error Email not matched" in new WithApplication {

      val login = route(FakeRequest(POST, "/changemail").withFormUrlEncodedBody("email" -> "akash.sethi@knoldus.in","newEmail" ->"akash@gmail.com","repeatEmail" ->"prabhat@gmail.com")).get
      status(login) must equalTo(303)
    }

    "Putting the Form using Empty Fields" in new WithApplication {

      val login = route(FakeRequest(POST, "/changemail").withFormUrlEncodedBody("email" -> "","newEmail" ->"","repeatEmail" ->"")).get
      status(login) must equalTo(400)
    }

  }

}
