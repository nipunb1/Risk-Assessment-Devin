import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CopilotDashboardService } from '../../services/copilot-dashboard.service';
import { CopilotDashboardData } from '../../models/copilot-dashboard.model';

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

  constructor(private copilotDashboardService: CopilotDashboardService) {}

  ngOnInit(): void {
    this.loadAvailableMonths();
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
}
