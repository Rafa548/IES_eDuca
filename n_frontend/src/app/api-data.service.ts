import { Injectable } from '@angular/core';
import {Class} from "./class";

@Injectable({
  providedIn: 'root'
})
export class ApiDataService {
  private baseURL = 'http://localhost:8080';

  constructor() { }

  async getClasses(token: string|null): Promise<any[]> {
    const url = this.baseURL + '/classes';
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async getSubjects(token: string|null): Promise<any[]> {
    const url = this.baseURL + '/subjects';
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }


  async sendMsg(token: string|null, msg: string, receiver: string): Promise<any> {
    const url = this.baseURL + '/notification';
    //console.log(url);
    const data = await fetch(url, {method: 'POST', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify({message: msg, receiver: receiver, type: "CLASS"}) });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async sendMsgStudent(token: string|null, msg: string, receiver: string): Promise<any> {
    const url = this.baseURL + '/notification';
    //console.log(url);
    const data = await fetch(url, {method: 'POST', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify({message: msg, receiver: receiver, type: "ALERT"}) });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async getClass(token: string|null, classId: number): Promise<any> {
    const url = this.baseURL + '/classes/byID/' + classId;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    return await data.json() ?? undefined;
  }

  async getStudentGrade(token: string|null, className: string, studentnmec: number, subjectname: string): Promise<any> {
    const url = this.baseURL + '/classes/' + className + '/' + studentnmec + '/' + subjectname + '/grade';
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    return await data.text() ?? undefined;
  }


  async getStudentByEmail(token: string|null, email: string): Promise<any> {
    const url = this.baseURL + '/students?email=' + email;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    return await data.json() ?? undefined;
  }

  async deleteStudentFromClass(token: string|null, className: string, studentnmec: number): Promise<any> {
    const url = this.baseURL + '/classes/' + className + '/students/' + studentnmec;
    //console.log(url);
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
    if (!data.ok) {
      throw new Error('Failed to delete student from class');
    }
    return await data.text() ?? undefined;
  }

  async deleteClass(token: string|null, className: string): Promise<any> {
    const url = this.baseURL + '/classes/' + className;
    //console.log(url);
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
    if (!data.ok) {
      throw new Error('Failed to delete class');
    }
    return await data.text() ?? undefined;
  }

  async getStudents(token: string|null): Promise<any[]> {
    const url = this.baseURL + '/students';
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async getStudent(token: string|null, studentNmec: number): Promise<any> {
    const url = this.baseURL + '/students/' + studentNmec;
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async updateStudent(token: string|null, student : any): Promise<any> {
    const studentnmec = student.nmec;

    const url = this.baseURL + '/students/' + studentnmec;
    console.log(url);
    console.log(JSON.stringify(student));
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(student) });
    if (!data.ok) {
      throw new Error('Failed to update student');
    }
    return await data.text() ?? undefined;
  }

  async deleteStudent(token: string|null, studentnmec: number): Promise<any> {
    const url = this.baseURL + '/students/' + studentnmec;
    //console.log(url);
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
    if (!data.ok) {
      throw new Error('Failed to delete student');
    }
    return await data.text() ?? undefined;
  }

  async addStudent(token: string|null, student: any): Promise<any> {
    const url = this.baseURL + '/students';
    //console.log(url);
    //console.log(JSON.stringify(student));
    const data = await fetch(url, {method: 'POST', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(student) });
    if (!data.ok) {
      throw new Error('Failed to add student');
    }
    console.log(data);
    return await data.text() ?? undefined;
  }

  async getTeachers(token: string|null): Promise<any[]> {
    const url = this.baseURL + '/teachers';
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }


  async getTeacher(token: string|null, teacherNmec: number): Promise<any> {
    const url = this.baseURL + '/teachers/' + teacherNmec;
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }


  async getTeachingAssigmments(token: string|null): Promise<any[]> {
    const url = this.baseURL + '/teaching_assignments';
    //console.log(url);
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    //console.log(data.json());
    return await data.json() ?? undefined;
  }

  async updateTeacher(token: string|null , teacher: any): Promise<any> {
    //console.log(teacher);
    const teacherNmec = teacher.nmec;
    let classes : string[] = [];
    const url = this.baseURL + '/teachers/' + teacherNmec;
    //console.log(teacher.classes);

    classes = teacher.classes;
    teacher = {updates:{name: teacher.name, email: teacher.email, password: teacher.password, nmec: teacher.nmec},
               classes: classes
              };

    //console.log(url);
    //console.log(JSON.stringify(teacher));
    //console.log(JSON.stringify(classes));
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: (JSON.stringify(teacher)) });
    //console.log(data);
    return await data.text() ?? undefined;
  }

  async updateGrade(token: string|null, grade: any): Promise<any> {
    const id = grade.id;
    const url = this.baseURL + '/grades/' + id;
    //console.log(url);
    //console.log(JSON.stringify(grade));
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(grade) });
    //console.log(data);
    return await data.text() ?? undefined;
  }

  async deleteGrade(token: string|null, grade: any): Promise<any> {
    const id = grade.id;
    const url = this.baseURL + '/grades/' + id;
    //console.log(url);
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
    if (!data.ok) {
      throw new Error('Failed to delete grade');
    }
    return await data.text() ?? undefined;
  }



  async addTeachingAssigment(item: string | null, json: { subject: string; class: string; email: any }):Promise<any> {
      const url = this.baseURL + '/teaching_assignments';
      const data = await fetch(url, {method: 'POST', headers: { Authorization: `Bearer ${item}`, 'Content-Type': 'application/json' }, body: JSON.stringify(json) });
      return await data.text() ?? undefined;
  }

  async deleteTeachingAssigment(item: string | null, json: { subject: string; class: string; email: any }):Promise<any> {
    const url = this.baseURL + '/teaching_assignments';
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${item}`, 'Content-Type': 'application/json' }, body: JSON.stringify(json) });
    return await data.text() ?? undefined;

  }

  async getTeacherGrades(item: string | null, TeacherNmec: number):Promise<any> {
    const url = this.baseURL + '/grades/'  + 'teacher/' + TeacherNmec;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${item}` } });
    return await data.json() ?? undefined;

  }

  async deleteTeacher(item: string | null,TeacherNmec: number):Promise<any>{
    const url = this.baseURL + '/teachers/' + TeacherNmec;
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${item}` } });
    return await data.text() ?? undefined;
  }

  async getTeachersBySubject(item: string | null, subjectName: string):Promise<any>{
    const url = this.baseURL + '/teachers?subject=' + subjectName;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${item}` } });
    return await data.json() ?? undefined;
  }

  async getClassTeachers(item: string | null, className: string):Promise<any>{
    const url = this.baseURL + '/teaching_assignments?class_name=' + className;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${item}` } });
    return await data.json() ?? undefined;
  }

  async addTeacher(item: string | null, teacher: any):Promise<any>{
    const url = this.baseURL + '/teachers';
    const data = await fetch(url, {method: 'POST', headers: { Authorization: `Bearer ${item}`, 'Content-Type': 'application/json' }, body: JSON.stringify(teacher) });
    return await data.text() ?? undefined;
  }
  
  async getAvgGrade(item: string | null, className: string, subject: string, nmec: number): Promise<any> {
    const url = this.baseURL + '/classes/' + className + '/' + nmec + '/' + subject + '/grade';
    const data = await fetch(url, {method: 'GET', headers: {Authorization: `Bearer ${item}`}});
    return await data.text() ?? undefined;
  }

  async createClass(item: string | null, json: { classname: string; }): Promise<any> {
    const url = this.baseURL + '/classes';
    const data = await fetch(url, {
      method: 'POST',
      headers: {Authorization: `Bearer ${item}`, 'Content-Type': 'application/json'},
      body: JSON.stringify(json)
    });
    return await data.text() ?? undefined;
  }
}
