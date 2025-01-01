
export interface SubjectRequest {
  title:string
  description:string
  parentSubjectId:number
}

export interface SubjectResponse {
  title:string
  description:string
  parentSubjectId:number
  subSubjects: SubjectResponse[]
  level:number
  path:string
  createdAt: Date
  updatedAt: Date
}
