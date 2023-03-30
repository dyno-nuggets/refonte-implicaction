import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Post} from '../../../../discussion/model/post';
import {Univers} from '../../../../shared/enums/univers';
import {Constants} from '../../../../config/constants';


@Component({
  selector: 'app-latest-post-card',
  templateUrl: './latest-post-card.component.html',
  styleUrls: ['./latest-post-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestPostCardComponent {

  @Input()
  post: Post;
  univers = Univers;
  constant = Constants;
}
