import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SurveyRequest, SurveyResponse } from '../../models/survey.interface';

@Injectable({
    providedIn: 'root'
})
export class SurveyService {
    private apiUrl = 'http://localhost:8080/api/surveys';

    constructor(private http: HttpClient) { }

    getAllSurveys(): Observable<SurveyResponse[]> {
        return this.http.get<SurveyResponse[]>(this.apiUrl);
    }

    getSurveyById(id: number): Observable<SurveyResponse> {
        return this.http.get<SurveyResponse>(`${this.apiUrl}/${id}`);
    }

    createSurvey(survey: SurveyRequest): Observable<SurveyResponse> {
        return this.http.post<SurveyResponse>(this.apiUrl, survey);
    }

    updateSurvey(id: number, survey: SurveyRequest): Observable<SurveyResponse> {
        return this.http.put<SurveyResponse>(`${this.apiUrl}/${id}`, survey);
    }

    deleteSurvey(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
