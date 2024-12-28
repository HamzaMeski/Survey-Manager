import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SurveyService } from '../../../core/services/survey.service';
import { SurveyResponse } from '../../../models/survey.interface';

@Component({
    selector: 'app-survey-edit',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './survey-edit.component.html'
})
export class SurveyEditComponent implements OnInit {
    surveyForm!: FormGroup;
    isSubmitting = false;

    @Input() survey!: SurveyResponse;  // Survey to edit
    @Output() surveyEdited = new EventEmitter<void>();

    constructor(
        private fb: FormBuilder,
        private surveyService: SurveyService
    ) {
        this.createForm();
    }

    ngOnInit() {
        if (this.survey) {
            this.surveyForm.patchValue({
                title: this.survey.title,
                description: this.survey.description,
                status: this.survey.status,
                category: this.survey.category,
                targetAudience: this.survey.targetAudience,
                ownerId: this.survey.owner.id
            });
        }
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
            
            this.surveyService.updateSurvey(this.survey.id, this.surveyForm.value)
                .subscribe({
                    next: () => {
                        this.surveyEdited.emit();
                    },
                    error: (error) => {
                        console.error('Error updating survey:', error);
                        this.isSubmitting = false;
                    }
                });
        }
    }
}
