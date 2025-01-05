import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionService} from '../../../../../../core/services/question.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {SubjectResponse} from '../../../../../../models/subject.interface';

@Component({
  selector: 'app-add-question',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-question.component.html'
})
export class AddQuestionComponent {
  @Input() subject!: SubjectResponse
  @Output() questionCreated = new EventEmitter<void>()
  questionForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private questionService:QuestionService
  ) {
    this.createForm()
  }

  createForm(): void {
    this.questionForm = this.fb.group({
      text: ['', [Validators.required, Validators.minLength(3)]],
      type: ['SINGLE_CHOICE'],
      required: ['true'],
      instructions: ['', Validators.minLength(3)]
    })
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.questionForm.get(fieldName);
    return field!.invalid && ( field!.touched) || false;
  }

  onSubmit(): void {
    if(this.questionForm.valid) {
      console.log(this.questionForm.value)
      this.questionService.createQuestion(this.subject.id, this.questionForm.value)
        .subscribe({
          next: (): void => {
            this.questionCreated.emit()
          },
          error: (error) => {
            console.error('Error creating question: ', error)
          }
        })
    }
  }
}
