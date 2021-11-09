import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {GroupService} from '../../services/group.service';

@Component({
  selector: 'app-group-form',
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.scss']
})
export class GroupFormComponent extends SidebarContentComponent implements OnInit {

  createGroupForm: FormGroup;
  isSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private groupService: GroupService
  ) {
    super();
  }

  ngOnInit(): void {
    this.createGroupForm = this.formBuilder
      .group({
        name: ['', Validators.required],
        description: ['']
      });
  }


  onSubmit(): void {
    const group = {...this.createGroupForm.value};
    this.groupService.createGroup(group)
      .subscribe(
        () => this.sidebarService.close(),
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la création du groupe'),
        () => this.toasterService.success('Succès', 'Le groupe a été créé avec succès')
      );
  }
}
