import {Component, Input} from '@angular/core';
import {TopicService} from "../../services/topic.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {SidebarService} from "../../../shared/services/sidebar.service";
import {SidebarContentComponent} from "../../../shared/models/sidebar-props";

@Component({
  selector: 'app-delete-topic-validation',
  templateUrl: './delete-topic-validation.component.html',
  styleUrls: ['./delete-topic-validation.component.scss']
})
export class DeleteTopicValidationComponent extends SidebarContentComponent<{ id: number }> {

  @Input() topicId: number;

  constructor(private topicService: TopicService,
              private toastService: ToasterService,
              private sidebarService: SidebarService,) {
    super();
  }

  onValidation() {
    this.topicId = typeof this.sidebarInput === "number" ? this.sidebarInput : this.sidebarInput.id;
    this.topicService.deleteTopic(this.topicId).subscribe(res => {
      this.toastService.success('Topic supprimé!', 'Le topic a bien été supprimé');
    }, (error) => {
      this.toastService.error("Erreur de suppression", error.error.errorMessage);
    });
    this.sidebarService.close();
  }

  onCancel() {
    this.sidebarService.close();
  }

}
