export interface Grades{
  id:number;
  grade:string;
  student:string;
  subjects: { [subject: string]: number };
  teacher:string;
}
