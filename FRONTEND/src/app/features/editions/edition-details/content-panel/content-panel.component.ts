import {Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges} from '@angular/core';
import {SubjectResponse} from '../../../../models/subject.interface';
import {QuestionResponse} from '../../../../models/question.interface';
import {QuestionService} from '../../../../core/services/question.service';
import {CommonModule} from '@angular/common';
import {QuestionListComponent} from './question/question-list.component';
import {AnswerListComponent} from './answer/answer-list.component';

@Component({
  selector: 'app-content-panel',
  imports: [
    CommonModule,
    QuestionListComponent,
    AnswerListComponent
  ],
  templateUrl: './content-panel.component.html'
})
export class ContentPanelComponent implements OnChanges {
  @Input() selectedSubject: SubjectResponse | null = null;
  selectedQuestion: QuestionResponse | null = null;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedSubject']) {
      this.selectedQuestion = null;
    }
  }

  onQuestionSelect(question: QuestionResponse) {
    this.selectedQuestion = question;
  }
}
