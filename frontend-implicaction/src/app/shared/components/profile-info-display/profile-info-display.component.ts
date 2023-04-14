import {ChangeDetectionStrategy, Component, Input, OnInit, ViewChild} from '@angular/core';
import {Univers} from "../../enums/univers";
import {Profile} from "../../../community/models/profile/profile";
import {Subscription, timer} from "rxjs";
import {OverlayPanel} from "primeng/overlaypanel";

@Component({
  selector: 'app-profile-info-display',
  templateUrl: './profile-info-display.component.html',
  styleUrls: ['profile-info-display.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileInfoDisplayComponent implements OnInit {

  private static readonly HIDE_TIMER_DELAY = 200;
  private static readonly SHOW_TIMER_DELAY = 600;

  @Input() profile?: Profile;

  profileLink = [];


  @ViewChild('panel') private panel: OverlayPanel;
  private subscriptionHide: Subscription;
  private subscriptionShow: Subscription;

  ngOnInit(): void {
    this.profileLink = ['/', Univers.COMMUNITY.url, 'profile', this.profile.username];
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
