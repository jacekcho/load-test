package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object FirstScenario {

  val computerIdFeeder = csv("computerId.csv").random

  val headers = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate, br",
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
  )

  val protocol = "http://"
  val homeUrl = "computer-database.gatling.io"

  val firstTestScenario = scenario("Scenario name")
    .forever {
      exec(session => session.reset)
        .exec(flushHttpCache)
        .exec(flushCookieJar)
        .exec(flushSessionCookies)
        .repeat(1) {
          exec(
            http("first request description")
              .get(protocol + homeUrl + "/computers")
              .headers(headers)
              .check(status.in(200, 304))
          )
            .pause(1)
        }
        .repeat(2) {
          feed(computerIdFeeder)
            .exec(
              http("second request description")
                .get(protocol + homeUrl + "/computers/${id}")
                .headers(headers)
                .check(status.in(200, 304))
            )
            .pause(1)
        }
    }
}