import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {BadgeModule} from 'primeng/badge';
import {IconsModule} from '../icons/icons.module';
import {LoadingComponent} from './components/loading/loading.component';
import {AlertComponent} from './components/alert/alert.component';
import {SidebarContentDirective} from './directives/sidebar-content.directive';
import {BrPipe} from './pipes/br.pipe';
import {ContractTypeComponent} from './components/contract-type/contract-type.component';
import {JobFilterComponent} from './components/job-filter/job-filter.component';
import {CompanyFilterComponent} from './components/company-filter/company-filter.component';
import {BaseWithPaginationAndFilterComponent} from './components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {AvatarModule} from 'primeng/avatar';
import {FormsModule} from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {TextEllipsisPipe} from './pipes/text-ellipsis.pipe';
import {ImplicactionLogoTextComponent} from './components/implicaction-logo-text/implicaction-logo-text.component';
import {UserAvatarComponent} from './components/user-avatar/user-avatar.component';
import {RequiredRoleDirective} from './directives/required-role.directive';
import {AnyRoleMatchesDirective} from './directives/any-role-matches.directive';
import {CardHorizontalComponent} from './components/cards/card-horizontal/card-horizontal.component';
import {CardWithAvatarDurationAndTitleComponent} from './components/cards/card-with-avatar-duration-and-title/card-with-avatar-duration-and-title.component';
import {
  CardWithAvatarDurationAndTitleSkeletonComponent
} from './components/cards/card-with-avatar-duration-and-title-skeleton/card-with-avatar-duration-and-title-skeleton.component';
import {SkeletonModule} from 'primeng/skeleton';
import {ProfileInfoDisplayComponent} from './components/profile-info-display/profile-info-display.component';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {BaseFormComponent} from './components/base-form/base-form.component';
import {PaginatorComponent} from './components/paginator/paginator.component';
import {PaginatorModule} from "primeng/paginator";
import {SidemenuComponent} from "./components/sidemenu/sidemenu.component";


@NgModule({
  declarations: [
    LoadingComponent,
    AlertComponent,
    SidebarContentDirective,
    BrPipe,
    ContractTypeComponent,
    ContractTypeComponent,
    JobFilterComponent,
    BaseWithPaginationAndFilterComponent,
    CompanyFilterComponent,
    TextEllipsisPipe,
    ImplicactionLogoTextComponent,
    UserAvatarComponent,
    RequiredRoleDirective,
    AnyRoleMatchesDirective,
    CardHorizontalComponent,
    CardWithAvatarDurationAndTitleComponent,
    CardWithAvatarDurationAndTitleSkeletonComponent,
    AnyRoleMatchesDirective,
    UserAvatarComponent,
    ProfileInfoDisplayComponent,
    BaseFormComponent,
    PaginatorComponent,
    SidemenuComponent,
  ],
  exports: [
    LoadingComponent,
    AlertComponent,
    SidebarContentDirective,
    BrPipe,
    ContractTypeComponent,
    ContractTypeComponent,
    JobFilterComponent,
    TextEllipsisPipe,
    ImplicactionLogoTextComponent,
    UserAvatarComponent,
    RequiredRoleDirective,
    AnyRoleMatchesDirective,
    CardHorizontalComponent,
    CardWithAvatarDurationAndTitleComponent,
    CardWithAvatarDurationAndTitleSkeletonComponent,
    ProfileInfoDisplayComponent,
    BaseFormComponent,
    PaginatorComponent,
    SidemenuComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    BadgeModule,
    IconsModule,
    AvatarModule,
    FormsModule,
    DropdownModule,
    SkeletonModule,
    OverlayPanelModule,
    PaginatorModule,
  ]
})
export class SharedModule {
}
