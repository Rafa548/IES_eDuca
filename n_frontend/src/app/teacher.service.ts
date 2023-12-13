import { Injectable } from '@angular/core';
import {Teacher} from "./Teacher";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private baseURL = 'http://localhost:8080/teachers';

  constructor() { }

  async getTeacherByEmail(email: string): Promise<Teacher> {
    const url = this.baseURL + "?email=" + email;
    console.log('token123', localStorage.getItem('token'));
    const data = await fetch(url, {method: 'GET', headers: {Authorization: `Bearer ${localStorage.getItem('token')}`},});
    return await data.json() ?? undefined;
  }

  async getTeacherClasses(nmec:number): Promise<any>{
    const url = this.baseURL + "/" + nmec + "/classes";
    console.log('token123', localStorage.getItem('token'));
    const data = await fetch(url, {method: 'GET', headers: {Authorization: `Bearer ${localStorage.getItem('token')}`},});
    return await data.json() ?? undefined;
  }

  async getStudentsFromClass(classname: string | null): Promise<any>{
    const url : string = "http://localhost:8080/classes/" + classname + "/students";
    const data = await fetch(url, {method: 'GET', headers: {Authorization: `Bearer ${localStorage.getItem('token')}`},});
    return await data.json() ?? undefined;
  }

  async getTeacherSubjects(email: string, classname: string | null): Promise<any> {
    console.log("email: ", email);
    console.log("classname: ", classname);
    const url = 'http://localhost:8080/teaching_assignments?email=' + email;
    const data = await fetch(url, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    console.log("data",data);
    const teachingAssignments = await data.json() ?? [];

    if (classname !== null) {
      return teachingAssignments.filter((teachingAssignment: any) => teachingAssignment.sclass.classname === classname);
    }
    console.log("teachingAssignments",teachingAssignments)
    return teachingAssignments;
  }


}
