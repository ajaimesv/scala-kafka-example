package controllers

import java.util.Properties

import javax.inject._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api._
import play.api.mvc._

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Post a kafka message every time we visit this page.
    * Kafka interaction can be seen in the kafka console.
    *
    * @return
    */
  def index() = Action { implicit request: Request[AnyContent] =>

    val  props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val Topic = "test"
    val timestamp = System.currentTimeMillis

    val record = new ProducerRecord(Topic, "key", s"message $timestamp")
    producer.send(record)

    producer.close()

    Ok("ok")
  }

}
