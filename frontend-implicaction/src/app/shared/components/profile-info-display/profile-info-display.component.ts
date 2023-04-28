import {ChangeDetectionStrategy, Component, Input, OnInit, ViewChild} from '@angular/core';
import {Univers} from "../../enums/univers";
import {Profile} from "../../../community/models/profile";
import {Subscription, timer} from "rxjs";
import {OverlayPanel} from "primeng/overlaypanel";

@Component({
  selector: 'app-profile-info-display',
  templateUrl: './profile-info-display.component.html',
  styleUrls: ['profile-info-display.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileInfoDisplayComponent implements OnInit {

  private static readonly HIDE_TIMER_DELAY = 200;
  private static readonly SHOW_TIMER_DELAY = 600;

  @Input() profile?: Profile;

  @ViewChild('panel') private panel: OverlayPanel;

  profileLink = [];
  transitionOptions = '.0s';

  private subscriptionHide: Subscription;
  private subscriptionShow: Subscription;

  ngOnInit(): void {
    this.profileLink = ['/', Univers.COMMUNITY.url, 'profiles', this.profile.username];
  }

  initPanelHideOperation() {
    this.subscriptionShow?.unsubscribe();
    this.subscriptionHide = timer(ProfileInfoDisplayComponent.HIDE_TIMER_DELAY).subscribe(
      () => this.panel.hide()
    );
  }

  initPanelShowOperation(event) {
    this.subscriptionHide?.unsubscribe();
    this.subscriptionShow = timer(ProfileInfoDisplayComponent.SHOW_TIMER_DELAY).subscribe(
      () => this.panel.show(event)
    );
  }

  cancelPanelHideOperation() {
    this.subscriptionHide?.unsubscribe();
  }
}
