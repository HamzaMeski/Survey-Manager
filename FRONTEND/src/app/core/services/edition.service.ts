import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SurveyResponse} from '../../models/survey.interface';
import {HttpClient} from '@angular/common/http';
import {apiUrl} from './api.url';
import {EditionResponse} from '../../models/edition.interface';


@Injectable({
  providedIn: 'root'
})
export class EditionService {
  private apiUrl = apiUrl+'/survey-editions'

  constructor(private http: HttpClient) { }

  getEditionById(id: number): Observable<EditionResponse> {
    return this.http.get<EditionResponse>(`${this.apiUrl}/${id}`);
  }
}
