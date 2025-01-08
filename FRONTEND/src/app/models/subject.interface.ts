import {QuestionResponse} from './question.interface';

export interface SubjectRequest {
  title:string
  description:string
  parentSubjectId:number
}

export interface SubjectResponse {
  id:number
  title:string
  description:string
  parentSubjectId:number
  subSubjects: SubjectResponse[]
  questions: QuestionResponse[]
  level:number
  path:string
  createdAt: Date
  updatedAt: Date
}
