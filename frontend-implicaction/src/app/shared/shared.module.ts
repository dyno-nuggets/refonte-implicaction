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
import {ImplicactionLogoTextComponent} from "./components/implicaction-logo-text/implicaction-logo-text.component";
import {UserAvatarComponent} from './components/user-avatar/user-avatar.component';
import {RequiredRoleDirective} from './directives/required-role.directive';
import {AnyRoleMatchesDirective} from './directives/any-role-matches.directive';
import {CardHorizontalComponent} from './components/cards/card-horizontal/card-horizontal.component';
import {CardWithAvatarDurationAndTitleComponent} from './components/cards/card-with-avatar-duration-and-title/card-with-avatar-duration-and-title.component';
import {CardWithAvatarDurationAndTitleSkeleton} from "./components/cards/card-with-avatar-duration-and-title-skeleton/card-with-avatar-duration-and-title-skeleton.component";
import {SkeletonModule} from "primeng/skeleton";


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
    CardWithAvatarDurationAndTitleSkeleton
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
    CardWithAvatarDurationAndTitleSkeleton
  ],
  imports: [
    CommonModule,
    RouterModule,
    BadgeModule,
    IconsModule,
    AvatarModule,
    FormsModule,
    DropdownModule,
    SkeletonModule
  ]
})
export class SharedModule {
}
