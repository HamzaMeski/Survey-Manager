import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionResponse} from '../../../../../models/question.interface';
import {AnswerResponse} from '../../../../../models/answer.interface';
import {AnswerService} from '../../../../../core/services/answer.service';
import {AddAnswerComponent} from './add-question/add-answer.component';
import {EditAnswerComponent} from './edit-question/edit-answer.component';
import {EditQuestionComponent} from '../question/edit-question/edit-question.component';

@Component({
  selector: 'app-answer-list',
  imports: [
    CommonModule,
    AddAnswerComponent,
    EditAnswerComponent,
    EditQuestionComponent
  ],
  templateUrl: './answer-list.component.html'
})
export class AnswerListComponent implements OnInit {
  @Input() question!: QuestionResponse
  @Output() backToQuestions = new EventEmitter<void>();

  clickedAnswerForEdit!: AnswerResponse
  clickedAnswerForDelete!: AnswerResponse
  displayAddAnswerForm: boolean = false
  displayEditAnswerForm: boolean = false
  confirmDeletion: boolean = false

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

  toggleAddQuestionInput(): void {
    this.displayAddAnswerForm? this.displayAddAnswerForm = false : this.displayAddAnswerForm = true
  }

  onDelete(answer: AnswerResponse): void {
    this.answerService.deleteAnswer(answer.id)
      .subscribe({
        next: (): void => {
          this.confirmDeletion = true
          this.loadAnswers()
        },
        error: (error): void => {
          console.log('Error deleting answer: ', error)
        }
      })
  }
}
