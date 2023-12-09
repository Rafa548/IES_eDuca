import {Component, Input} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-info-table',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './info-table.component.html',
  styleUrl: './info-table.component.css'
})
export class InfoTableComponent {
  @Input() headers: string[] = [];
  @Input() data: any[] = [];
  @Input() onDeleteRow?: (row: any) => void;
  @Input() onViewRow?: (row: any) => void;
  @Input() onEditRow?: (row: any) => void;
}
