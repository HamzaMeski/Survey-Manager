import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {SubjectResponse} from '../../../models/subject.interface';
import {SubjectService} from '../../../core/services/subject.service';

@Component({
    selector: 'app-edition-details',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './edition-details.component.html'
})
export class EditionDetailsComponent implements OnInit {
  editionId: number | null = null
  selectedSubject:SubjectResponse | null = null

  constructor(private subjectService:SubjectService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.getCurrentEditionId()
  }

  getCurrentEditionId() {
    this.route.params.subscribe(params => {
      this.editionId = +params['id']
    });
  }

  onSubjectSelect(subject: SubjectResponse) {
    this.selectedSubject = subject
  }
}
