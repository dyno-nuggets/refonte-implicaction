import {Component} from '@angular/core';
import {RouterLinkWithHref} from "@angular/router";
import {NgForOf} from "@angular/common";

@Component({
  standalone: true,
  imports: [RouterLinkWithHref, NgForOf],
  selector: 'app-footer',
  templateUrl: './footer.component.html',
})
export class FooterComponent {

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
