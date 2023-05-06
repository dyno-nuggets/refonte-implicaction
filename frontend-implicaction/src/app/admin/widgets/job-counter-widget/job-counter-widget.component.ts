import { Component, OnInit } from '@angular/core';
import {JobService} from "../../../job/services/job.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-job-counter-widget',
  templateUrl: './job-counter-widget.component.html',
})
export class JobCounterWidgetComponent implements OnInit {

  jobCounter$: Observable<number>

  constructor(private jobService: JobService) { }

  ngOnInit(): void {
    this.jobCounter$ = this.jobService.getEnabledJobsCount();
  }

}
