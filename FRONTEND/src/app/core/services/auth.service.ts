import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type UserRole = 'ADMIN' | 'EDITOR' | 'VIEWER';
export type Permission = 'create' | 'edit' | 'delete' | 'view';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<UserRole | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  login(role: UserRole): void {
    this.currentUserSubject.next(role);
  }

  logout(): void {
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!this.currentUserSubject.value;
  }

  hasPermission(permission: Permission): boolean {
    const role = this.currentUserSubject.value;
    if (!role) return false;

    switch (role) {
      case 'ADMIN':
        return true;
      case 'EDITOR':
        return ['create', 'edit', 'view'].includes(permission);
      case 'VIEWER':
        return permission === 'view';
      default:
        return false;
    }
  }
}
