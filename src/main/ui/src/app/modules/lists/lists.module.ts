import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
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
    FormsModule,
    //
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule
  ]
})
export class ListsModule { }
