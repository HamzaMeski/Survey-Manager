import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SurveyService } from '../../../core/services/survey.service';
import { SurveyResponse } from '../../../models/survey.interface';
import { SurveyCreateComponent } from '../survey-create/survey-create.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';

@Component({
  selector: 'app-survey-list',
  standalone: true,
  imports: [CommonModule, RouterModule, SurveyCreateComponent, ModalComponent],
  templateUrl: './survey-list.component.html'
})
export class SurveyListComponent implements OnInit {
  surveys: SurveyResponse[] = [];
  showCreateModal = false;

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

  deleteSurvey(id: number): void {
    if (confirm('Are you sure you want to delete this survey?')) {
      this.surveyService.deleteSurvey(id)
        .subscribe({
          next: () => this.loadSurveys(),
          error: (error) => console.error('Error deleting survey:', error)
        });
    }
  }

  onSurveyCreated() {
    this.showCreateModal = false;
    this.loadSurveys();
  }
}