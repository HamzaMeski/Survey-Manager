import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SubjectResponse} from '../../models/subject.interface';
import {apiUrl} from './api.url';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  private apiUrl = apiUrl+'/subjects'

  constructor(private http: HttpClient) { }

  getBySurveyEditionId(id: number): Observable<SubjectResponse[]> {
    return this.http.get<SubjectResponse[]>(`${this.apiUrl}/survey-edition/${id}`)
  }
}
