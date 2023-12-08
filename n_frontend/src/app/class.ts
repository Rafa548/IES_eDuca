


export interface Class {
  id: number;
  classname: string;
  school: string;
  subjects: any[];
  students: Student[];
  teachers: any[];
  teaching_assignments: any[];
}

export interface Subject {
  id: number;
  name: string;
  teacher: any;
  class: any;
}

export interface Teacher {
  id: number;
  name: string;
  subjects: any[];
  classes: any[];
  teaching_assignments: any[];
}

export interface TeachingAssignment {
  id: number;
  teacher: any;
  subject: any;
  class: any;
}

export interface Student {
  id: number;
  name: string;
  classes: any[];
  grades: any[];
  subjects: any[];
}
