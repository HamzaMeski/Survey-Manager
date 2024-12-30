enum EditionStatus {
    DRAFT,
    PUBLISHED,
    CLOSED
} 
export interface Edition {
    id: number;
    creationDate: Date;
    startDate: Date;
    endDate: Date;
    year: number;
    status: EditionStatus;
    version: string;
}