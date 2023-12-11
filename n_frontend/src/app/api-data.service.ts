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
    console.log(teacher);
    const teacherNmec = teacher.nmec;
    const url = this.baseURL + '/teachers/' + teacherNmec;
    console.log(url);
    //console.log(JSON.stringify(teacher));
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(teacher) });
    //console.log(data);
    return await data.text() ?? undefined;
  }


}
