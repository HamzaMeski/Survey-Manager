import { Routes } from '@angular/router';
import { SurveyListComponent } from './features/surveys/survey-list/survey-list.component';
import { SurveyCreateComponent } from './features/surveys/survey-create/survey-create.component';

export const routes: Routes = [
    {path: '', redirectTo: '/surveys', pathMatch: 'full'},
    {path: 'surveys', component: SurveyListComponent},
    { path: 'surveys/create', component: SurveyCreateComponent }

];
