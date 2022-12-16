import { Component,Input, OnInit } from '@angular/core';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Group} from '../../model/group';
import {Criteria} from '../../../shared/models/Criteria';
import {ActivatedRoute} from '@angular/router';
import {GroupService} from '../../services/group.service';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import { PostService } from '../../services/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-custom-table-with-search-bar',
  templateUrl: './custom-table-with-search-bar.component.html',
  styleUrls: ['./custom-table-with-search-bar.component.scss']
})
export class CustomTableWithSearchBarComponent extends BaseWithPaginationAndFilterComponent<Group, Criteria> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = [5];
  isLoading = true;
  title: string;
  labels: string[];
  posts: Post[];

  constructor(
    private toastService: ToasterService,
    private groupService: GroupService,
    private postService: PostService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
          this.title=data.title;
          this.labels=data.labels;
      })
    this.pageable.rowsPerPages = this.ROWS_PER_PAGE_OPTIONS;
    this.pageable.rows = this.ROWS_PER_PAGE_OPTIONS[0];
    this.paginate();
  }

  protected innerPaginate(): void {
    if(this.title === "Forums"){
    this.groupService
      .getAllGroups(this.pageable)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des groupes')
      );
    }
    if(this.title === "Posts"){
      this.postService
        .getLatestPosts(10)
        .pipe(finalize(() => this.isLoading = false))
        .subscribe(
          data => {
            this.posts = data;
          },
          () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des groupes')
        );
    }
  }
}