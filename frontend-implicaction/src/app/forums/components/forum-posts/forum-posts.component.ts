import {Component, OnInit} from '@angular/core';
import {ForumTableTypesEnum} from "../../enums/table-type-enum";

@Component({
  selector: 'app-forum-posts',
  templateUrl: './forum-posts.component.html',
  styleUrls: ['./forum-posts.component.scss']
})
export class ForumPostsComponent implements OnInit {

  tableType = ForumTableTypesEnum;

  constructor() {
  }

  ngOnInit(): void {
  }

}
