import { Component, ElementRef, NgZone, ViewChild } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-new-list',
  templateUrl: './new-list.component.html',
  styleUrls: ['./new-list.component.scss']
})
export class NewListComponent {

  listTitle = '';
  editTitle = false;

  @ViewChild('titleInput') titleInput!: ElementRef;

  constructor(
    private zone: NgZone,
    private location: Location
  ) { }


  backdropClick() {
    this.location.back();
  }

  listTitleFocus() {
    this.editTitle = true;
    this.zone.run(() => {
      setTimeout(() => {
        this.titleInput.nativeElement.focus();
      });
    });
  }

  listTitleKeyPress(key: string) {
    if (key === 'Enter') {
      this.listTitleChange();
    }
  }

  listTitleChange() {
    this.editTitle = false;
  }

  saveList() {
  }

}
