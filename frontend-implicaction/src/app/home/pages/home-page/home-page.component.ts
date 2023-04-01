import {Component, OnInit} from '@angular/core';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from "../../../config/constants";
import {JobService} from "../../../job/services/job.service";
import {JobPosting} from "../../../shared/models/job-posting";
import {finalize} from "rxjs/operators";
import {ImplicactionEventService} from "../../../shared/services/implicaction-event.service";
import {ImplicactionEvent} from "../../../shared/models/implicactionEvent";
import {HighlightPoint} from "../../models/highlight-point";
import {forkJoin} from "rxjs";
import {TopicService} from "../../../forum/services/topic.service";
import {Topic} from "../../../forum/model/topic";


@Component({
  selector: 'app-index',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  latestTopics: Topic[] = [];
  latestJobs: JobPosting[] = [];
  latestEvents: ImplicactionEvent[] = [];
  univers = Univers;
  isLoading = true;
  highlightPoints: HighlightPoint[] = [
    {
      title: 'Un réseau',
      text: 'Un réseau d’anciens militaires et civils des armés reconvertis ou en phase de reconversion partageant leur expérience et leurs connaissances',
      imageUrl: '/assets/img/reseau.png'
    },
    {
      title: 'Une dynamique',
      text: 'Une dynamique simple mais efficace répartie sur l’ensemble du territoire, au coeur des zones de Défense et des bassins d’emploi',
      imageUrl: '/assets/img/dynamique.png'
    },
    {
      title: 'Un forum',
      text: 'Un espace d’échanges, des réponses à vos questions sur différents sujets liés aux métiers, aux dispositifs de reconversion …',
      imageUrl: '/assets/img/forum.png'
    }
  ]

  constructor(
    private topicService: TopicService,
    private jobService: JobService,
    private eventService: ImplicactionEventService
  ) {
  }

  ngOnInit(): void {
    forkJoin({
      latestTopics: this.topicService.getLatestTopics(Constants.LATEST_TOPICS_COUNT),
      latestJobs: this.jobService.getLatestJobs(Constants.LATEST_JOBS_COUNT),
      latestEvents: this.eventService.getLatestEvents()
    })
      .pipe(finalize(() => this.isLoading = false))
      .subscribe({
        next: value => {
          this.latestTopics = value.latestTopics;
          this.latestJobs = value.latestJobs;
          this.latestEvents = value.latestEvents;
        }
      });
  }

}
