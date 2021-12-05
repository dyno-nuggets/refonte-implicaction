import { Component, OnInit } from '@angular/core';
import {Post} from 'src/app/discussion/model/post.ts';
import {PostService} from 'src/app/discussion/services/post.service.ts';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  lastPosts : Post[]
  constructor(
   private postService : PostService,
   private toasterService: ToasterService
  ) { }

  ngOnInit(): void {
    this.postService
    .getLastPosts(5)
    .subscribe(
       posts => this.lastPosts = posts,
       () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
       () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
     );
  }

}
