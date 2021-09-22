import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {Utils} from '../../../shared/classes/utils';

@Component({
    selector: 'app-experience-detail',
    templateUrl: './experience-detail.component.html',
    styleUrls: ['./experience-detail.component.scss']
})
export class ExperienceDetailComponent implements OnInit {

    @Input()
    experience: WorkExperience;
    @Input()
    isEditing: boolean;
    guid = Utils.generateGuid();

    @Output()
    onDelete = new EventEmitter<WorkExperience>();

    constructor() {
    }

    ngOnInit(): void {
    }

    deleteExperience(experience: WorkExperience): void {
        this.onDelete.next(experience);
    }
}
