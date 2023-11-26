""" from kafka import KafkaProducer, KafkaConsumer
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
    main() """


from kafka import KafkaProducer, KafkaConsumer
from kafka.errors import KafkaError
import json
import time
import random
import string
import requests

student_json = '{"nmec":107360,"email":"@gmail.com","name":"diogo silva","password":"hello","school":"andiacityschool"}'
student_json1 = '{"nmec":1073670,"email":"m@gmail.com","name":"miguel","password":"hello","school":"andiacityschool"}'
student = json.loads(student_json1)

def generate_random_string(length):
    return ''.join(random.choices(string.ascii_lowercase, k=length))

def main():

    students = {}
    teachers = {}
    classes = {}

    
    consumer = KafkaConsumer(bootstrap_servers=['localhost:9092'], auto_offset_reset='earliest', enable_auto_commit=True, group_id='my-group', value_deserializer=lambda x: json.loads(x.decode('utf-8')))

    producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    
    send_topic = 'eDuca_dataGen'
    receive_topic = 'eDucaApp'

    consumer.subscribe([receive_topic])

    num_students = 200
    subject_id = 1

    classes = set()
    used_emails = set()
    class_objects = []
    students = []
    teachers = []
    subjects = ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"]
    subjects_objects = []
    

    for i in range(1, 11):
        for letter in ['a', 'b', 'c', 'd']:
            class_name = f"{i}{letter}"
            if class_name not in classes:
                classes.add(class_name)
                class_obj = {
                    "id": len(class_objects) + 1,
                    "classname": class_name,
                    "school": "SampleSchool",
                    "students": [],
                    "subjects": [],
                    "teachers": []
                }
                class_objects.append(class_obj)

    

    for i in range(1, num_students + 1):
        student_class = random.choice(class_objects)
        email = f"{generate_random_string(8)}@gmail.com"
        while email in used_emails:
            email = f"{generate_random_string(8)}@gmail.com"
        used_emails.add(email)
        student_nmec = 100000 + i
        student = {
            "nmec": student_nmec,
            "school": "SampleSchool",
            "studentclass": student_class,
            "id": i,
            "name": f"{generate_random_string(5)} {generate_random_string(6)}",
            "email": email,
            "password": "hello"
        }
        students.append(student)
    
    for subject in subjects:
        subject_obj = {
            "id": subject_id,
            "name": subject,
            "teacher": "",
            "classes": []
        }
        subject_id += 1
        subjects_objects.append(subject_obj)

    

    for i in range(1, 31):
        random.shuffle(subjects_objects)
        num_classes = random.randint(1, 6)
        num_subjects_assigned = random.randint(1, 3)
        subjects_assigned = subjects_objects[:num_subjects_assigned] 
        classes_assigned = random.sample(class_objects, num_classes)
        email = f"{generate_random_string(8)}@gmail.com"
        while email in used_emails:
            email = f"{generate_random_string(8)}@gmail.com"
        used_emails.add(email)
        teacher = {
            "id": i,
            "name": f"Teacher {i}",
            "email": email,
            "password": "password123", 
            "nmec": 2000000 + i, 
            "school": "SampleSchool",
            "s_classes": classes_assigned,  
            "subjects": subjects_assigned  
        }
        teachers.append(teacher)

    


    while True:
        print("Waiting for messages...")
        for message in consumer:
            print(message.value)
            #producer.send(send_topic, message.value)
            if message.value['type'] == 'init':
                time.sleep(7)
                classes_api_url = "http://localhost:8080/classes"
                current_classes = requests.get(classes_api_url).json()
                for class_ in class_objects:
                    existing_class = next((c for c in current_classes if c['classname'] == class_['classname'] and c["school"] == class_["school"]), None)
                    if existing_class is None:
                        producer.send(send_topic,       
                        {
                            "type": "class",
                            "classname": class_['classname'],
                            "school": class_['school'],
                            "students": class_['students'],
                            "subjects": class_['subjects'],
                            "teachers": class_['teachers']
                        }
                        )
                        print(class_)
                students_api_url = "http://localhost:8080/students"
                current_students = requests.get(students_api_url).json()
                #print(current_students)
                for student in students:
                    existing_student = next((s for s in current_students if s['nmec'] == student['nmec']), None)
                    if existing_student is None:
                        producer.send(send_topic,       
                        {
                            "type": "student",
                            "name": student['name'],
                            "studentclass": student['studentclass'],
                            "nmec": student['nmec'],
                            "email": student['email'],
                            "password": student['password'],
                            "school": student['school']
                        }
                        )
                subjects_api_url = "http://localhost:8080/subjects"
                current_subjects = requests.get(subjects_api_url).json()
                #print(current_subjects)
                for subject in subjects_objects:
                    existing_subject = next((s for s in current_subjects if s['name'] == subject['name']), None)
                    if existing_subject is None:
                        producer.send(send_topic,       
                        {
                            "type": "subject",
                            "name": subject['name'],
                            "teacher": subject['teacher'],
                            #"classes": subject['classes']
                        }
                        )
                
                teachers_api_url = "http://localhost:8080/teachers"
                current_teachers = requests.get(teachers_api_url).json()
                #print(current_teachers)
                for teacher in teachers:
                    existing_teacher = next((t for t in current_teachers if t['nmec'] == teacher['nmec']), None)
                    if existing_teacher is None:
                        producer.send(send_topic,       
                        {
                            "type": "teacher",
                            "name": teacher['name'],
                            "nmec": teacher['nmec'],
                            "email": teacher['email'],
                            "password": teacher['password'],
                            "school": teacher['school'],
                            "s_classes": teacher['s_classes'],
                            "subjects": teacher['subjects']
                        }
                        )
                
            if message.value['type'] == 'update':
                students_api_url = "http://localhost:8080/students"
                class_api_url = "http://localhost:8080/classes"


            


if __name__ == '__main__':
    main()