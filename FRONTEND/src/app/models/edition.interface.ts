import { SubjectResponse } from "./subject.interface"

enum EditionStatus {
    DRAFT,
    PUBLISHED,
    CLOSED
}

export interface EditionRequest {
    startDate: Date
    endDate: Date
    year: number
    status: EditionStatus
    surveyId: number
}

export interface EditionResponse {
    id: number
    creationDate: Date
    startDate: Date
    endDate: Date
    year: number
    status: EditionStatus
    surveyId: number
    subjects: SubjectResponse[]
    version: string
}