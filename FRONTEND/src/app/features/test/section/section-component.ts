import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ModalComponent} from '../modal/modal-component';


@Component({
  selector: 'app-section',
  imports: [
    CommonModule,
    ModalComponent
  ],
  templateUrl: './section-component.html'
})
export class SectionComponent {
  openModal: boolean  = false

  onClick():boolean {
    return this.openModal
  }
}
