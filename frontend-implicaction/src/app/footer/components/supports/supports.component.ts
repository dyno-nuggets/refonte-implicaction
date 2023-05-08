import {Component} from '@angular/core';

@Component({
  selector: 'app-supports',
  templateUrl: './supports.component.html',
  styleUrls: ['./supports.component.scss']
})
export class SupportsComponent {

  supports = [
    {
      label: 'Union Nationale des Combattants (U.N.C)',
      imageSrc: 'assets/img/supports/UNC-logo.png',
      link: 'https://www.unc.fr/'
    },
    {
      label: 'Association Nationale des Officiers de Carrière en Retraite (A.N.O.C.R)',
      imageSrc: 'assets/img/supports/ANOCR-logo.png',
      link: 'https://www.anocr.org/'
    },
    {
      label: 'La France mutualiste',
      imageSrc: 'assets/img/supports/LFM-logo.png',
      link: 'https://www.la-france-mutualiste.fr/'
    },
    {
      label: 'Socété Nationale d\'Entraide de la Médaille Militaire (SNEMM)',
      imageSrc: 'assets/img/supports/SNEMM-logo.png',
      link: 'https://www.snemm.fr/'
    }
  ]

}
