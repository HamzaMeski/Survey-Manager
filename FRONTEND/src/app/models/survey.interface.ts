import { Owner } from "./owner.interface";

enum SurveyStatus {
    ACTIVE,
    INACTIVE,
    ARCHIVED
}

export interface SurveyRequest {
    title: string;
    description: string;
    status: SurveyStatus
    category: string, 
    targetAudience: string, 
    ownerId: number
}

export interface SurveyResponse {
    id: number;
    title: string;
    description: string;
    status: SurveyStatus
    category: string, 
    targetAudience: string, 
    owner: Owner
    createdAt: Date;
    updatedAt: Date;
}