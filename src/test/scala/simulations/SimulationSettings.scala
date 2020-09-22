package simulations

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep

import scala.concurrent.duration._

trait SimulationSettings extends Simulation {

  val RAMPS = 2
  val maxUsers = 8
  val paceDurationMinutes = 2
  val rampDurationMinutes = 1

  def rampProfile(users: Int) : Iterable[InjectionStep] =
    List(
      rampUsers(users / RAMPS) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(users / RAMPS) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes)
    )

}