import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../model/category";
import {CategoryService, ITree} from "../../services/category.service";
import {TopicService} from "../../services/topic.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {TopicPayload} from "../../model/topicPayload";
import {SidebarContentComponent} from "../../../shared/models/sidebar-props";
import {SidebarService} from "../../../shared/services/sidebar.service";
import {Observable} from 'rxjs';


@Component({
  selector: 'app-create-topic-form',
  templateUrl: './create-topic-form.component.html',
  styleUrls: ['./create-topic-form.component.scss']
})
export class CreateTopicFormComponent extends SidebarContentComponent<never> implements OnInit {

  topicForm = new FormGroup({
    title: new FormControl<string>('', Validators.required),
    message: new FormControl<string>('', Validators.required),
    isLocked: new FormControl<boolean>(false),
    isPinned: new FormControl<boolean>(false),
    category: new FormControl<Category>(null, Validators.required)
  });

  categoriesNodes$: Observable<ITree>;

  constructor(
    private categoryService: CategoryService,
    private topicService: TopicService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.categoriesNodes$ = this.categoryService.getCategoriesTreeSelectNode();
  }

  onSubmit() {
    const createTopic: TopicPayload = {
      title: this.topicForm.value.title,
      message: this.topicForm.value.message,
      pinned: this.topicForm.value.isPinned,
      locked: this.topicForm.value.isLocked,
      categoryId: this.topicForm.value.category.id
    };
    this.topicService.createTopic(createTopic).subscribe(res => {
      this.toastService.success('Topic créé!', 'Le topic a bien été créé');
    }, error => {
      this.toastService.error('Oops', 'Une erreur est survenue');
    }, () => {
      this.sidebarService.close();
    });
  }
}
