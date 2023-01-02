import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../model/category";
import {CategoryNode, CategoryService} from "../../services/category.service";
import {TopicService} from "../../services/topic.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {CreateTopicPayload} from "../../model/createTopicPayload";
import {SidebarContentComponent} from "../../../shared/models/sidebar-props";
import {SidebarService} from "../../../shared/services/sidebar.service";

interface CategoryTreeSelectNode {
  id: number;
  label: string;
  data: string;
  selectable: boolean;
  children?: CategoryTreeSelectNode[];
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

  categoriesNodes: CategoryTreeSelectNode[];

  constructor(
    private categoryService: CategoryService,
    private topicService: TopicService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    const categories = this.categoryService.getCategoryTree();
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
    };
    this.topicService.createTopic(createTopic).subscribe(res => {
      this.toastService.success('Topic créé!', 'Le topic a bien été créé');
    }, error => {
      this.toastService.error('Oops', 'Une erreur est survenue');
    }, () => {
      this.sidebarService.close();
    });
  }

  private categoriesToCategoriesNode(categories: CategoryNode[]): CategoryTreeSelectNode[] {
    return categories.map(({id, title, parentId, children}) => ({
      id: id,
      label: title,
      selectable: parentId !== null,
      data: '',
      children: this.categoriesToCategoriesNode(children)
    }));
  }
}
