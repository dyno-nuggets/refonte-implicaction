import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ForumTableTypesEnum } from './enums/table-type-enum';

@Component({
  selector: 'app-forums',
  templateUrl: './forums.component.html',
  styleUrls: ['./forums.component.scss'],
})
export class ForumsComponent implements OnInit {
  constructor() {}

  tableType = ForumTableTypesEnum;
  ngOnInit(): void {
    return;
  }
}
