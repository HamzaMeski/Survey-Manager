export interface Survey {
    id?: number;
    title: string;
    description: string;
    createdAt?: Date;
    updatedAt?: Date;
}

export interface SurveyRequest {
    title: string;
    description: string;
}

export interface SurveyResponse {
    id: number;
    title: string;
    description: string;
    createdAt: Date;
    updatedAt: Date;
}