import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionResponse} from '../../../../../models/question.interface';
import {SubjectResponse} from '../../../../../models/subject.interface';
import {QuestionService} from '../../../../../core/services/question.service';
import {AddQuestionComponent} from './add-question/add-question.component';
import {EditQuestionComponent} from './edit-question/edit-question.component';

@Component({
  selector: 'app-question-list',
  imports: [
    CommonModule,
    AddQuestionComponent,
    EditQuestionComponent
  ],
  templateUrl: './question-list.component.html'
})
export class QuestionListComponent implements OnInit, OnChanges {
  @Input() subject!: SubjectResponse
  @Output() questionSelected = new EventEmitter<QuestionResponse>()

  questions: QuestionResponse[] = []
  isLoading: boolean = false
  isAddQuestionClicked: boolean = false
  confirmDeletion :boolean = false
  clickedQuestion!: QuestionResponse

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
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading questions:', error);
          this.isLoading = false;
        }
      });
  }

  onSelect(question: QuestionResponse): void {
    this.questionSelected.emit(question);
  }

  hasSubSubjects(): boolean {
    return this.subject.subSubjects.length > 0
  }

  toggleAddQuestionInput(): void {
     this.isAddQuestionClicked? this.isAddQuestionClicked = false : this.isAddQuestionClicked = true
  }

  onDelete(question: QuestionResponse): void {
    this.questionService.deleteQuestion(question.id)
      .subscribe({
        next: (): void => {
          this.confirmDeletion = true
          this.loadQuestions()
        },
        error: (error): void => {
          console.log('Error deleting question: ', error)
        }
      })
  }
}
