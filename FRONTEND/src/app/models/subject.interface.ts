
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
  createdAt: Date
  updatedAt: Date
}
