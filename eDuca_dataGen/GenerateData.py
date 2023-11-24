from kafka import KafkaProducer, KafkaConsumer
from kafka.errors import KafkaError
import json
import time
import random



def main():

    
    consumer = KafkaConsumer(bootstrap_servers=['localhost:9092'], auto_offset_reset='earliest', enable_auto_commit=True, group_id='my-group', value_deserializer=lambda x: json.loads(x.decode('utf-8')))

    producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    
    send_topic = 'eDuca_dataGen'
    receive_topic = 'eDuca_db'

    consumer.subscribe([receive_topic])

    while True:
        print("Waiting for messages...")
        for message in consumer:
            print(message.value)
            producer.send(send_topic, message.value)
            


if __name__ == '__main__':
    main()