from kafka import KafkaProducer, KafkaConsumer
from kafka.errors import KafkaError
import json
import time
import random


def main():
    producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    send_topic = 'eDuca_db'
    while True:
        print("Waiting for messages...")
        for i in range(0, 100):
            message = input("Enter message: ")
            producer.send(send_topic, message)
            time.sleep(1)

if __name__ == '__main__':
    main()