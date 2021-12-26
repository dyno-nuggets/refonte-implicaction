import {Component, OnInit} from '@angular/core';
import {Post} from '../../model/post';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {PostService} from '../../services/post.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {Criteria} from '../../../shared/models/Criteria';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent extends BaseWithPaginationAndFilterComponent<Post, Criteria> implements OnInit {

  constructor(
    private postService: PostService,
    private toastService: ToasterService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.pageable.sortOrder = 'DESC';
    this.paginate();
  }

  protected innerPaginate(): void {
    this.postService
      .getAllPosts(this.pageable)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des discussions')
      );
  }

}
