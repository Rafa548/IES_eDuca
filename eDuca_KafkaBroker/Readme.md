### Running the .yml File with Docker Compose

```bash
$ docker-compose up -d
```

Verify if the services are running correctly:

```bash

$ docker-compose ps
```
Expected output:
```
Name                                    Command            State     Ports
----------------------------------------------------------------
kafka-single-node_kafka_1       /etc/confluent/docker/run   Up
kafka-single-node_zookeeper_1   /etc/confluent/docker/run   Up
```

###Consumer/Producer Execution

Create a Python virtual environment:
```bash
$ python3 -m venv venv
```

Activate the virtual environment:
```bash

$ source venv/bin/activate
```
Install dependencies from requirements.txt:

```bash

$ pip install -r requirements.txt
```

Run the consumer.py and producer.py files

 **Referências**

 [Apache Kafka](https://medium.com/trainingcenter/apache-kafka-codificação-na-pratica-9c6a4142a08f)