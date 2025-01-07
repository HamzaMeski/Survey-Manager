import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SurveyService } from '../../../core/services/survey.service';

@Component({
    selector: 'app-survey-create',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './survey-create.component.html'
})
export class SurveyCreateComponent {
    surveyForm!: FormGroup;
    isSubmitting = false;

    @Output() surveyCreated = new EventEmitter<void>();

    constructor(
        private fb: FormBuilder,
        private surveyService: SurveyService
    ) {
        this.createForm();
    }

    private createForm(): void {
        this.surveyForm = this.fb.group({
            title: ['', [Validators.required, Validators.minLength(3)]],
            description: ['', [Validators.required, Validators.minLength(10)]],
            status: ['INACTIVE'],
            ownerId: ['', [Validators.required]],
            category: [''],
            targetAudience: ['']
        });
    }

    isFieldInvalid(fieldName: string): boolean {
        const field = this.surveyForm.get(fieldName);
        return field!.invalid && (field!.dirty || field!.touched) || false;
    }

    onSubmit(): void {
        if (this.surveyForm.valid) {
            this.isSubmitting = true;

            this.surveyService.createSurvey(this.surveyForm.value)
                .subscribe({
                    next: () => {
                        this.surveyCreated.emit();
                    },
                    error: (object) => {
                        console.error('Error creating survey:', object);
                        this.isSubmitting = false;
                    }
                });
        }
    }
}
