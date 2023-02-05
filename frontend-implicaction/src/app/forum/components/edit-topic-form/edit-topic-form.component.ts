import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {Topic} from '../../model/topic';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CategoryTreeSelectNode} from '../../model/categoryTreeSelectNode';
import {TopicService} from '../../services/topic.service';
import {CategoryService, ITree} from '../../services/category.service';
import {BehaviorSubject, Observable, of, zip} from 'rxjs';
import {TopicPayload} from '../../model/topicPayload';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';

export type EditTopicFormProps = Topic | number;

@Component({
  selector: 'app-edit-topic-form',
  templateUrl: './edit-topic-form.component.html',
  styleUrls: ['./edit-topic-form.component.scss']
})
export class EditTopicFormComponent extends SidebarContentComponent<EditTopicFormProps> implements OnInit {

  topicId: number;
  categoriesNodes$: Observable<ITree>;
  topic$: Observable<Topic>;
  topicForm$: Observable<unknown>;
  topicForm = new BehaviorSubject<FormGroup>(null);
  seletedNode: CategoryTreeSelectNode;

  constructor(private topicService: TopicService,
              private categoryService: CategoryService,
              private toastService: ToasterService,
              private sidebarService: SidebarService) {
    super();
  }

  ngOnInit(): void {
    this.topicId = typeof this.sidebarInput === "number" ? this.sidebarInput : this.sidebarInput.id;
    this.topic$ = this.getTopic(this.sidebarInput);
    this.categoriesNodes$ = this.categoryService.getCategoriesTreeSelectNode({
      selectable: ({parentId}) => parentId !== null
    });
    zip(this.topic$, this.categoriesNodes$).subscribe(([topic, {map}]) =>
      this.topicForm.next(this.createTopicForm(topic, map.get(topic.category.id)))
    );
    this.topicForm$ = this.topicForm.asObservable();
  }

  getTopic(postOrId: EditTopicFormProps) {
    if (typeof postOrId === "number") {
      return this.topicService.getTopic(postOrId);
    }
    return of(postOrId);
  }

  createTopicForm(topic: Topic, category: CategoryTreeSelectNode) {
    return new FormGroup({
      title: new FormControl<string>(topic.title, Validators.required),
      message: new FormControl<string>(topic.message, Validators.required),
      isLocked: new FormControl<boolean>(topic.locked),
      isPinned: new FormControl<boolean>(topic.pinned),
      category: new FormControl(category, Validators.required) //TODO: RECUPERER LE BON NOEUD
    });
  }

  onSubmit() {
    const topicForm = this.topicForm.getValue();
    const editedTopic: TopicPayload = {
      id: this.topicId,
      title: topicForm.value.title,
      message: topicForm.value.message,
      pinned: topicForm.value.isPinned,
      locked: topicForm.value.isLocked,
      categoryId: topicForm.value.category.id
    };

    this.topicService.editTopic(editedTopic).subscribe(res => {
      this.toastService.success('Topic modifié!', 'Le topic a bien été modifié');
    }, error => {
      this.toastService.error('Oops', 'Une erreur est survenue');
    }, () => {
      this.sidebarService.close();
    });
  }
}
