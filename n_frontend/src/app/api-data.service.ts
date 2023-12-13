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

  async getStudentByEmail(token: string|null, email: string): Promise<any> {
    const url = this.baseURL + '/students?email=' + email;
    const data = await fetch(url, {method: 'GET', headers: { Authorization: `Bearer ${token}` } });
    return await data.json() ?? undefined;
  }
}
