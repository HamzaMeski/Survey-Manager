import {Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges} from '@angular/core';
import {SubjectResponse} from '../../../../models/subject.interface';
import {QuestionResponse} from '../../../../models/question.interface';
import {QuestionService} from '../../../../core/services/question.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-content-panel',
  imports: [CommonModule],
  templateUrl: './content-panel.component.html'
})
export class ContentPanelComponent implements OnChanges {
  @Input() selectedSubject: SubjectResponse | null = null;

  questions: QuestionResponse[] = [];
  isLoading = false;

  constructor(private questionService: QuestionService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedSubject'] && this.selectedSubject) {
      this.loadQuestions();
    }
  }

  loadQuestions() {
    if (!this.selectedSubject) return;

    this.isLoading = true;
    this.questionService.getQuestionsBySubjectId(this.selectedSubject.id)
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
}
