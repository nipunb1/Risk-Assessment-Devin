import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CopilotDashboardService } from '../../services/copilot-dashboard.service';
import { CopilotDashboardData, CopilotDetailsData, CopilotDetailsPage } from '../../models/copilot-dashboard.model';

@Component({
  selector: 'app-copilot-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './copilot-dashboard.html',
  styleUrl: './copilot-dashboard.css'
})
export class CopilotDashboardComponent implements OnInit {
  dashboardData: CopilotDashboardData[] = [];
  availableMonths: string[] = [];
  selectedMonth: string = '';
  loading = false;
  error: string | null = null;

  detailsData: CopilotDetailsData[] = [];
  detailsPage: CopilotDetailsPage | null = null;
  
  availableLobs: string[] = [];
  availableProjects: string[] = [];
  availableDetailMonths: string[] = [];
  
  selectedLob: string = '';
  selectedProject: string = '';
  selectedDetailMonth: string = '';
  
  currentPage: number = 0;
  pageSize: number = 20;
  
  detailsLoading = false;
  filtersLoading = false;

  constructor(private copilotDashboardService: CopilotDashboardService) {}

  ngOnInit(): void {
    this.loadAvailableMonths();
    this.loadFilterOptions();
  }

  loadAvailableMonths(): void {
    this.loading = true;
    this.copilotDashboardService.getAvailableMonths().subscribe({
      next: (months) => {
        this.availableMonths = months;
        if (months.length > 0) {
          this.selectedMonth = months[0];
          this.loadDashboardData();
        }
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load available months. Please try again.';
        this.loading = false;
        console.error('Error loading months:', error);
      }
    });
  }

  loadDashboardData(): void {
    if (!this.selectedMonth) return;
    
    this.loading = true;
    this.error = null;
    
    this.copilotDashboardService.getCopilotDashboardData(this.selectedMonth).subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load dashboard data. Please try again.';
        this.loading = false;
        console.error('Error loading dashboard data:', error);
      }
    });
  }

  onMonthChange(): void {
    this.loadDashboardData();
  }

  loadFilterOptions(): void {
    this.filtersLoading = true;
    this.copilotDashboardService.getUniqueLobs().subscribe({
      next: (lobs) => {
        this.availableLobs = ['All', ...lobs];
        this.filtersLoading = false;
      },
      error: (error) => {
        this.error = 'Failed to load filter options.';
        this.filtersLoading = false;
      }
    });
  }

  onLobChange(): void {
    this.selectedProject = '';
    this.selectedDetailMonth = '';
    this.availableProjects = [];
    this.availableDetailMonths = [];
    
    if (this.selectedLob && this.selectedLob !== 'All') {
      this.copilotDashboardService.getProjectsByLob(this.selectedLob).subscribe({
        next: (projects) => {
          this.availableProjects = ['All', ...projects];
        }
      });
    }
  }

  onProjectChange(): void {
    this.selectedDetailMonth = '';
    this.availableDetailMonths = [];
    
    if (this.selectedLob && this.selectedLob !== 'All') {
      const projectParam = this.selectedProject && this.selectedProject !== 'All' ? this.selectedProject : undefined;
      this.copilotDashboardService.getMonthsByLobAndProject(this.selectedLob, projectParam).subscribe({
        next: (months) => {
          this.availableDetailMonths = ['All', ...months];
        }
      });
    }
  }

  searchDetails(): void {
    this.currentPage = 0;
    this.loadDetailsData();
  }

  loadDetailsData(): void {
    this.detailsLoading = true;
    
    const lobParam = this.selectedLob && this.selectedLob !== 'All' ? this.selectedLob : undefined;
    const projectParam = this.selectedProject && this.selectedProject !== 'All' ? this.selectedProject : undefined;
    const monthParam = this.selectedDetailMonth && this.selectedDetailMonth !== 'All' ? this.selectedDetailMonth : undefined;
    
    this.copilotDashboardService.getCopilotDetails(lobParam, projectParam, monthParam, this.currentPage, this.pageSize).subscribe({
      next: (page) => {
        this.detailsPage = page;
        this.detailsData = page.content;
        this.detailsLoading = false;
      },
      error: (error) => {
        this.error = 'Failed to load details data.';
        this.detailsLoading = false;
      }
    });
  }

  goToPage(page: number): void {
    this.currentPage = page;
    this.loadDetailsData();
  }

  nextPage(): void {
    if (this.detailsPage?.hasNext) {
      this.goToPage(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.detailsPage?.hasPrevious) {
      this.goToPage(this.currentPage - 1);
    }
  }
}
