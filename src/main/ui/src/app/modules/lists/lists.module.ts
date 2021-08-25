import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';

import { NewListComponent } from './new-list/new-list.component';

@NgModule({
  declarations: [
    NewListComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule
  ]
})
export class ListsModule { }
