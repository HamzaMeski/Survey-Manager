import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SubjectResponse} from '../../../../models/subject.interface';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-subject-tree-item',
  imports: [CommonModule],
  templateUrl: './subject-tree-item.component.html'
})
export class SubjectTreeItemComponent {
  @Input() subject!: SubjectResponse;
  @Input() level: number = 0;
  @Output() select = new EventEmitter<SubjectResponse>();

  isExpanded: boolean = false;

  get hasSubSubjects(): boolean {
    return this.subject.subSubjects && this.subject.subSubjects.length > 0;
  }

  get isLastLevel(): boolean {
    return !this.hasSubSubjects;
  }

  get paddingLeft(): string {
    return `${this.level * 1.5}rem`;
  }

  toggleExpand(event: Event) {
    event.stopPropagation();
    if (this.hasSubSubjects) {
      this.isExpanded = !this.isExpanded;
    }
  }

  onSelect(event: Event) {
    event.stopPropagation();
    this.select.emit(this.subject);
  }
}
