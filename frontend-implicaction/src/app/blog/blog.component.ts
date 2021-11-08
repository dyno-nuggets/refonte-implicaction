import {Component, OnInit} from '@angular/core';
import {Post} from './model/post';
import {PostService} from './services/post.service';
import {finalize} from 'rxjs/operators';
import {Constants} from '../config/constants';
import {ToasterService} from '../core/services/toaster.service';
import {Pageable} from '../shared/models/pageable';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.scss']
})
export class BlogComponent implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  posts: Post[] = [];
  isLoading = true;

  // Pagination
  pageable: Pageable<Post> = Constants.PAGEABLE_DEFAULT;

  constructor(
    private postService: PostService,
    private toastService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.pageable.sortOrder = 'DESC';
    this.paginate();
  }

  paginate({page, first, rows} = this.pageable): void {
    this.isLoading = true;
    this.pageable.page = page;
    this.pageable.first = first;
    this.pageable.rows = rows;
    this.postService
      .getAllPosts(this.pageable)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des offres')
      );
  }
}
