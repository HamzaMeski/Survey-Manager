
export interface AnswerRequest {
    text:string
    orderIndex:number
    incrementCount:boolean
}

export interface AnswerResponse {
    id:number
    text:string
    orderIndex:number
    incrementCount:boolean
    questionId:number
    createdAt: Date;
    updatedAt: Date;
}