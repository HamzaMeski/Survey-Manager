import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiUrl} from './api.url';
import {AnswerResponse} from '../../models/answer.interface';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  private apiUrl = apiUrl+'/answers'

  constructor(private http:HttpClient) { }

  getAnswersByQuestion(id: number): Observable<AnswerResponse[]> {
    return this.http.get<AnswerResponse[]>(`${this.apiUrl}/question/${id}`)
  }
}
