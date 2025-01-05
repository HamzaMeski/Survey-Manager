import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SubjectResponse} from '../../../../../../models/subject.interface';
import {QuestionResponse} from '../../../../../../models/question.interface';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {QuestionService} from '../../../../../../core/services/question.service';

@Component({
  selector: 'app-edit-question',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-question.component.html'
})
export class EditQuestionComponent implements OnInit{
  @Input() question!: QuestionResponse
  @Output() questionEdited: EventEmitter<void> = new EventEmitter<void>()
  @Output() onClose: EventEmitter<void> = new EventEmitter<void>()

  questionForm!: FormGroup
  serverValidationMessage!: string

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

  ngOnInit(): void {
    if(this.question) {
      this.questionForm.patchValue({
        text: this.question.text,
        instructions: this.question.instructions,
        required: this.question.required,
        type: this.question.type
      })
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.questionForm.get(fieldName);
    return field!.invalid && ( field!.touched) || false;
  }

  onSubmit(): void {
    if(this.questionForm.valid) {
      console.log(this.questionForm.value)
      this.questionService.updateQuestion(this.question.id, this.questionForm.value)
        .subscribe({
          next: (): void => {
            this.questionEdited.emit()
          },
          error: (object) => {
            console.error('Error creating question: ', object)
            this.serverValidationMessage = object.error.message
          }
        })
    }
  }
}
