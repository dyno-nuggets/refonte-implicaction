import { Component } from '@angular/core';
import {
  ForumTableTypeCode,
  ForumTableTypesEnum,
} from './enums/table-type-enum';

@Component({
  selector: 'app-forums',
  templateUrl: './forums.component.html',
  styleUrls: ['./forums.component.scss'],
})
export class ForumsComponent {
  forumType: ForumTableTypesEnum = ForumTableTypesEnum.FORUM;
  postType: ForumTableTypesEnum = ForumTableTypesEnum.POST;
}
