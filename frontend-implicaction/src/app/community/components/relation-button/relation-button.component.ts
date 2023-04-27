import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {Profile} from "../../models/profile/profile";
import {AuthService} from "../../../core/services/auth.service";
import {Relation} from "../../models/relation";

@Component({
  selector: 'app-relation-button',
  templateUrl: './relation-button.component.html',
  styleUrls: ['./relation-button.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RelationButtonComponent implements OnInit {

  @Input() profile: Profile;

  okButtonText: string;
  cancelButtonText: string;
  principalUsername: string;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.principalUsername = this.authService.getPrincipal()?.username;
    this.okButtonText = this.getOkButtonText(this.profile.relationWithCurrentUser);
    this.cancelButtonText = this.getCancelButton(this.profile.relationWithCurrentUser);
  }

  getOkButtonText(relation: Relation): string {
    if (this.profile.username === this.principalUsername) {
      return null;
    }
    if (!relation) {
      return 'ajouter en ami(e)';
    }
    if (!relation.confirmedAt && relation.receiver === this.principalUsername) {
      return 'confirmer';
    }
    return null;
  }

  getCancelButton(relation: Relation): string {
    return relation ? 'supprimer' : null;
  }
}
