import { Component, OnInit } from '@angular/core';
import {Cocktail, Page} from './cocktail.model';
import { CocktailService } from './cocktail.service';

@Component({
    selector: 'app-cocktail-list',
    templateUrl: './cocktail-list.component.html',
    styleUrls: ['./cocktail-list.component.css']
})
export class CocktailListComponent implements OnInit {

    page: Page = {
        content: [],
        first: true,
        last: true,
        number: -1
    };
    currentPage = 0;
    pageSize = 10;

    constructor(private cocktailService: CocktailService) { }

    ngOnInit(): void {
        this.loadCocktails();
    }

    loadCocktails(): void {
        this.cocktailService.getCocktails(this.currentPage, this.pageSize)
            .subscribe(page => this.page = page);
    }

    setPage(page: number): void {
        this.currentPage = page;
        this.loadCocktails();
    }
}
