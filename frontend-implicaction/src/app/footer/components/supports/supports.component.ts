import {Component} from '@angular/core';

@Component({
  selector: 'app-supports',
  templateUrl: './supports.component.html',
  styleUrls: ['./supports.component.scss']
})
export class SupportsComponent {

  supports = [
    {
      label: 'Défense et mobilité',
      imageSrc: 'assets/img/supports/DFME-logo.png',
      link: 'https://www.defense-mobilite.fr/'

    },
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
      label: 'Association Tégo',
      imageSrc: 'assets/img/supports/TEGO-logo.png',
      link: 'https://associationtego.fr/'
    },
    {
      label: 'Unéo',
      imageSrc: 'assets/img/supports/UNEO-logo.png',
      link: 'https://www.groupe-uneo.fr/'
    },
    {
      label: 'La France mutualiste',
      imageSrc: 'assets/img/supports/LFM-logo.png',
      link: 'https://www.la-france-mutualiste.fr/'
    },
    {
      label: 'Cap vers une 2ème carrière (CAP2C)',
      imageSrc: 'assets/img/supports/CAP2C-logo.png',
      link: 'https://cap2c.org/eh/'
    },
    {
      label: 'Fiducial',
      imageSrc: 'assets/img/supports/fiducial-logo.png',
      link: 'https://www.fiducial.fr/'
    }
  ]

}
