import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {SubjectResponse} from '../../models/subject.interface';
import {SubjectService} from '../../core/services/subject.service';
import {SubjectTreeComponent} from './subject-tree/subject-tree.component';
import {ContentPanelComponent} from './content-panel/content-panel.component';
import {EditionResponse} from '../../models/edition.interface';
import {EditionService} from '../../core/services/edition.service';

@Component({
    selector: 'app-edition',
    standalone: true,
    imports: [
      CommonModule,
      SubjectTreeComponent,
      ContentPanelComponent
    ],
    templateUrl: './edition.component.html'
})
export class EditionComponent implements OnInit {
  editionId!: number
  selectedSubject!:SubjectResponse
  edition!:EditionResponse

  constructor(
    private subjectService:SubjectService,
    private editionService:EditionService,
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

  getCurrentEdition() {
    this.editionService.getEditionById(this.editionId)
      .subscribe({
        next: (edition: EditionResponse)=>{
          this.edition = edition
        }
      })
  }

  onSubjectSelect(subject: SubjectResponse) {
    this.selectedSubject = subject
  }
}
