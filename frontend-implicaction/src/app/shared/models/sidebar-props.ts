import {Type} from '@angular/core';

/**
 * Les composant de type "contenu" doivent hériter de cette interface pour la simplification du code d'affichage et pour
 * bénéficier du partage d'inputs
 */
export class SidebarContentComponent<T> {
  sidebarInput?: T;
}


/**
 * Les propriétés de la sidebar
 */
export interface SidebarProps<T> {
  component: Type<SidebarContentComponent<T>>;
  title: string;
  /**
   * largeur en pixels (px)
   */
  width?: number;
  input?: T;
}
