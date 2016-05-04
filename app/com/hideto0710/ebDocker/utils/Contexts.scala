package com.hideto0710.ebDocker.utils

import akka.actor.ActorSystem
import com.google.inject.Inject
import javax.inject.Singleton
import scala.concurrent.ExecutionContext

@Singleton
class Contexts @Inject() (system: ActorSystem) {
  val defaultContext: ExecutionContext = ExecutionContext.Implicits.global
  val simpleDbLookUps: ExecutionContext = system.dispatchers.lookup("contexts.simple-db-lookups")
  val expensiveDbLookUps: ExecutionContext = system.dispatchers.lookup("contexts.expensive-db-lookups")
  val dbWriteOperations: ExecutionContext = system.dispatchers.lookup("contexts.db-write-operations")
  val expensiveCpuOperations: ExecutionContext = system.dispatchers.lookup("contexts.expensive-cpu-operations")
}
