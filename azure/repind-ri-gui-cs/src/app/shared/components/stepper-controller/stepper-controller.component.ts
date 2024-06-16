import { MatStepper } from '@angular/material/stepper';
import { Component, OnInit, Input, HostListener } from '@angular/core';

@Component({
  selector: 'app-stepper-controller',
  templateUrl: './stepper-controller.component.html',
  styleUrls: ['./stepper-controller.component.scss']
})
export class StepperControllerComponent implements OnInit {

  @Input() stepper: MatStepper;
  @Input() steps: number;

  constructor() { }

  ngOnInit() {
  }

}
