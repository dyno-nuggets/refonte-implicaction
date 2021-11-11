import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {PostPayload} from '../../model/post-payload';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Group} from '../../model/group';
import {Router} from '@angular/router';
import {PostService} from '../../services/post.service';
import {GroupService} from '../../services/group.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from '../../../config/constants';
import {AuthService} from '../../../shared/services/auth.service';
import {User} from '../../../shared/models/user';

@Component({
  selector: 'app-create-post-form',
  templateUrl: './create-post-form.component.html',
  styleUrls: ['./create-post-form.component.scss']
})
export class CreatePostFormComponent extends SidebarContentComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: PostPayload = {name: '', subredditName: ''};
  groups: Group[];
  currentUser: User = {};

  constructor(
    private router: Router,
    private authService: AuthService,
    private postService: PostService,
    private groupService: GroupService,
    private toasterService: ToasterService
  ) {
    super();
  }

  ngOnInit(): void {
    this.createPostForm = new FormGroup({
      name: new FormControl('', Validators.required),
      subredditName: new FormControl(''),
      url: new FormControl(''),
      description: new FormControl(''),
    });

    this.authService
      .currentUser
      .subscribe(user => console.log(user));

    this.groupService
      .getAllGroups(Constants.ALL_VALUE_PAGEABLE)
      .subscribe(
        data => this.groups = data.content,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la récupération des groupes')
      );
  }

  createPost(): void {
    this.postPayload = {...this.createPostForm.value};

    this.postService.createPost(this.postPayload)
      .subscribe(
        post => this.router
          .navigateByUrl(`${Univers.DISCUSSIONS.url}/${post.id}`)
          .then(() => this.toasterService.success('Succès', 'Votre discussion a été créée avec succès')),
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la création de votre discussion')
      );
  }
}
