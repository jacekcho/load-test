package simulation

import io.gatling.core.Predef._
import scenarios.FirstScenario._

import scala.concurrent.duration._

class DemoSimulation extends Simulation {

  val RAMPS = 2
  val maxUsers = 8
  val paceDurationMinutes = 2
  val rampDurationMinutes = 1

  setUp(
    firstTestScenario
      .inject(
        rampUsers(maxUsers / RAMPS) during (rampDurationMinutes minutes),
        nothingFor(paceDurationMinutes minutes),

        rampUsers(maxUsers / RAMPS) during (rampDurationMinutes minutes),
        nothingFor(paceDurationMinutes minutes)
      )
  ).maxDuration((paceDurationMinutes * RAMPS + rampDurationMinutes * RAMPS) minutes)

}
