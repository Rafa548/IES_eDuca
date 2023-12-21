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
    
    consumer = KafkaConsumer(bootstrap_servers=['kafka:29092'], auto_offset_reset='earliest', enable_auto_commit=True, group_id='my-group', value_deserializer=lambda x: json.loads(x.decode('utf-8')))

    producer = KafkaProducer(bootstrap_servers=['kafka:29092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'))

    
    
    send_topic = 'eDuca_dataGen'
    receive_topic = 'eDucaApp'

    consumer.subscribe([receive_topic])

    num_students = 400
   
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
                #print(assignment["assigned_class"])
                if assignment["assigned_class"] not in assigned_classes:
                    assigned_classes.append(assignment["assigned_class"])
                if assignment["assigned_subject"] not in assigned_subjects:
                    assigned_subjects.append(assignment["assigned_subject"])
                assigned_assigments.append(assignment)
        #print(assigned_subjects)
        #print(assigned_classes)
        #print("--------------------")
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
        #print(teacher["s_classes"])
        teachers.append(teacher)

    
    

    assigned_classes = {class_id: set() for class_id in range(1, 41)}

    for i in range(1, num_students + 1):
        student_class = random.choice(class_objects)
        while len(assigned_classes[student_class["id"]]) >= 10:
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
        assigned_classes[student_class["id"]].add(student_nmec)

    
    for class_ in class_objects:
        for subject in subjects_objects:
            class_["subjects"].append(subject)

    while True:
        print("Waiting for messages...")
        for message in consumer:
                print(message.value)

                admin_token_url = "http://spring:8080/auth/signin"
                data = {
                    'email': "admin@gmail.com",
                    'password': "admin"
                }

                
                response = requests.post(admin_token_url, json=data) 
                token = response.json().get('token')

                headers = {
                    'Authorization': 'Bearer ' + token 
                }
                
                #producer.send(send_topic, message.value)
                

                if message.value['type'] == 'init':
                    subjects_api_url = "http://spring:8080/subjects"
                    current_subjects = requests.get(subjects_api_url,headers=headers).json()
                    subjects_messages = []
                    
                    for subject in subjects_objects:
                        existing_subject = next((s for s in current_subjects if s['name'] == subject['name']), None)
                        if existing_subject is None:
                            subjects_messages.append({
                                "type": "subject",
                                "name": subject['name'],
                                "teachers": subject['teachers'],
                                "classes": subject['classes']
                            })
                            """ producer.send(send_topic,       
                            {
                                "type": "subject",
                                "name": subject['name'],
                                "teachers": subject['teachers'],
                                "classes": subject['classes']
                            }
                            ) """
                            #print(subject)

                    producer.send(send_topic,
                                {
                                "type": "task",
                                "task_type": "subjects",
                                "total_expected": len(subjects_messages)
                                }
                            )
                    
                    for subject_json in subjects_messages:
                        producer.send(send_topic, subject_json)

                    


                    classes_api_url = "http://spring:8080/classes"
                    current_classes = requests.get(classes_api_url,headers=headers).json()
                    classes_messages = []
                    for class_ in class_objects:
                        existing_class = next((c for c in current_classes if c['classname'] == class_['classname']), None) #and c["school"] == class_["school"]), None)
                        if existing_class is None:
                            classes_messages.append({
                                "type": "class",
                                "classname": class_['classname'],
                                "school": class_['school'],
                                "students": class_['students'],
                                "subjects": class_['subjects'],
                                "teachers": [   ]
                            })
                            """ producer.send(send_topic,       
                            {
                                "type": "class",
                                "classname": class_['classname'],
                                "school": class_['school'],
                                "students": class_['students'],
                                "subjects": class_['subjects'],
                                "teachers": class_['teachers']
                            }
                            ) """
                            #print(class_)
                    
                    producer.send(send_topic,
                                {
                                "type": "task",
                                "task_type": "classes",
                                "total_expected": len(classes_messages)
                                }
                            )
                    
                    for class_json in classes_messages:
                        producer.send(send_topic, class_json)
                            

                    students_api_url = "http://spring:8080/students"
                    current_students = requests.get(students_api_url,headers=headers).json()
                    students_messages = []

                    for student in students:
                        existing_student = next((s for s in current_students if s['nmec'] == student['nmec']), None)
                        if existing_student is None:
                            students_messages.append({
                                "type": "student",
                                "name": student['name'],
                                "nmec": student['nmec'],
                                "email": student['email'],
                                "password": student['password'],
                                "school": student['school'],
                                "studentclass": student['studentclass']
                            })
                            """ producer.send(send_topic,       
                            {
                                "type": "student",
                                "name": student['name'],
                                "nmec": student['nmec'],
                                "email": student['email'],
                                "password": student['password'],
                                "school": student['school'],
                                "studentclass": student['studentclass']
                            }
                            ) """
                            #print(student)

                    producer.send(send_topic,
                                {
                                "type": "task",
                                "task_type": "students",
                                "total_expected": len(students_messages)
                                }
                            )
                    
                    for student_json in students_messages:
                        producer.send(send_topic, student_json)
                    
                    
                    teachers_api_url = "http://spring:8080/teachers"
                    current_teachers = requests.get(teachers_api_url,headers=headers).json()
                    teachers_messages = []
                    for teacher in teachers:
                        existing_teacher = next((t for t in current_teachers if t['nmec'] == teacher['nmec']), None)
                        if existing_teacher is None:
                            if "1a" in teacher["s_classes"]:
                                print("1a")
                            teachers_messages.append({
                                "type": "teacher",
                                "name": teacher['name'],
                                "nmec": teacher['nmec'],
                                "email": teacher['email'],
                                "password": teacher['password'],
                                "school": teacher['school'],
                                "s_classes": teacher['s_classes'],
                                "subjects": teacher['subjects'],
                                "teacher_assignments": teacher['teacher_assignments']
                            })
                            """ producer.send(send_topic,       
                            {
                                "type": "teacher",
                                "name": teacher['name'],
                                "nmec": teacher['nmec'],
                                "email": teacher['email'],
                                "password": teacher['password'],
                                "school": teacher['school'],
                                "s_classes": teacher['s_classes'],
                                "subjects": teacher['subjects'],
                                "teacher_assignments": teacher['teacher_assignments']
                            }
                            ) """
                            #print(teacher)
                    
                    producer.send(send_topic,
                                {
                                "type": "task",
                                "task_type": "teachers",
                                "total_expected": len(teachers_messages)
                                }
                            )
                    
                    for teacher_json in teachers_messages:
                        producer.send(send_topic, teacher_json)

                    assigments_api_url = "http://spring:8080/teaching_assignments"
                    current_assigments = requests.get(assigments_api_url,headers=headers).json()
                    assigments_messages = []
                    for assigment in teacher_assignment:
                        existing_assigment = next((s for s in current_assigments if s['id'] == subject['id']), None)
                        if existing_assigment is None:
                            #print(assigment["assigned_class"])
                            for teacher in teachers:
                                if teacher["id"] == assigment["teacher_id"]:
                                    assigments_messages.append({
                                        "type": "assigment",
                                        "teacher": teacher,
                                        "class": assigment["assigned_class"],
                                        "subject": assigment["assigned_subject"]
                                    })
                                    """ producer.send(send_topic,       
                                    {
                                        "type": "assigment",
                                        "teacher": teacher,
                                        "class": assigment["assigned_class"],
                                        "subject": assigment["assigned_subject"]
                                    }
                                    ) """
                            #print(assigment)
 
                    producer.send(send_topic,
                                {
                                "type": "task",
                                "task_type": "assigments",
                                "total_expected": len(assigments_messages)
                                }
                            )

                    for assigment_json in assigments_messages:
                        producer.send(send_topic, assigment_json)

                if message.value['type'] == 'periodic':
                    

                    std_progress_api_url = "http://spring:8080/progress/insertion/students"
                    response = requests.get(std_progress_api_url,headers=headers)
                    while response.status_code != 200:
                        response = requests.get(std_progress_api_url,headers=headers)
                        time.sleep(1)
                    std_progress = response.json()
                    while std_progress < 99.99999:
                        response = requests.get(std_progress_api_url,headers=headers)
                        std_progress = response.json()
                        time.sleep(1)
                    students_api_url = "http://spring:8080/students"
                    current_students = requests.get(students_api_url,headers=headers).json()
                    class_progress_api_url = "http://spring:8080/progress/insertion/classes"
                    response = requests.get(class_progress_api_url,headers=headers)
                    while response.status_code != 200:
                        response = requests.get(class_progress_api_url,headers=headers)
                        time.sleep(1)
                    class_progress = response.json()
                    while class_progress < 99.99999:
                        response = requests.get(class_progress_api_url,headers=headers)
                        class_progress = response.json()
                        time.sleep(1)
                    class_api_url = "http://spring:8080/classes"
                    current_classes = requests.get(class_api_url,headers=headers).json()
                    teacher_progress_api_url = "http://spring:8080/progress/insertion/teachers"
                    response = requests.get(teacher_progress_api_url,headers=headers)
                    while response.status_code != 200:
                        response = requests.get(teacher_progress_api_url,headers=headers)
                        time.sleep(1)
                    teacher_progress = response.json()
                    while teacher_progress < 99.99999:
                        response = requests.get(teacher_progress_api_url,headers=headers)
                        teacher_progress = response.json()
                        time.sleep(1)
                    teacher_api_url = "http://spring:8080/teachers"
                    current_teachers = requests.get(teacher_api_url,headers=headers).json()
                    subject_progress_api_url = "http://spring:8080/progress/insertion/subjects"
                    response = requests.get(subject_progress_api_url,headers=headers)
                    while response.status_code != 200:
                        response = requests.get(subject_progress_api_url,headers=headers)
                        time.sleep(1)
                    subject_progress = response.json()
                    while subject_progress < 99.99999:
                        response = requests.get(subject_progress_api_url,headers=headers)
                        subject_progress = response.json()
                        time.sleep(1)
                    subjects_api_url = "http://spring:8080/subjects"
                    current_subjects = requests.get(subjects_api_url,headers=headers).json()
                    assigment_progress_api_url = "http://spring:8080/progress/insertion/assigments"
                    response = requests.get(assigment_progress_api_url)
                    while response.status_code != 200:
                        response = requests.get(assigment_progress_api_url,headers=headers)
                        time.sleep(1)
                    assigment_progress = response.json()
                    while assigment_progress < 99.99999:
                        response = requests.get(assigment_progress_api_url,headers=headers)
                        assigment_progress = response.json()
                        time.sleep(1)
                    assigments_api_url = "http://spring:8080/teaching_assignments"
                    current_assigments = requests.get(assigments_api_url,headers=headers).json()
                    """ grade_progress_api_url = "http://spring:8080/progress/insertion/grades"
                    response = requests.get(grade_progress_api_url)
                    while response.status_code != 200:
                        response = requests.get(grade_progress_api_url)
                        time.sleep(1)
                    grade_progress = response.json()
                    while grade_progress["progress"] < 99.99999:
                        response = requests.get(grade_progress_api_url)
                        grade_progress = response.json()
                        time.sleep(1) """
                    max_grades_number = 20
                    grades = {}
                    new_grades = []
                    grades_api_url = "http://spring:8080/grades"
                    current_grades = requests.get(grades_api_url,headers=headers).json()

                    for existing_grade in current_grades:
                        pair_id = (
                            existing_grade["student"]["id"],
                            existing_grade["subject"]["id"],
                        )

                        if pair_id not in grades:
                            grades[pair_id] = []

                        grades[pair_id].append(existing_grade)
                        #print(len(grades[pair_id]))

                    for student_class in current_classes:
                        for student in current_students:
                            #print(student)
                            if student["studentclass"]["classname"] == student_class["classname"]:
                                subject = random.choice(student_class["subjects"])
                                pair_id = (
                                    student["id"],
                                    subject["id"],
                                )
                                if pair_id not in grades:
                                    grades[pair_id] = []
                                if len(grades[pair_id]) < max_grades_number:
                                    grade = random.randint(-2, 20)
                                    teacher_info = next((t for t in current_assigments if t["sclass"]["classname"] == student_class["classname"] and t["subject"]["name"] == subject["name"]), None)
                                    teacher = next((t for t in current_teachers if t["id"] == teacher_info["teacher"]["id"]), None)
                                    #print("teacher:",teacher)
                                
                                    #print("..........................")
                                    
                                    new_grades.append({
                                        "student": student,
                                        "subject": subject,
                                        "teacher": teacher,
                                        "grade": grade
                                    })
                    
                    
                    for grade in new_grades:
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
