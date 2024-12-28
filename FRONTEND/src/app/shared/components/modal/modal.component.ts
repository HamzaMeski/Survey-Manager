import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-modal',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity z-40"></div>
        
        <div class="fixed inset-0 z-50 overflow-y-auto">
            <div class="flex min-h-full items-center justify-center p-4">
                <div class="bg-white rounded-lg shadow-xl w-full max-w-2xl">
                    <!-- Header -->
                    <div class="px-6 py-4 border-b">
                        <h3 class="text-xl font-semibold">{{title}}</h3>
                    </div>

                    <!-- Content -->
                    <div class="px-6 py-4">
                        <ng-content></ng-content>
                    </div>

                    <!-- Optional Footer -->
                    <div *ngIf="showFooter" class="px-6 py-4 border-t flex justify-end space-x-4">
                        <button 
                            (click)="onClose.emit()"
                            class="px-4 py-2 text-gray-700 hover:text-gray-900">
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `
})
export class ModalComponent {
    @Input() title: string = '';
    @Input() showFooter: boolean = true;
    @Output() onClose = new EventEmitter<void>();
}
