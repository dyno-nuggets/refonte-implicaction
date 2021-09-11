import {NgModule} from '@angular/core';

import {FeatherModule} from 'angular-feather';
import {
  Award,
  Bell,
  Briefcase,
  Calendar,
  Camera,
  Github,
  Heart,
  Inbox,
  Mail,
  Phone,
  Plus,
  Shield,
  User,
  UserCheck,
  UserMinus,
  UserPlus,
  Users,
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
  Shield,
  Briefcase,
  Users,
  UserMinus,
  UserPlus,
  UserCheck
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
