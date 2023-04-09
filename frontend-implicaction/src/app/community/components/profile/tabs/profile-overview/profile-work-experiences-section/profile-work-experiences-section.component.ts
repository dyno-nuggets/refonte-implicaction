import {Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from "../../../../../../shared/models/work-experience";
import {Training} from "../../../../../../shared/models/training";
import {Utils} from "../../../../../../shared/classes/utils";

@Component({
  selector: 'app-profile-dated-items-section',
  templateUrl: './profile-work-experiences-section.component.html',
})
export class ProfileWorkExperiencesSectionComponent implements OnInit {

  @Input() inputs!: WorkExperience[] | Training[];

  public inputsCopy!: { label: string, date: string }[];


  ngOnInit(): void {
    this.inputsCopy = this.inputs?.map(item => ({label: item.label, date: 'date' in item ? item.date : item.startedAt}))
      .sort((i1, i2) => Utils.sortDateByYearDesc(i1.date, i2.date));
  }

}
