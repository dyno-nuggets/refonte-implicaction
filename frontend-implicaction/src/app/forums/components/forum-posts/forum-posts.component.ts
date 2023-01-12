import {Component, OnInit} from '@angular/core';
import {ForumTableTypesEnum} from "../../enums/table-type-enum";
import {MenuItem} from "primeng/api";
import {
  BaseWithPaginationAndFilterComponent
} from "../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component";
import {Criteria} from "../../../shared/models/Criteria";
import {ActivatedRoute} from "@angular/router";
import {Post} from "../../model/post";
import {finalize} from "rxjs/operators";
import {ToasterService} from "../../../core/services/toaster.service";
import {PostService} from "../../services/post.service";

@Component({
  selector: 'app-forum-posts',
  templateUrl: './forum-posts.component.html',
  styleUrls: ['./forum-posts.component.scss']
})
export class ForumPostsComponent
  extends BaseWithPaginationAndFilterComponent<Post, Criteria> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = [5];
  optionsTopMenu: MenuItem[];
  tableType = ForumTableTypesEnum;
  topRange: number = 7;

  constructor(
    protected route: ActivatedRoute,
    private toastService: ToasterService,
    private postService: PostService
  ) {
    super(route);
  }

  ngOnInit() {
    this.isLoading = true
    this.pageable.rowsPerPages = this.ROWS_PER_PAGE_OPTIONS;
    this.pageable.rows = this.ROWS_PER_PAGE_OPTIONS[0];
    this.innerPaginate()
    this.optionsTopMenu = [
      {
        label: '7 jours', icon: 'pi pi-fw pi-calendar', command: event => {
          this.isLoading = true
          this.innerPaginate()

        },
      },
      {
        label: '30 jours', icon: 'pi pi-fw pi-calendar', command: event => {
          this.pageable.content = this.pageable.content.slice(0, 2)
        }
      },

    ];
  }

  protected innerPaginate(): void {
    this.postService
      .getPopularPostsByForum(this.pageable, 33)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe(
        (data) => {
          this.pageable.content = data.content;
        },
        () =>
          this.toastService.error(
            'Oops',
            'Une erreur est survenue lors de la récupération de la liste des groupes'
          )
      );
  }

}
