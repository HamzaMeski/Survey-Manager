import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionResponse} from '../../../../models/question.interface';
import {SubjectResponse} from '../../../../models/subject.interface';
import {QuestionService} from '../../../../core/services/question.service';

@Component({
  selector: 'app-question-list',
  imports: [
    CommonModule
  ],
  templateUrl: './question-list.component.html'
})
export class QuestionListComponent implements OnInit, OnChanges {
  @Input() subject: SubjectResponse  | null = null
  @Output() questionSelected = new EventEmitter<QuestionResponse>();
  isQuestionSelected: boolean = false

  questions: QuestionResponse[] = [];
  isLoading = false;

  constructor(private questionService: QuestionService) {}

  ngOnInit() {
    this.loadQuestions();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['subject']) {
      this.loadQuestions();
    }
  }

  loadQuestions() {
    if (!this.subject) return;

    this.isLoading = true;
    this.questionService.getQuestionsBySubjectId(this.subject.id)
      .subscribe({
        next: (questions) => {
          this.questions = questions;
          this.isQuestionSelected = this.questions.length > 0;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading questions:', error);
          this.isLoading = false;
        }
      });
  }

  onSelect(question: QuestionResponse) {
    this.questionSelected.emit(question);
  }
}
