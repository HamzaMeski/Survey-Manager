import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionResponse} from '../../../../../models/question.interface';
import {AnswerResponse} from '../../../../../models/answer.interface';
import {AnswerService} from '../../../../../core/services/answer.service';

@Component({
  selector: 'app-answer-list',
  imports: [
    CommonModule
  ],
  templateUrl: './answer-list.component.html'
})
export class AnswerListComponent implements OnInit {
  @Input() question: QuestionResponse  | null = null
  @Output() backToQuestions = new EventEmitter<void>();

  answers: AnswerResponse[] = [];
  isLoading = false;

  constructor(private answerService: AnswerService) {}

  ngOnInit() {
    this.loadAnswers();
  }

  loadAnswers() {
    if (!this.question) return;

    this.isLoading = true;
    this.answerService.getAnswersByQuestion(this.question.id)
      .subscribe({
        next: (answers) => {
          this.answers = answers;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading answers:', error);
          this.isLoading = false;
        }
      });
  }

  goBackToQuestions() {
    this.backToQuestions.emit();
  }
}
