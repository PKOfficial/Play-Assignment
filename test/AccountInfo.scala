import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
/**
  * Created by prabhat on 29/2/16.
  */
class AccountInfo  extends Specification{

  "Home Page" should {

    "render the Home page without login" in new WithApplication {
      val homePage = route(FakeRequest(GET, "/")).get
      status(homePage) must equalTo(303)
    }
    "render the Home page with login" in new WithApplication {
      val homePage = route(FakeRequest(GET, "/").withSession("email" -> "some@email.com")).get
      status(homePage) must equalTo(200)
      contentType(homePage) must beSome.which(_ == "text/html")
      contentAsString(homePage) must contain("Home Page")

    }
  }
}
