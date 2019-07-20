package services

import java.util.{Collections, Properties}

import akka.actor.ActorSystem

import javax.inject.Inject
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class Consumer @Inject()(
  actorSystem: ActorSystem
)(implicit executionContext: ExecutionContext) {

  val Topic = "test"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "group-1")

  val blockingTime = java.time.Duration.ofMillis(500)

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList(Topic))

  /*
   * Kafka consumer.
   * Using Play's scheduler, check kafka for new messages starting 10 seconds
   * from now, every 5 seconds.
   */
  actorSystem.scheduler.schedule(initialDelay = 0.seconds, interval = 5.seconds) {
    val messages = consumer.poll(blockingTime).asScala
    messages foreach println
  }

}
