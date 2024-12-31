import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-edition-details',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './edition-details.component.html'
})
export class EditionDetailsComponent implements OnInit {
  editionId: number | null = null
  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.editionId = +params['id']
      console.log(params)
      console.log(params['id'])
      // We'll add loading logic later
    });
  }
}
