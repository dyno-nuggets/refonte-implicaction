import {Component, OnDestroy, OnInit} from '@angular/core';
import {Alert} from '../../models/alert';
import {Subscription} from 'rxjs';
import {NavigationStart, Router} from '@angular/router';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit, OnDestroy {

  alert: Alert = undefined;
  alertSubscription: Subscription;
  routeSubscription: Subscription;

  constructor(
    private router: Router,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.alertSubscription = this.alertService
      .onAlert()
      .subscribe(alert => {
        if (!alert?.message) {
          return;
        }
        this.alert = alert;
      });

    this.routeSubscription = this.router
      .events
      .subscribe(event => {
        if (event instanceof NavigationStart) {
          this.alertService.clear();
        }
      });
  }

  ngOnDestroy(): void {
    this.alertSubscription?.unsubscribe();
    this.routeSubscription?.unsubscribe();
  }

}
