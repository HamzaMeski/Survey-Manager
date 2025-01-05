import { Routes } from '@angular/router';
import { SurveyListComponent } from './features/surveys/survey-list/survey-list.component';
import {EditionComponent} from './features/edition/edition.component';

export const routes: Routes = [
  {path: '', redirectTo: '/surveys', pathMatch: 'full'},
  {path: 'surveys', component: SurveyListComponent},
  {path: 'editions/:id', component: EditionComponent}
];
