import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../model/category";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  $categories: Observable<Category[]>;

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.$categories = this.categoryService.getCategories()
  }

}
