import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Cocktail, Page} from './cocktail.model';

@Injectable({
    providedIn: 'root'
})
export class CocktailService {

    private baseUrl = '/rest/cocktails';

    constructor(private http: HttpClient) { }

    getCocktails(page: number, pageSize: number): Observable<Page> {
        return this.http.get<Page>(`${this.baseUrl}?page=${page}&pageSize=${pageSize}`);
    }
}
