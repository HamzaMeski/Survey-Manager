import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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

    constructor(
        private fb: FormBuilder,
        private surveyService: SurveyService,
        private router: Router
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
                        // Navigate back to survey list on success
                        this.router.navigate(['/surveys']);
                    },
                    error: (error) => {
                        console.error('Error creating survey:', error);
                        this.isSubmitting = false;
                        // Here you might want to show an error message to the user
                    }
                });
        }
    }

    onCancel(): void {
        this.router.navigate(['/surveys']);
    }
}