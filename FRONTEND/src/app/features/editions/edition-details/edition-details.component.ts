import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {SubjectResponse} from '../../../models/subject.interface';
import {SubjectService} from '../../../core/services/subject.service';
import {SubjectTreeComponent} from './subject-tree/subject-tree.component';
import {ContentPanelComponent} from './content-panel/content-panel.component';
import {EditionResponse} from '../../../models/edition.interface';
import {EditionService} from '../../../core/services/edition.service';

@Component({
    selector: 'app-edition-details',
    standalone: true,
    imports: [
      CommonModule,
      RouterModule,
      SubjectTreeComponent,
      ContentPanelComponent
    ],
    templateUrl: './edition-details.component.html'
})
export class EditionDetailsComponent implements OnInit {
  editionId!: number
  edition: EditionResponse | null = null
  selectedSubject:SubjectResponse | null = null

  constructor(
    private subjectService: SubjectService,
    private editionService: EditionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.getCurrentEditionId()
    this.getCurrentEdition()
  }

  getCurrentEditionId() {
    this.route.params.subscribe(params => {
      this.editionId = +params['id']
    });
  }

  getCurrentEdition(): void {
    this.editionService.getEditionById(this.editionId)
      .subscribe({
        next: (edition): void => {
          this.edition = edition
        },
        error: (object): void => {
          console.error('Error while getting edition: ', object.error.message)
        }
      })
  }

  onSubjectSelect(subject: SubjectResponse) {
    this.selectedSubject = subject
  }
}
