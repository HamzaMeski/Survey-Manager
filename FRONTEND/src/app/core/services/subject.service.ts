import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SubjectRequest, SubjectResponse} from '../../models/subject.interface';
import {apiUrl} from './api.url';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  private apiUrl = apiUrl+'/subjects'

  constructor(private http: HttpClient) { }

  createSubject(surveyEditionId: number, subject: SubjectRequest): Observable<SubjectResponse> {
    return this.http.post<SubjectResponse>(`${this.apiUrl}/survey-edition/${surveyEditionId}`, subject)
  }

  getSubjectsBySurveyEditionId(id: number): Observable<SubjectResponse[]> {
    return this.http.get<SubjectResponse[]>(`${this.apiUrl}/survey-edition/${id}`)
  }
}
