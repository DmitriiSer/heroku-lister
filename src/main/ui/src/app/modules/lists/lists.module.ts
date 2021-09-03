import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { NewListComponent } from './new-list/new-list.component';
import { ListsComponent } from './lists.component';

@NgModule({
  declarations: [
    NewListComponent,
    ListsComponent
  ],
  imports: [
    CommonModule,
    //
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class ListsModule { }
