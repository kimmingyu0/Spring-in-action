import { Component, Input } from '@angular/core';

@Component({
  selector: 'big-button',
  templateUrl: './bigbutton.component.html',
  styleUrls: ['./bigbutton.component.css']
})
export class BigButtonComponent {
  @Input() label: string = '';

  constructor() { }
}

