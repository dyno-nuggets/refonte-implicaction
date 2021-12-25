import {Component, OnInit} from '@angular/core';
import {Post} from 'src/app/discussion/model/post';
import {PostService} from 'src/app/discussion/services/post.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  latestPosts: Post[];

  constructor(
    private postService: PostService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.postService
      .getLatestPosts(Constants.LATEST_POSTS_COUNT)
      .subscribe(
        posts => this.latestPosts = posts,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données')
      );
  }

  trackByPostId = (index: number, post: Post) => post.id;

}
