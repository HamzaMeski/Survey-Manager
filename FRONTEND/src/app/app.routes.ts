// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { SurveyListComponent } from './features/surveys/survey-list/survey-list.component';
import { EditionDetailsComponent } from './features/editions/edition-details/edition-details.component';
import { HomeComponent } from './features/home/home.component';
import { UnauthorizedComponent } from './shared/components/unauthorized/unauthorized.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'surveys',
    component: SurveyListComponent,
    canActivate: [authGuard],
    data: { permission: 'view' }
  },
  {
    path: 'editions/:id',
    component: EditionDetailsComponent,
    canActivate: [authGuard],
    data: { permission: 'view' }
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  }
];
