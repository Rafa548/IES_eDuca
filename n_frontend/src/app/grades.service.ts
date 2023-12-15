import { Injectable } from '@angular/core';
import {Grades} from "./Grades";

@Injectable({
  providedIn: 'root'
})
export class GradesService {
  private baseURL = 'http://localhost:8080/grades';


  constructor() { }


  async getStudentGrades(nmec:number|undefined): Promise<Grades[]> {
    const url = this.baseURL + "?nmec=" + nmec;
    const data = await fetch(url,{method: 'GET', headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem('token')}});
    return await data.json() ?? undefined;
  }

  async updateGrade(gradeId: number,grade:number): Promise<any> {
    const token = localStorage.getItem('token');
    const url=this.baseURL + "/" + gradeId;
    const new_grade = {"id":gradeId,"grade":grade};
    const data = await fetch(url, {method: 'PUT', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(new_grade) });
    //console.log(data);
    return await data.text() ?? undefined;
  }

  async deleteGrade(gradeId: number): Promise<any> {
    const token = localStorage.getItem('token');
    const url=this.baseURL + "/" + gradeId;
    const data = await fetch(url, {method: 'DELETE', headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }});
    return await data.text() ?? undefined;
  }

  async addGrade(grade: number, email_t: string | undefined, email_s: string, subject_name: string | undefined): Promise<any> {
    const token = localStorage.getItem('token');
    const url = this.baseURL;
    const new_grade = {
      "grade": grade,
      "email_t": email_t,
      "email_s": email_s,
      "subject": subject_name
    };
    const data = await fetch(url, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(new_grade)
    });

    return await data.text() ?? undefined;
  }
}
