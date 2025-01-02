import { Routes } from '@angular/router';
import { SurveyListComponent } from './features/surveys/survey-list/survey-list.component';
import {EditionDetailsComponent} from './features/edition/edition-details.component';

export const routes: Routes = [
  {path: '', redirectTo: '/surveys', pathMatch: 'full'},
  {path: 'surveys', component: SurveyListComponent},
  {path: 'editions/:id', component: EditionDetailsComponent}
];
