import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-modal',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './modal.component.html'
})
export class ModalComponent {
    @Input() title: string = '';
    @Input() showFooter: boolean = true;
    @Output() onClose = new EventEmitter<void>();
}
