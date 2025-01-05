import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SubjectResponse} from '../../../../../../models/subject.interface';

@Component({
  selector: 'app-add-question',
  imports: [
    CommonModule,
  ],
  templateUrl: './add-question.component.html'
})
export class AddQuestionComponent {
  @Input() subject!: SubjectResponse

}
