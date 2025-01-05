import {Injectable} from '@angular/core';
import {apiUrl} from './api.url';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {QuestionRequest, QuestionResponse} from '../../models/question.interface';
import {SurveyResponse} from '../../models/survey.interface';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = apiUrl+'/questions'

  constructor(private http:HttpClient) { }

  createQuestion(subjectId: number, question:QuestionRequest): Observable<QuestionResponse> {
    return this.http.post<QuestionResponse>(`${this.apiUrl}/subject/${subjectId}`, question)
  }

  getQuestionsBySubjectId(id: number): Observable<QuestionResponse[]> {
    return this.http.get<QuestionResponse[]>(`${this.apiUrl}/subject/${id}`)
  }

  updateQuestion(id: number, question: QuestionRequest): Observable<QuestionResponse> {
    return this.http.put<QuestionResponse>(`${this.apiUrl}/${id}`, question);
  }

  deleteQuestion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
