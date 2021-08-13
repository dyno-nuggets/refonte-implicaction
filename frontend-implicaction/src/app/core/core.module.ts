import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Constants} from '../config/constants';

/**
 * Cette classe abstraite utilisée pour la construction de modules, empêche l'importation du module dans un autre endroit
 * que le module root App Module.
 */
export abstract class EnsureImportedOnceModule {
  protected constructor(targetModule: any) {
    if (targetModule) {
      throw new Error(`${targetModule.constructor.name} has already been loaded.`);
    }
  }
}

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [Constants]
})
export class CoreModule extends EnsureImportedOnceModule {
  public constructor(@SkipSelf() @Optional() parent: CoreModule) {
    super(parent);
  }
}
