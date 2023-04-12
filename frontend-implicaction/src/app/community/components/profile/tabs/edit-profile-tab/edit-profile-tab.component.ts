import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Profile} from "../../../../models/profile/profile";
import {FileUploadService} from "../../../../../shared/services/file-upload.service";
import {ToasterService} from "../../../../../core/services/toaster.service";
import {ProfileContextService} from "../../../../../core/services/profile-context.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
  styleUrls: ['./edit-profile-tab.component.scss'],
})
export class EditProfileTabComponent implements OnChanges {

  @Input() profile!: Profile;

  profileCopy: Profile;
  progress = 0;
  isLoading = false;
  selectedFile: File;

  constructor(
    private fileUploadService: FileUploadService,
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
    const [file] = event.target.files;
    this.selectedFile = file;
    reader.readAsDataURL(this.selectedFile);
    reader.onload = () => {
      this.profileCopy.imageUrl = reader.result as string;
    }
  }

  uploadFile(): void {
    if (!this.selectedFile) {
      return;
    }

    this.isLoading = true;
    this.progress = 0;

    this.fileUploadService
      .uploadProfileAvatar(this.selectedFile, this.profile.username)
      .pipe(finalize(() => this.progress = 100))
      .subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.profile.imageUrl = event?.body;
            this.pcs.profile = this.profile;
          }
        },
        error: () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la modification de votre image de profil')
      });
  }
}
