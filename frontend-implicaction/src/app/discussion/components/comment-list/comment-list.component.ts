import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../../shared/services/auth.service';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {PostService} from '../../services/post.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {finalize} from 'rxjs/operators';
import {Comment} from '../../model/comment';
import {BaseWithPaginationComponent} from '../../../shared/components/base-with-pagination-component/base-with-pagination.component';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss']
})
export class CommentListComponent extends BaseWithPaginationComponent<Comment> implements OnInit, OnDestroy {

  currentUserImageUrl = Constants.USER_DEFAULT_IMAGE_SRC;
  subscription: Subscription;
  postId: string;

  constructor(
    private authService: AuthService,
    private postService: PostService,
    private route: ActivatedRoute,
    private toasterService: ToasterService
  ) {
    super();
  }

  ngOnInit(): void {
    this.currentUserImageUrl = this.authService.getCurrentUser().imageUrl ?? Constants.USER_DEFAULT_IMAGE_SRC;
    this.subscription = this.route.paramMap.subscribe(paramMap => {
      this.postId = paramMap.get('postId');
      this.paginate();
    });
  }

  trackCommentById = (index: number, comment: Comment) => comment.id;

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  protected innerPaginate(): void {
    this.postService
      .getCommentsByPostId(this.pageable, this.postId)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la récupération des commentaires')
      );
  }
}
