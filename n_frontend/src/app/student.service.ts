import { Injectable } from '@angular/core';
import {Student} from "./Student";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private baseURL = 'http://localhost:8080/students';

  constructor() {
  }

  async getStudentByNmec(nmec: number): Promise<Student> {
    const url = this.baseURL + "?nmec=" + nmec;
    const data = await fetch(url, {
      method: 'GET',
      headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem('token')}
    });
    return await data.json() ?? undefined;
  }

  async getIDofStudent(email: string): Promise<number | undefined> {
    const url = this.baseURL + "?email=" + email;
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data.id as number;
    } else {
      // Handle the error case
      console.error('Failed to get student ID:', response.statusText);
      return undefined;
    }
  }

  async getNmecofStudent(email: string): Promise<number | undefined> {
    const url = this.baseURL + "?email=" + email;
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data.nmec as number;
    } else {
      // Handle the error case
      console.error('Failed to get student nmec:', response.statusText);
      return undefined;
    }

  }


  async getStudentByEmail(email: string): Promise<any> {
    const url = this.baseURL + "?email=" + email;
    console.log('token123', localStorage.getItem('token'));
    const data = await fetch(url, {method: 'GET', headers: {Authorization: `Bearer ${localStorage.getItem('token')}`},});
    return await data.json() ?? undefined;
  }


  async updateStudent(token: string|null, student : any): Promise<any> {
    const studentnmec = student.nmec;
    console.log("nmecfeihgiejgiejig",studentnmec)
    console.log("token ggggg",token)
    const url = this.baseURL + '/' + studentnmec;
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(student) });
    if (!data.ok) {
      throw new Error('Failed to update student');
    }
    return await data.text() ?? undefined;
  }

  async getPasswordofStudent(email: string): Promise<any> {
    const url = this.baseURL + "?email=" + email;
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data.password as string;
    } else {
      // Handle the error case
      console.error('Failed to get student password:', response.statusText);
      return undefined;
    }
  }

  async getStudentGradeBySubject(subjectname: string | undefined, studentnmec: number): Promise<any> {
    const url = 'http://localhost:8080/grades?nmec=' + studentnmec;
    const data = await fetch(url, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    console.log("1111")
    const grades = await data.json() ?? [];
    const filteredGrades = grades.filter((grade: any) => grade.subject.name === subjectname);
    return filteredGrades;
  }

}
