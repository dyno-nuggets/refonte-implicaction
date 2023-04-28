import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Profile} from "../../models/profile";
import {ProfileContextService} from "../../../core/services/profile-context.service";
import {RelationAction, RelationActionEnum, RelationActionEnumCode} from "../../models/relation-action";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-relation-button',
  templateUrl: './relation-button.component.html',
  styleUrls: ['./relation-button.component.scss'],
})
export class RelationButtonComponent implements OnInit, OnChanges {

  @Input() profile: Profile;

  @Output() action = new EventEmitter<RelationAction>();

  okButton: { text?: string, action?: RelationAction };
  cancelButton: { text?: string, action?: RelationAction };
  currentProfile: Profile;

  constructor(private pcs: ProfileContextService) {
  }

  ngOnInit(): void {
    this.pcs.observeProfile()
      .pipe(
        take(1)
      )
      .subscribe(p => {
        this.currentProfile = p;
        this.okButton = this.getOkButton();
        this.cancelButton = this.getCancelButton();
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.hasOwnProperty('profile')) {
      this.okButton = this.getOkButton();
      this.cancelButton = this.getCancelButton();
    }
  }

  cancelButtonClicked(): void {
    this.buttonClicked(this.cancelButton.action);
  }

  okButtonClicked(): void {
    this.buttonClicked(this.okButton.action);
  }

  private buttonClicked(action: RelationAction): void {
    if (action) {
      this.action.emit(action);
    }
  }


  private getOkButton(): { text: string, action: RelationAction } {
    // CAS 1: il s’agit de l’utilisateur courant
    if (!(this.profile && this.currentProfile) || this.profile.username === this.currentProfile.username) {
      return null;
    }

    // CAS 2: il existe n’existe pas de relation
    if (!this.profile.relationWithCurrentUser) {
      return {
        text: RelationActionEnum.CREATE.label,
        action: {
          relation: {sender: this.currentProfile, receiver: this.profile},
          action: RelationActionEnum.CREATE.code
        }
      };
    }

    // CAS 3: la relation n’a pas été confirmée
    if (!this.profile.relationWithCurrentUser.confirmedAt) {
      return {
        text: RelationActionEnum.CONFIRM.label,
        action: {
          relation: this.profile.relationWithCurrentUser,
          action: RelationActionEnum.CONFIRM.code
        }
      };
    }

    // CAS 4: la relation est déjà confirmée
    return null;
  }

  private getCancelButton(): { text: string, action: RelationAction } {
    // CAS 1: il n'y a aucune relation
    if (!(this.profile && this.currentProfile) || !this.profile.relationWithCurrentUser) {
      return null
    }

    // CAS 2: peu importe la relation, on veut la supprimer
    return {
      text: RelationActionEnum.DELETE.label,
      action: {
        relation: this.profile.relationWithCurrentUser,
        action: RelationActionEnumCode.DELETE
      }
    };
  }
}
