import {Component, Input} from '@angular/core';
import {Post} from '../../model/post';

@Component({
  selector: 'app-votebox',
  templateUrl: './votebox.component.html',
  styleUrls: ['./votebox.component.scss']
})
export class VoteboxComponent {

  @Input()
  post: Post = {};

}
