package simulations

import io.gatling.core.Predef._
import scala.concurrent.duration._
import scenarios.StandardUser._


class Demo extends Simulation with SimulationSettings {

  setUp(
    testScenario
      .inject(rampProfile(maxUsers))
  ).maxDuration((paceDurationMinutes * RAMPS + rampDurationMinutes * RAMPS) minutes)

}
