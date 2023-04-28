import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Profile} from "../../../models/profile";
import {ToasterService} from "../../../../core/services/toaster.service";
import {ProfileContextService} from "../../../../core/services/profile-context.service";
import {finalize} from "rxjs/operators";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {ProfileService} from "../../../services/profile/profile.service";

@Component({
  selector: 'app-profile-avatar-file-uploader',
  templateUrl: './profile-avatar-file-uploader.component.html',
})
export class ProfileAvatarFileUploaderComponent implements OnChanges {

  @Input() profile!: Profile;

  profileCopy: Profile;
  progress = 0;
  isLoading = false;
  selectedFile: File;
  fileExtensionsAccepted = ['.jpg', '.png', '.jpeg', '.gif'].join(', ');

  constructor(
    private profileService: ProfileService,
    private toasterService: ToasterService,
    private pcs: ProfileContextService
  ) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.profileCopy = {...this.profile};
  }

  selectFile(event): void {
    const reader = new FileReader();

    if (!event.target.files || !event.target.files.length) {
      return;
    }

    this.progress = 0;
    const [file] = event.target.files;
    this.selectedFile = file;
    reader.readAsDataURL(this.selectedFile);
    reader.onload = () => this.profileCopy.imageUrl = reader.result as string;
  }

  uploadFile(): void {
    if (!this.selectedFile) {
      return;
    }

    this.isLoading = true;
    this.profileService
      .uploadProfileAvatar(this.selectedFile, this.profile.username)
      .pipe(finalize(() => this.progress = 100))
      .subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.profile.imageUrl = JSON.parse(event?.body)?.imageUrl;
            this.pcs.profile = {...this.profile};
            this.selectedFile = null;
          }
        },
        error: () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la modification de votre image de profil')
      });
  }
}
