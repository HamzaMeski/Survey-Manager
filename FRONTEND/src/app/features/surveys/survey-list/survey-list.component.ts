import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SurveyService } from '../../../core/services/survey.service';
import { SurveyResponse } from '../../../models/survey.interface';
import { SurveyCreateComponent } from '../survey-create/survey-create.component';
import { SurveyEditComponent } from '../survey-edit/survey-edit.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ConfirmationModalComponent } from '../../../shared/components/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-survey-list',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    SurveyCreateComponent, 
    SurveyEditComponent, 
    ModalComponent,
    ConfirmationModalComponent
  ],
  templateUrl: './survey-list.component.html'
})
export class SurveyListComponent implements OnInit {
  surveys: SurveyResponse[] = [];
  showCreateModal = false;
  showEditModal = false;
  showDeleteModal = false;
  selectedSurvey: SurveyResponse | null = null;

  constructor(private surveyService: SurveyService) {}

  ngOnInit(): void {
    this.loadSurveys();
  }

  loadSurveys(): void {
    this.surveyService.getAllSurveys()
      .subscribe({
        next: (surveys) => this.surveys = surveys,
        error: (error) => console.error('Error loading surveys:', error)
      });
  }

  onEditClick(survey: SurveyResponse): void {
    this.selectedSurvey = survey;
    this.showEditModal = true;
  }

  onDeleteClick(survey: SurveyResponse): void {
    this.selectedSurvey = survey;
    this.showDeleteModal = true;
  }

  confirmDelete(): void {
    if (this.selectedSurvey) {
      this.surveyService.deleteSurvey(this.selectedSurvey.id)
        .subscribe({
          next: () => {
            this.showDeleteModal = false;
            this.selectedSurvey = null;
            this.loadSurveys();
          },
          error: (error) => console.error('Error deleting survey:', error)
        });
    }
  }

  onSurveyEdited(): void {
    this.showEditModal = false;
    this.selectedSurvey = null;
    this.loadSurveys();
  }

  onSurveyCreated(): void {
    this.showCreateModal = false;
    this.loadSurveys();
  }
}