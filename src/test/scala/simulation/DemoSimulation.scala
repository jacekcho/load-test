package simulation

import io.gatling.core.Predef._
import scenarios.FirstScenario._

import scala.concurrent.duration._

class DemoSimulation extends Simulation {

  val ramps = Integer.parseInt(System.getProperty("ramps", "2"))
  val users = Integer.parseInt(System.getProperty("users", "8"))
  val paceDurationMinutes = Integer.parseInt(System.getProperty("pace_duration", "2"))
  val rampDurationMinutes = Integer.parseInt(System.getProperty("ramp_duration", "1"))

  val usersPerRamp = users / ramps
  val maxDuration = paceDurationMinutes * ramps + rampDurationMinutes * ramps

  Console.println("Max users: " + users)
  Console.println("Number of ramps: " + ramps)
  Console.println("Users per ramps: " + usersPerRamp)
  Console.println("Single ramp duration in minutes: " + rampDurationMinutes)
  Console.println("Pace duration in minutes: " + paceDurationMinutes)

  setUp(
    firstTestScenario
      .inject(
        rampUsers(usersPerRamp) during (rampDurationMinutes minutes),
        nothingFor(paceDurationMinutes minutes),

        rampUsers(usersPerRamp) during (rampDurationMinutes minutes),
        nothingFor(paceDurationMinutes minutes)
      )
  ).maxDuration(maxDuration minutes)

}
