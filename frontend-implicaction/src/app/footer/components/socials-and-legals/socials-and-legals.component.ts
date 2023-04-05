import {Component} from '@angular/core';

@Component({
  selector: 'app-socials-and-legals',
  templateUrl: './socials-and-legals.component.html',
  styleUrls: ['./socials-and-legals.component.scss']
})
export class SocialsAndLegalsComponent {

  socials = [
    {
      icon: 'fa-facebook-square',
      url: 'https://www.facebook.com/jeanmichel.lebec.2/',
      label: 'visiter notre page facebok'
    },
    {
      icon: 'fa-linkedin-square',
      url: 'https://www.linkedin.com/company/association-implic-action/',
      label: 'visiter notre page linkedin'
    },
    {
      icon: 'fa-youtube-play',
      url: 'https://www.youtube.com/@ImplicAction',
      label: 'découvrir notre chaîne youtube'
    }
  ]

}
