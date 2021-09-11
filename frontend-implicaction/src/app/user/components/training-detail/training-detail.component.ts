import {Component, Input, OnInit} from '@angular/core';
import {Training} from '../../../shared/models/training';

@Component({
  selector: 'app-training-detail',
  templateUrl: './training-detail.component.html',
  styleUrls: ['./training-detail.component.scss']
})
export class TrainingDetailComponent implements OnInit {

  @Input()
  training: Training;

  constructor() {
  }

  ngOnInit(): void {
  }

}
