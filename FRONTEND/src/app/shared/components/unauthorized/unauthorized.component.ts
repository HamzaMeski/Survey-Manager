// src/app/shared/components/unauthorized/unauthorized.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-unauthorized',
  template: `
    <div class="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div class="p-8 bg-white rounded-lg shadow-md">
        <h1 class="text-2xl font-bold text-red-600 mb-4">Unauthorized Access</h1>
        <p class="text-gray-600">You don't have permission to access this resource.</p>
        <button
          (click)="goBack()"
          class="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
          Go Back
        </button>
      </div>
    </div>
  `,
  standalone: true
})
export class UnauthorizedComponent {
  goBack() {
    window.history.back();
  }
}
