import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RiskService } from '../services/risk.service';
import { EnumUtilityService } from '../services/enum-utility.service';
import { ErrorHandlingService } from '../services/error-handling.service';
import { Risk, RiskStatus } from '../models/risk.model';
import { FILTER_CONSTANTS } from '../constants/filter-constants';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
  risks: Risk[] = [];
  filteredRisks: Risk[] = [];
  selectedStatus: string = FILTER_CONSTANTS.ALL;
  loading = false;
  error: string | null = null;

  constructor(
    private riskService: RiskService,
    private enumUtility: EnumUtilityService,
    private errorHandler: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    this.loadRisks();
  }

  loadRisks(): void {
    this.loading = true;
    this.error = null;
    
    this.riskService.getAllRisks().subscribe({
      next: (risks) => {
        this.risks = risks;
        this.filterRisks();
        this.loading = false;
      },
      error: (error) => {
        this.error = this.errorHandler.getErrorMessage('load risks');
        this.loading = false;
        this.errorHandler.handleError(error, this.error);
      }
    });
  }

  filterRisks(): void {
    if (this.selectedStatus === FILTER_CONSTANTS.ALL) {
      this.filteredRisks = this.risks;
    } else {
      this.filteredRisks = this.risks.filter(risk => risk.riskStatus === this.selectedStatus);
    }
  }

  onStatusFilterChange(status: string): void {
    this.selectedStatus = status;
    this.filterRisks();
  }

  deleteRisk(id: number): void {
    if (confirm('Are you sure you want to delete this risk?')) {
      this.riskService.deleteRisk(id).subscribe({
        next: () => {
          this.loadRisks();
        },
        error: (error) => {
          this.error = this.errorHandler.getErrorMessage('delete risk');
          this.errorHandler.handleError(error, this.error);
        }
      });
    }
  }

  getRiskTypeDisplay(type: string): string {
    return this.enumUtility.getRiskTypeDisplay(type);
  }

  getRiskProbabilityDisplay(probability: string): string {
    return this.enumUtility.getRiskProbabilityDisplay(probability);
  }

  getRiskStatusDisplay(status: string): string {
    return this.enumUtility.getRiskStatusDisplay(status);
  }

  getRiskImpactDisplay(impact: string): string {
    return this.enumUtility.getRiskImpactDisplay(impact);
  }

  getProbabilityClass(probability: string): string {
    switch (probability) {
      case 'HIGH': return 'probability-high';
      case 'MEDIUM': return 'probability-medium';
      case 'LOW': return 'probability-low';
      default: return '';
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'OPEN': return 'status-open';
      case 'IN_PROGRESS': return 'status-in-progress';
      case 'CLOSED': return 'status-closed';
      default: return '';
    }
  }
}
