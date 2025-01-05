import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionResponse} from '../../../../../models/question.interface';
import {SubjectResponse} from '../../../../../models/subject.interface';
import {QuestionService} from '../../../../../core/services/question.service';
import {InputComponent} from '../../../../../shared/components/input/input.component';
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
  @Output() questionSelected = new EventEmitter<QuestionResponse>();

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

  hasSubSubjects(): boolean {
    return this.subject.subSubjects.length > 0
  }
}
