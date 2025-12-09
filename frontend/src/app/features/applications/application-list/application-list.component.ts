import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { ApplicationService, Page } from '../../../core/services/application.service';
import { Application } from '../../../core/models/application.model';

@Component({
  selector: 'app-application-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule
  ],
  templateUrl: './application-list.component.html',
  styleUrl: './application-list.component.scss'
})
export class ApplicationListComponent implements OnInit {
  applications = signal<Application[]>([]);
  isLoading = signal<boolean>(true);
  displayedColumns = ['companyName', 'jobTitle', 'status', 'appliedDate', 'actions'];

  constructor(private applicationService: ApplicationService) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications(): void {
    this.isLoading.set(true);
    this.applicationService.getApplications().subscribe({
      next: (page: Page<Application>) => {
        this.applications.set(page.content);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error:', err);
        this.isLoading.set(false);
      }
    });
  }

  getStatusColor(status: string): string {
    const colors: { [key: string]: string } = {
      'SAVED': 'accent',
      'APPLIED': 'primary',
      'SCREENING': '',
      'INTERVIEWING': 'primary',
      'OFFER': 'primary',
      'REJECTED': 'warn'
    };
    return colors[status] || '';
  }
}
