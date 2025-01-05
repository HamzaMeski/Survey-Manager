import { AnswerResponse } from "./answer.interface"

enum QuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE
}

export interface QuestionRequest {
    text:string
    type:QuestionType
    required:boolean
    instructions:string
}

export interface QuestionResponse {
    id:number
    text:string
    type:QuestionType
    required:boolean
    orderIndex:number
    instructions:string
    answers: AnswerResponse[]
    createdAt: Date
    updatedAt: Date
}
