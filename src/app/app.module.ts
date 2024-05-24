import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import {CocktailListComponent} from "./cocktail/cocktail-list.component";
import {CocktailService} from "./cocktail/cocktail.service";

@NgModule({
  declarations: [
    AppComponent,
    CocktailListComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    CocktailService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
