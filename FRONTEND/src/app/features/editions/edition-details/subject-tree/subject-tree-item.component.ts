import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SubjectResponse} from '../../../../models/subject.interface';
import {CommonModule} from '@angular/common';
import {ModalComponent} from '../../../../shared/components/modal/modal.component';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {SubjectService} from '../../../../core/services/subject.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-subject-tree-item',
  imports: [
    CommonModule,
    ModalComponent,
    ReactiveFormsModule
  ],
  templateUrl: './subject-tree-item.component.html'
})
export class SubjectTreeItemComponent implements OnInit{
  @Input() subject!: SubjectResponse
  @Input() level: number = 0
  @Output() select = new EventEmitter<SubjectResponse>()

  isExpanded: boolean = false
  showModal: boolean = false
  subjectForm!: FormGroup
  surveyEditionId!: number

  constructor(
    private fb: FormBuilder,
    private subjectService: SubjectService,
    private route: ActivatedRoute
  ) {
    this.createForm()
  }

  ngOnInit(): void {
    this.getSurveyEditionId()
  }

  getSurveyEditionId(): void {
    this.route.params.subscribe(params => {
      this.surveyEditionId = Number(params['id'])
    })
  }

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

  createForm(): void {
    this.subjectForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      parentSubjectId: ['']
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.subjectForm.get(fieldName);
    return field!.invalid && (field!.dirty || field!.touched) || false;
  }

  onSubmit(): void {
    if(this.subjectForm.valid) {
      console.log('surveyEditionId' ,this.surveyEditionId)

      this.subjectForm.patchValue({parentSubjectId: this.subject.id})
      this.subjectService.createSubject(this.surveyEditionId, this.subjectForm.value).subscribe({
        next: ():void => {
          this.showModal = false
        },
        error: (object): void => {
          console.error('Error while create subject ', object)
        }
      })
    }
  }
}
