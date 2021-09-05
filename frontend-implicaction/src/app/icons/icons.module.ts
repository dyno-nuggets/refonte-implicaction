import {NgModule} from '@angular/core';

import {FeatherModule} from 'angular-feather';
import {
  Award,
  Bell,
  Calendar,
  Camera,
  Github,
  Heart,
  Inbox,
  Mail,
  Phone,
  Plus,
  Shield,
  User
} from 'angular-feather/icons';

// Select some icons (use an object, not an array)
const icons = {
  Camera,
  Heart,
  Github,
  Plus,
  Bell,
  User,
  Inbox,
  Mail,
  Phone,
  Calendar,
  Award,
  Shield
};

@NgModule({
  imports: [
    FeatherModule.pick(icons)
  ],
  exports: [
    FeatherModule
  ]
})
export class IconsModule {
}
