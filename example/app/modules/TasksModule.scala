package modules

import play.api.inject.SimpleModule
import play.api.inject._
import services.Consumer

/**
  * Starts the consumer.
  * @see: https://www.playframework.com/documentation/2.7.x/ScheduledTasks#Starting-tasks-when-your-app-starts
  */
class TasksModule extends SimpleModule(bind[Consumer].toSelf.eagerly())
