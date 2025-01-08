// src/app/features/home/home.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  template: `
    <div class="min-h-screen bg-gray-100 p-8">
      <div class="max-w-2xl mx-auto bg-white rounded-lg shadow p-6">
        <h1 class="text-3xl font-bold mb-6">Welcome to Survey System</h1>

        <div class="flex gap-2">
          <button (click)="loginAsAdmin()" class="px-4 py-2 bg-blue-500 text-white rounded">
            Login as Admin
          </button>
          <button (click)="loginAsEditor()" class="px-4 py-2 bg-green-500 text-white rounded">
            Login as Editor
          </button>
          <button (click)="loginAsViewer()" class="px-4 py-2 bg-yellow-500 text-white rounded">
            Login as Viewer
          </button>
        </div>
      </div>
    </div>
  `,
  standalone: true
})
export class HomeComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  loginAsAdmin() {
    this.authService.login('ADMIN');
    this.router.navigate(['/surveys']);
  }

  loginAsEditor() {
    this.authService.login('EDITOR');
    this.router.navigate(['/surveys']);
  }

  loginAsViewer() {
    this.authService.login('VIEWER');
    this.router.navigate(['/surveys']);
  }
}
