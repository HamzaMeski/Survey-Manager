import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AnswerService} from '../../../../../../core/services/answer.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {QuestionResponse} from '../../../../../../models/question.interface';

@Component({
  selector: 'app-add-answer',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-answer.component.html'
})
export class AddAnswerComponent {
  @Input() question!: QuestionResponse
  @Output() answerCreated: EventEmitter<void> = new EventEmitter<void>()
  @Output() onClose: EventEmitter<void> = new EventEmitter<void>()
  answerForm!: FormGroup
  serverValidationMessage!: string

  constructor(
    private fb: FormBuilder,
    private answerService: AnswerService
  ) {
    this.createForm()
  }

  createForm(): void {
    this.answerForm = this.fb.group({
      text: ['', [Validators.required, Validators.minLength(3)]]
    })
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.answerForm.get(fieldName);
    return field!.invalid && ( field!.touched) || false;
  }

  onSubmit(): void {
    if(this.answerForm.valid) {
      console.log(this.answerForm.value)
      this.answerService.createAnswer(this.question.id, this.answerForm.value)
        .subscribe({
          next: (): void => {
            this.answerCreated.emit()
          },
          error: (object) => {
            this.serverValidationMessage = object.error.message
          }
        })
    }
  }
}
