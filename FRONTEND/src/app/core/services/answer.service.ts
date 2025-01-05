import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiUrl} from './api.url';
import {AnswerRequest, AnswerResponse} from '../../models/answer.interface';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  private apiUrl = apiUrl+'/answers'

  constructor(private http:HttpClient) { }

  createAnswer(questionId: number, answer:AnswerRequest): Observable<AnswerResponse> {
    return this.http.post<AnswerResponse>(`${this.apiUrl}/question/${questionId}`, answer)
  }

  getAnswersByQuestion(id: number): Observable<AnswerResponse[]> {
    return this.http.get<AnswerResponse[]>(`${this.apiUrl}/question/${id}`)
  }

  updateAnswer(id: number, answer:AnswerRequest): Observable<AnswerResponse> {
    return this.http.put<AnswerResponse>(`${this.apiUrl}/${id}`, answer);
  }

  deleteAnswer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
