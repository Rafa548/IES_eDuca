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
}
