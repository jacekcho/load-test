package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object StandardUser {

  val userAgentsFeeder = csv("userAgents.csv").random

  val headers = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate, br",
    "User-Agent" -> "${UserAgent}"
  )

  val protocol = "http://"
  val homeUrl = "computer-database.gatling.io"

  val testScenario = scenario("Scenario name")
    .forever {
      exec(session => session.reset)
        .exec(flushHttpCache)
        .exec(flushCookieJar)
        .exec(flushSessionCookies)
        .feed(userAgentsFeeder)
        .repeat(1) {
          exec(
            http("first request description")
              .get(protocol + homeUrl + "/computers")
              .headers(headers)
              .check(status.in(200, 304))
          )
            .pause(1)
        }
        .repeat(3) {
          exec(
            http("second request description")
              .get(protocol + homeUrl + "/computers/381")
              .headers(headers)
              .check(status.in(200, 304))
          )
            .pause(1)
        }
    }
}