import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SurveyService } from '../../../core/services/survey.service';
import { SurveyResponse } from '../../../models/survey.interface';

@Component({
  selector: 'app-survey-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container mx-auto px-4 py-8">
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold">Surveys</h1>
        <button 
          [routerLink]="['/surveys/create']"
          class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded">
          Create Survey
        </button>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div *ngFor="let survey of surveys" 
             class="border rounded-lg p-4 hover:shadow-lg transition-shadow">
          <h2 class="text-xl font-semibold mb-2">{{survey.title}}</h2>
          <p class="text-gray-600 mb-4">{{survey.description}}</p>
          <div class="flex justify-end space-x-2">
            <button 
              [routerLink]="['/surveys', survey.id]"
              class="text-blue-500 hover:text-blue-600">
              Edit
            </button>
            <button 
              (click)="deleteSurvey(survey.id)"
              class="text-red-500 hover:text-red-600">
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>
  `
})
export class SurveyListComponent implements OnInit {
  surveys: SurveyResponse[] = [];

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
}