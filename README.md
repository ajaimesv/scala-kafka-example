# Scala Kafka Example

This project shows how to use a kafka producer and consumer.

A new kafka message is produced every time we visit `localhost:9000`. (See `HomeController`).

The scala consumer works on its
own thread checking every 5 seconds for new messages. It is implemented using PlayFramework's
scheduler functions. Once a new message is received, it prints it to the screen. (See `Consumer`,
`TasksModule` and `application.conf`).

> **Note:** On this example we run two consumers: A and B. Consumer A is executed from the terminal, and it's part
> of Kafka's files. Consumer B is our scala kafka consumer. It is automatically run when we execute
> `sbt run`.

We have to run kafka before running this application. Instructions for setting up can be found
on [Kafka's Quickstart](https://kafka.apache.org/quickstart) page, and can be summarized as follows:

Start Zookeeper

```
> tar -xzf kafka_2.12-2.2.0.tgz
> cd kafka_2.12-2.2.0
> bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka on a different terminal

```
> bin/kafka-server-start.sh config/server.properties
```

Create a topic called `test`. We use the topic's name in our scala code.

```
> bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```

Check it was created

```
> bin/kafka-topics.sh --list --bootstrap-server localhost:9092
test
```

Send a test message

```
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
This is a message
^D
```

Start consumer A on the terminal. Don't end this program. We'll use it to monitor messages
we send using our scala code.

```
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
This is a message
```

Run our scala code. This will make our producer available on `localhost:9000` and will start our
scala kafka consumer, called B.

```
cd scala-kafka-project/example
sbt run
```

Make a first call from a browser to `localhost:9000` to initialize everything.
Now make a second call. This time you should see on consumer A's output (started in the previous
step) something like this:

```
message 1563656125978
```

And after some seconds in our PlayFramework output, consumer B (scala code) a message like this:

```
ConsumerRecord(topic = test, partition = 0, leaderEpoch = 0, offset = 38, CreateTime = 1563656125981, serialized key size = 3, serialized value size = 21, headers = RecordHeaders(headers = [], isReadOnly = false), key = key, value = message 1563656125978)
```

> **Important:** In my experience, recompiling the code disrupts consumer B, for example, it may stop printing
> results. If this happens, you have to
> stop Play (^C) and start it again (`sbt run`). The reason behind this may be related to the fact that
> this consumer runs using Play's scheduler, but I haven't really looked further into it.
