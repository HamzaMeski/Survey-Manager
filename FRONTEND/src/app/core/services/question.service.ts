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

  getQuestionsBySubjectId(id: number): Observable<QuestionResponse[]> {
    return this.http.get<QuestionResponse[]>(`${this.apiUrl}/subject/${id}`)
  }
}
