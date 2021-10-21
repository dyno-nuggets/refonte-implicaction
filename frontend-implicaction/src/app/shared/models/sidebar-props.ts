import {WorkExperience} from './work-experience';
import {Training} from './training';
import {Type} from '@angular/core';
import {JobPosting} from './job-posting';
import {Company} from './company';


/**
 * Inputs partagés en entrée pour un contenu de la sidebar
 */
export interface SidebarInputs {
  experience?: WorkExperience;
  training?: Training;
  job?: JobPosting;
  company?: Company;
}

/**
 * Les composant de type "contenu" doivent hériter de cette interface pour la simplification du code d'affichage et pour
 * bénéficier du partage d'inputs
 */
export class SidebarContentComponent {
  sidebarInput?: SidebarInputs;
}


/**
 * Les propriétés de la sidebar
 */
export interface SidebarProps {
  component: Type<SidebarContentComponent>;
  title: string;
  /**
   * largeur en pixels (px)
   */
  width?: number;
  input?: SidebarInputs;
}
