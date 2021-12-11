import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.scss']
})
export class EventCardComponent implements OnInit {
  @Input()
  event: { date: Date, description: string };

  // Should take a real Event object when ready

  constructor() {
  }

  ngOnInit(): void {
  }

}
