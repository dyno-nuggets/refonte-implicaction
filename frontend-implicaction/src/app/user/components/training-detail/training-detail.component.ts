import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Training} from '../../../shared/models/training';

@Component({
  selector: 'app-training-detail',
  templateUrl: './training-detail.component.html',
  styleUrls: ['./training-detail.component.scss']
})
export class TrainingDetailComponent implements OnInit {


  @Input()
  training: Training;
  @Input()
  isEditing: boolean;

  @Output()
  deleteEvent = new EventEmitter<Training>();

  constructor() {
  }

  ngOnInit(): void {
  }

  deleteTraining(training: Training): void {
    this.deleteEvent.next(training);
  }

}
