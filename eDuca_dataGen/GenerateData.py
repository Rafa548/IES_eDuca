from kafka import KafkaProducer, KafkaConsumer
from kafka.errors import KafkaError
import json
import time
import random
import requests


def fetch_data_from_api(api_url):
    try:
        
        response = requests.get(api_url)

        
        if response.status_code == 200:
           
            data = response.json()

            
            process_data_for_initialization(data)
        else:
            print(f"Failed to fetch data from API: {response.status_code}")
    except requests.RequestException as e:
        print(f"Error fetching data from API: {e}")

    
def process_data_for_initialization(data):
    print(data)


def main():

    
    consumer = KafkaConsumer(bootstrap_servers=['localhost:9092'], auto_offset_reset='earliest', enable_auto_commit=True, group_id='my-group', value_deserializer=lambda x: json.loads(x.decode('utf-8')))

    producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    
    send_topic = 'eDuca_dataGen'
    receive_topic = 'eDuca_db'

    consumer.subscribe([receive_topic])

    """ while True:
        print("Waiting for messages...")
        for message in consumer:
            print(message.value)
            producer.send(send_topic, message.value)
     """
    # Initialize the API endpoint
    students_api_url = "http://localhost:8080/students"
    class_api_url = "http://localhost:8080/classes"

    # Fetch data from the API endpoint
    student_data = fetch_data_from_api(students_api_url)
    class_data = fetch_data_from_api(class_api_url)

    if class_data is None:
        sample_classes = [
            {
                "id": 1,
                "classname": "10b",
                "subjects": [],
                "teachers": []
            },
            {
                "id": 2,
                "classname": "10a",
                "subjects": [],
                "teachers": []
            }
        ]

        msg_class = {"type": "init-classes", "data": sample_classes}
        producer.send(send_topic, msg_class)

    if student_data is None:
        sample_students = [
            {
                "nmec": 102360,
                "school": "andiacityschool",
                "studentclass": {
                    "id": 1,
                    "classname": "10b",
                    "subjects": [],
                    "teachers": []
                },
                "id": 1,
                "name": "diogo silva",
                "email": "12@gmail.com",
                "password": "hello"
            },
            {
                "nmec": 1023360,
                "school": "andiacityschool",
                "studentclass": {
                    "id": 1,
                    "classname": "10a",
                    "subjects": [],
                    "teachers": []
                },
                "id": 4,
                "name": "diogo silva",
                "email": "123@gmail.com",
                "password": "hello"
            }
        ]

        msg_student = {"type": "init-students", "data": sample_students}
        producer.send(send_topic, msg_student)

    producer.send(send_topic, {"type": "init-subjects", "data": ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"]})

        

    

    
            


if __name__ == '__main__':
    main()