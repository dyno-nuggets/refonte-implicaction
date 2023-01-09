import {Component, OnInit} from '@angular/core';
import {ToasterService} from "../../../../core/services/toaster.service";
import {GroupService} from "../../../../discussion/services/group.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../../user/services/user.service";


import {finalize, take} from "rxjs/operators";
import {Pageable} from "../../../../shared/models/pageable";
import {User} from "../../../../shared/models/user";
import {Constants} from "../../../../config/constants";
import {JobService} from "../../../../job/services/job.service";
import {CompanyService} from "../../../../company/services/company.service";

@Component({
  selector: 'app-admin-overview',
  templateUrl: './admin-overview.component.html',
  styleUrls: ['./admin-overview.component.scss']
})
export class AdminOverviewComponent implements OnInit{
   public nbUsers = 0 ;
   public nbOffres=0;
  public nbCompanies=0;

  constructor(
    private  userService : UserService,
    private jobService : JobService,
    private CompanyService:CompanyService,

  ) {
  }

  ngOnInit(): void {
    this.getNbUsers();
    this.getNbOfJobs();
    this.getNbOfCompanies()
  }
  getNbUsers(){
    this.userService.getNbOfUsers().subscribe(res =>{
      this.nbUsers = res;
    })
  }
  getNbOfJobs(){
    this.jobService.getNbOfJobs().subscribe(res=>{
      this.nbOffres=res;
    })
  }
  getNbOfCompanies(){
    this.CompanyService.getNbCompanies().subscribe(res=>{
      this.nbCompanies=res;
})
}

  }





