import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SubjectResponse} from '../../../../../../models/subject.interface';
import {AnswerResponse} from '../../../../../../models/answer.interface';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AnswerService} from '../../../../../../core/services/answer.service';

@Component({
  selector: 'app-edit-answer',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-answer.component.html'
})
export class EditAnswerComponent implements OnInit{
  @Input() answer!: AnswerResponse
  @Output() answerEdited: EventEmitter<void> = new EventEmitter<void>()
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

  ngOnInit(): void {
    if(this.answer) {
      this.answerForm.patchValue({
        text: this.answer.text
      })
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.answerForm.get(fieldName);
    return field!.invalid && ( field!.touched) || false;
  }

  onSubmit(): void {
    if(this.answerForm.valid) {
      console.log(this.answerForm.value)
      this.answerService.updateAnswer(this.answer.id, this.answerForm.value)
        .subscribe({
          next: (): void => {
            this.answerEdited.emit()
          },
          error: (object) => {
            console.error('Error creating answer: ', object)
            this.serverValidationMessage = object.error.message
          }
        })
    }
  }
}
