from kafka import KafkaProducer, KafkaConsumer
from kafka.errors import KafkaError
import json
import time
import random
import string
import requests


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
    assignment_id = 1

    classes = set()
    used_emails = set()
    class_objects = []
    students = []
    teachers = []
    subjects = ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"]
    subjects_objects = []
    teacher_assignment = []
    

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
    
    for subject in subjects:
        subject_obj = {
            "id": subject_id,
            "name": subject,
            "teachers": [],
            "classes": []
        }
        subject_id += 1
        subjects_objects.append(subject_obj)


    assigned_subjects = {teacher_id: set() for teacher_id in range(1, 31)}

    for class_ in class_objects:
        for subject in subjects_objects:
            teacher_id = random.randint(1, 30)
            while len(assigned_subjects[teacher_id]) >= 3 and subject["id"] not in assigned_subjects[teacher_id]:
                teacher_id = random.randint(1, 30)
            
            teacher_assignment.append({
                            "id": assignment_id,
                            "teacher_id": teacher_id,
                            "assigned_class": class_,
                            "assigned_subject": subject
                        })
            assignment_id += 1
            assigned_subjects[teacher_id].add(subject["id"])
            
            
            
            
            


    """ for assignment in teacher_assignment:
        print(f"Assignment ID: {assignment['id']}")
        print(f"Teacher ID: {assignment['teacher_id']}")
        print("Assigned Subject:")
        print(assignment['assigned_subject']['name'])
        print("Assigned Class:")
        print(assignment['assigned_class']['classname'])
        print("--------------------")
    
    print(len(class_objects)) """

    if len(assigned_subjects[subject["id"]]) < 3:
                teacher_ids = set()
                for teacher_id in range(1, 31):
                    # Check if the teacher can be assigned this subject and this class
                    if (len(assigned_subjects[teacher_id]) < 3 and 
                        teacher_id not in teacher_ids and 
                        subject["id"] not in assigned_subjects[teacher_id]):
                        
                        teacher_assignment.append({
                            "id": assignment_id,
                            "teacher_id": teacher_id,
                            "assigned_class": class_,
                            "assigned_subject": subject
                        })
                        assignment_id += 1
                        assigned_subjects[subject["id"]].add(teacher_id)
                        assigned_subjects[teacher_id].add(subject["id"])
                        teacher_ids.add(teacher_id)
                        break

    for i in range(1, 31):
        #random.shuffle(subjects_objects)
        #num_classes = random.randint(1, 6)
        #num_subjects_assigned = random.randint(1, 3)
        #subjects_assigned = subjects_objects[:num_subjects_assigned] 
        assigned_subjects = []
        assigned_classes = []
        assigned_assigments = []
        for assignment in teacher_assignment: 
            #print(assignment)
            #print("--------------------")
            if assignment["teacher_id"] == i:
                assigned_classes = [assignment["assigned_class"]]
                assigned_subjects = [assignment["assigned_subject"]]
                assigned_assigments = assignment
        #print(assigned_subjects)
        #print(assigned_classes)
        #classes_assigned = random.sample(class_objects, num_classes)


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
            "s_classes": assigned_classes,
            "subjects": assigned_subjects,
            "teacher_assignments": assigned_assigments
        }
        teachers.append(teacher)


    grades = []

    

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

    
    for class_ in class_objects:
        for subject in subjects_objects:
            class_["subjects"].append(subject)

            
    generate_data = False

    while True:
        print("Waiting for messages...")
        for message in consumer:
                print(message.value)
                #producer.send(send_topic, message.value)
                if message.value['type'] == 'init':
                    time.sleep(7)
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
                                "teachers": subject['teachers'],
                                "classes": subject['classes']
                            }
                            )
                            #print(subject)

                    


                    classes_api_url = "http://localhost:8080/classes"
                    current_classes = requests.get(classes_api_url).json()
                    for class_ in class_objects:
                        existing_class = next((c for c in current_classes if c['classname'] == class_['classname']), None) #and c["school"] == class_["school"]), None)
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
                            #print(class_)

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
                            #print(student)
                    
                    
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
                                "subjects": teacher['subjects'],
                            }
                            )
                            #print(teacher)
                    

                    assigments_api_url = "http://localhost:8080/teaching_assignments"
                    current_assigments = requests.get(assigments_api_url).json()
                    for assigment in teacher_assignment:
                        existing_assigment = next((s for s in current_assigments if s['id'] == subject['id']), None)
                        if existing_assigment is None:
                            #print(assigment["assigned_class"])
                            for teacher in teachers:
                                if teacher["id"] == assigment["teacher_id"]:
                                    producer.send(send_topic,
                                    {
                                        "type": "assigment",
                                        "teacher": teacher,
                                        "class": assigment["assigned_class"],
                                        "subject": assigment["assigned_subject"]
                                    }             
                            )
                            #print(assigment)


                    generate_data = True

                if message.value['type'] == 'update':
                    generate_data = False
                    students_api_url = "http://localhost:8080/students"
                    class_api_url = "http://localhost:8080/classes"

                if message.value['type'] == 'periodic':
                    for students_classes in class_objects:
                        for student in students:
                            
                            if student["studentclass"] == students_classes :
                                subject = random.choice(students_classes["subjects"])
                                grade = random.randint(1, 20)
                                teacher_info = next((t for t in teacher_assignment if t["assigned_class"] == students_classes and t["assigned_subject"] == subject), None)
                                teacher = next((t for t in teachers if t["id"] == teacher_info["teacher_id"]), None)
                                print("teacher:",teacher)
                                print
                                print("..........................")
                                #print(student)
                                grades.append({
                                    "student": student,
                                    "subject": subject,
                                    "teacher": teacher,
                                    "grade": grade
                                })
                    
                    for grade in grades:
                        producer.send(send_topic,
                        {
                            "type": "grade",
                            "student": grade["student"],
                            "subject": grade["subject"],
                            "teacher": grade["teacher"],
                            "grade": grade["grade"]
                        }
                        )
                        #print(grade)
        
        



            
if __name__ == '__main__':
    main()