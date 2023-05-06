import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Profile} from '../../models/profile';
import {ProfileContextService} from '../../../core/services/profile-context.service';
import {RelationActionEnum} from '../../models/enums/relation-action';
import {take} from 'rxjs/operators';
import {RelationButton} from '../../models/relation-button';


@Component({
  selector: 'app-relation-button',
  templateUrl: './relation-button.component.html',
  styleUrls: ['./relation-button.component.scss'],
})
export class RelationButtonComponent implements OnInit, OnChanges {

  @Input() profile: Profile;

  @Output() action = new EventEmitter<RelationButton>();

  buttons: RelationButton[] = [];
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
        this.buttons = this.buildButtons();
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    // le firstchange est géré par le OnInit
    if (changes.hasOwnProperty('profile') && !changes['profile'].firstChange) {
      this.buttons = this.buildButtons();
    }
  }

  buttonClicked(action: RelationButton): void {
    if (action) {
      this.action.emit(action);
    }
  }

  private buildButtons(): RelationButton[] {
    return [this.getCancelButton(), this.getOkButton()].filter(b => b !== null);
  }

  private getOkButton(): RelationButton {
    // CAS 1: il s’agit de l’utilisateur courant
    if (!(this.profile && this.currentProfile) || this.profile.username === this.currentProfile.username) {
      return null;
    }

    // CAS 2: il existe n’existe pas de relation
    if (!this.profile.relationWithCurrentUser) {
      return {
        action: RelationActionEnum.CREATE,
        relation: {sender: this.currentProfile, receiver: this.profile}
      };
    }

    // CAS 3: la relation n’a pas été confirmée
    if (!this.profile.relationWithCurrentUser.confirmedAt) {
      return {
        action: RelationActionEnum.CONFIRM,
        relation: this.profile.relationWithCurrentUser
      };
    }

    // CAS 4: la relation est déjà confirmée
    return null;
  }

  private getCancelButton(): RelationButton {
    // CAS 1: il n'y a aucune relation
    if (!(this.profile && this.currentProfile) || !this.profile.relationWithCurrentUser) {
      return null;
    }

    // CAS 2: peu importe la relation, on veut la supprimer
    return {
      action: RelationActionEnum.DELETE,
      relation: this.profile.relationWithCurrentUser
    };
  }
}
