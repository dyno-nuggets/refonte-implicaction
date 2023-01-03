import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';


@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})

export class TagsComponent implements OnInit {

  li: any;
  lis = [];

  checked_tag1: boolean = true;
  checked_tag2: boolean = true;
  checked_tag3: boolean = true;

  stateOptions: any[];
  tagOptions: any[];

  value1: string = "off";
  value2: number;
  value3: any;


  constructor(private http: HttpClient) {

    this.stateOptions = [{label: 'Off', value: 'off'}, {label: 'On', value: 'on'}];

    this.tagOptions = [
      {name: 'Associatons', value: 1},
      {name: 'Metiers', value: 2},
      {name: 'Cooperations', value: 3},
      {name: 'Insertions', value: 4},
      {name: 'Benevolats', value: 5}
    ];

  }

  find_tag1() {

  }

  find_tag2() {

  }

  find_tag3() {

  }


  ngOnInit(): void {
    this.http.get('')
      .subscribe(Response => {

        console.log(Response)
        this.li = Response;
        this.lis = this.li.list;
      });
  }
}
