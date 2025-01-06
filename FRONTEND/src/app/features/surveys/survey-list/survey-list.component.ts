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
  showCreateSurveyModal = false;
  showEditSurveyModal = false;
  showDeleteSurveyModal = false;
  showEditionsModal = false;
  selectedSurvey: SurveyResponse | null = null;

  showCreateEditionModal: boolean = false

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
    this.showEditSurveyModal = true;
  }

  onDeleteClick(survey: SurveyResponse): void {
    this.selectedSurvey = survey;
    this.showDeleteSurveyModal = true;
  }

  openEditionsModal(survey: SurveyResponse): void {
    this.selectedSurvey = survey;
    this.showEditionsModal = true;
  }

  confirmDelete(): void {
    if (this.selectedSurvey) {
      this.surveyService.deleteSurvey(this.selectedSurvey.id)
        .subscribe({
          next: () => {
            this.showDeleteSurveyModal = false;
            this.selectedSurvey = null;
            this.loadSurveys();
          },
          error: (error) => console.error('Error deleting survey:', error)
        });
    }
  }

  onSurveyEdited(): void {
    this.showEditSurveyModal = false;
    this.selectedSurvey = null;
    this.loadSurveys();
  }

  onSurveyCreated(): void {
    this.showCreateSurveyModal = false;
    this.loadSurveys();
  }
}
