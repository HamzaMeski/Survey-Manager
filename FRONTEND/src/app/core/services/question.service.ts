import {Injectable} from '@angular/core';
import {apiUrl} from './api.url';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {QuestionResponse} from '../../models/question.interface';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = apiUrl+'/questions'

  constructor(private http:HttpClient) { }

  createQuestion(subjectId: number, question:QuestionResponse): Observable<QuestionResponse> {
    return this.http.post<QuestionResponse>(`${this.apiUrl}/subject/${subjectId}`, question)
  }

  getQuestionsBySubjectId(id: number): Observable<QuestionResponse[]> {
    return this.http.get<QuestionResponse[]>(`${this.apiUrl}/subject/${id}`)
  }

  deleteQuestion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
