import {Component, Input} from '@angular/core';
import {Post} from '../../model/post';
import {VoteService} from '../../services/vote.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {throwError} from 'rxjs';
import {VotePayload, VoteType} from '../../model/vote-payload';

@Component({
  selector: 'app-votebox',
  templateUrl: './votebox.component.html',
  styleUrls: ['./votebox.component.scss']
})
export class VoteboxComponent {

  @Input()
  post: Post = {};

  votePayload: VotePayload = {};


  constructor(
    private voteService: VoteService,
    private toasterService: ToasterService
  ) {
  }

  upVote(): void {
    if (this.post.upVote) {
      return;
    }
    this.votePayload.voteType = VoteType.UPVOTE;
    this.post.downVote = false;
    this.post.upVote = true;
    this.post.voteCount++;
    this.vote();
  }

  downVote(): void {
    if (this.post.downVote) {
      return;
    }
    this.post.downVote = true;
    this.post.upVote = false;
    this.post.voteCount--;
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  private vote(): void {
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(
      () => {
        // on ne fait rien
      },
      error => {
        this.toasterService.error('Oops', error.error.message);
        throwError(error);
      });
  }
}
