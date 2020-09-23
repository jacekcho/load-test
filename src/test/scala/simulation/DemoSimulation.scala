package simulation

import io.gatling.core.Predef._
import scenarios.FirstScenario._
import utils.PropertyReader

import scala.concurrent.duration._

class DemoSimulation extends Simulation {

  private val ramps = PropertyReader.getProperty("ramps", 3)
  private val users = PropertyReader.getProperty("users", 9)
  private val paceDurationMinutes = PropertyReader.getProperty("pace_duration", 2)
  private val rampDurationMinutes = PropertyReader.getProperty("ramp_duration", 1)
  private val usersPerRamp = users / ramps
  private val maxDuration = paceDurationMinutes * ramps + rampDurationMinutes * ramps

  Console.println("Max users: " + users)
  Console.println("Number of ramps: " + ramps)
  Console.println("Users per ramp: " + usersPerRamp)
  Console.println("Single ramp duration in minutes: " + rampDurationMinutes)
  Console.println("Pace duration in minutes: " + paceDurationMinutes)
  Console.println("Max duration in minutes: " + maxDuration)

  setUp(
    firstTestScenario
      .inject(
        (1 to ramps).flatMap
        (i => Seq(
          rampUsers(usersPerRamp) during (rampDurationMinutes minutes),
          nothingFor(paceDurationMinutes minutes)
        ))
      )
  ).maxDuration(maxDuration minutes)

}
