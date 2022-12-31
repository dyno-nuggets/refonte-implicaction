import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../model/category";
import {CategoryService} from "../../services/category.service";
import {TopicService} from "../../services/topic.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {Observable} from "rxjs";
import {CreateTopicPayload} from "../../model/createTopicPayload";
import {SidebarContentComponent} from "../../../shared/models/sidebar-props";
import {SidebarService} from "../../../shared/services/sidebar.service";

interface CategoryNode {
  id: number;
  label: string;
  data: string;
  selectable: boolean;
  children?: CategoryNode[];
}

@Component({
  selector: 'app-create-topic-form',
  templateUrl: './create-topic-form.component.html',
  styleUrls: ['./create-topic-form.component.scss']
})
export class CreateTopicFormComponent extends SidebarContentComponent implements OnInit {

  topicForm = new FormGroup({
    title: new FormControl<string>('', Validators.required),
    message: new FormControl<string>('', Validators.required),
    isLocked: new FormControl<boolean>(false),
    isPinned: new FormControl<boolean>(false),
    category: new FormControl<Category>(null, Validators.required)
  });

  categoriesNodes: CategoryNode[];

  constructor(private categoryService: CategoryService,
              private topicService: TopicService,
              private toastService: ToasterService,
              private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    const categories: Observable<Category[]> = this.categoryService.getCategories();
    categories.subscribe((val) => {
      this.categoriesNodes = this.categoriesToCategoriesNode(val);
    });
  }

  onSubmit() {
    const createTopic: CreateTopicPayload = {
      title: this.topicForm.value.title,
      message: this.topicForm.value.message,
      isPinned: this.topicForm.value.isPinned,
      isLocked: this.topicForm.value.isLocked,
      categoryId: this.topicForm.value.category.id
    }
    this.topicService.createTopic(createTopic).subscribe(res => {
      this.toastService.success('Topic créé!', 'Le topic a bien été créé');
      this.sidebarService.close();
    })
  }

  private categoriesToCategoriesNode(categories: Category[]): CategoryNode[] {
    return categories.map(({id, title, parentId, children}) => ({
      id: id,
      label: title,
      selectable: parentId !== null,
      data: "",
      children: this.categoriesToCategoriesNode(children)
    }));
  }
}
