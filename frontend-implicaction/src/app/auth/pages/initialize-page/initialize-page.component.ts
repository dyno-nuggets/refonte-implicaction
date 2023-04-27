import {Component} from '@angular/core';
import {Alert, AlertType} from '../../../shared/models/alert';
import {SignupRequestPayload} from '../../models/signup-request-payload';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {AppService} from '../../../core/services/app.service';
import {AppStatusEnum} from '../../../shared/enums/app-status-enum';

@Component({
  selector: 'app-initialize-page',
  templateUrl: './initialize-page.component.html',
  styleUrls: ['./initialize-page.component.scss']
})
export class InitializePageComponent {

  alert: Alert = {
    type: AlertType.INFO,
    message: 'Il semble que vous utilisiez l\'application pour la première fois. <strong>Veuillez créer le compte administrateur</strong>.'
  };

  constructor(
    private authService: AuthService,
    private router: Router,
    private toasterService: ToasterService,
    private appService: AppService,
  ) {
  }

  submit(signupRequest: SignupRequestPayload) {
    this.authService.signup(signupRequest).subscribe(
      () => {
        this.appService.setStatus(AppStatusEnum.INITIALIZED);
        this.router.navigateByUrl('/')
          .then(() => this.toasterService.success('Succès', `La création du compte administrateur ${signupRequest.username} a été effectuée avec succès`));
      });
  }
}
