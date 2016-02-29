import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
/**
  * Created by prabhat on 29/2/16.
  */
class LogoutSpec  extends Specification{

  "LogOut" should {

    "render the Logout page when no session" in new WithApplication {
      val home = route(FakeRequest(GET, "/logout")).get

      status(home) must equalTo(303)
   }
    "render the Logout page with session" in new WithApplication {
      val home = route(FakeRequest(GET, "/logout").withSession("email"->"some@email.com")).get
      status(home) must equalTo(303)
    }
  }
}
