
export interface OwnerRequest {
    name: string,
    email: string,
    active: boolean
}

export interface OwnerResponse {
    id: number,
    name: string,
    email: string,
    active: boolean
}