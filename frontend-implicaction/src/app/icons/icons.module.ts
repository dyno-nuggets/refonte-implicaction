import {NgModule} from '@angular/core';

import {FeatherModule} from 'angular-feather';
import {
  Award,
  Bell,
  Briefcase,
  Calendar,
  Camera,
  Edit,
  Edit2,
  Github,
  Heart,
  Inbox,
  Mail,
  Phone,
  Plus,
  RotateCcw,
  Save,
  Shield,
  Trash2,
  User,
  UserCheck,
  UserMinus,
  UserPlus,
  Users,
  UserX,
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
  UserCheck,
  UserX,
  Save,
  Edit,
  RotateCcw,
  Trash2,
  Edit2,
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
