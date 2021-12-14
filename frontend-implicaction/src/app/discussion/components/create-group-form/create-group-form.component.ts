import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {GroupService} from '../../services/group.service';
import {Observable} from 'rxjs';
import {Group} from '../../model/group';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-group-form',
  templateUrl: './create-group-form.component.html',
  styleUrls: ['./create-group-form.component.scss']
})
export class CreateGroupFormComponent extends SidebarContentComponent implements OnInit {

  readonly GROUP_IMAGE_DEFAULT_URI = Constants.GROUP_IMAGE_DEFAULT_URI;

  createGroupForm: FormGroup;
  isSubmitted = false;
  imageSrc: string;
  formData = new FormData();

  constructor(
    private formBuilder: FormBuilder,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private groupService: GroupService
  ) {
    super();
  }

  get formControls(): { [p: string]: AbstractControl } {
    return this.createGroupForm.controls;
  }

  ngOnInit(): void {
    this.createGroupForm = this.formBuilder
      .group({
        name: ['', Validators.required],
        description: [''],
        file: [''],
        fileSource: ['']
      });
  }

  onSubmit(): void {
    const group = {...this.createGroupForm.value};
    let group$: Observable<Group>;
    if (this.formControls.fileSource.value) {
      this.formData.set('group', new Blob([JSON.stringify(group)], {type: 'application/json'}));
      group$ = this.groupService.createGroup(this.formData);
    } else {
      group$ = this.groupService.createGroupWithoutPicture(group);
    }
    group$.subscribe(
      () => this.sidebarService.close(),
      () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la création du groupe'),
      () => this.toasterService.success('Succès', 'Le groupe a été créé avec succès, mais doit encore être validé !')
    );
  }

  onFileChange(event): void {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      this.formData.append('file', file, file.filename);

      reader.onload = () => {
        this.imageSrc = reader.result as string;
        this.createGroupForm.patchValue({
          fileSource: reader.result
        });
      };
    }
  }
}
