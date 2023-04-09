import {Component, Input, OnDestroy, ViewChild} from '@angular/core';
import {Univers} from "../../enums/univers";
import {Profile} from "../../../community/models/profile/profile";
import {Subscription, timer} from "rxjs";
import {OverlayPanel} from "primeng/overlaypanel";

@Component({
  selector: 'app-profile-info-display',
  templateUrl: './profile-info-display.component.html',
  styleUrls: ['./profile-info-display.component.scss']
})
export class ProfileInfoDisplayComponent implements OnDestroy {

  private static readonly HIDE_TIMER_DELAY = 200;
  @Input() profile?: Profile;
  @ViewChild('panel') private panel: OverlayPanel;

  univers = Univers;

  private subscription: Subscription;

  initPanelHide() {
    this.subscription = timer(ProfileInfoDisplayComponent.HIDE_TIMER_DELAY).subscribe(() => this.panel.hide());
  }

  cancelPanelHideOperation() {
    this.subscription?.unsubscribe();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
