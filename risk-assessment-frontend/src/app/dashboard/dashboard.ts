import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RiskService } from '../services/risk.service';
import { Risk, RiskStatus } from '../models/risk.model';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
  risks: Risk[] = [];
  filteredRisks: Risk[] = [];
  selectedStatus: string = 'ALL';
  loading = false;
  error: string | null = null;

  constructor(private riskService: RiskService) {}

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
        this.error = 'Failed to load risks. Please try again.';
        this.loading = false;
        console.error('Error loading risks:', error);
      }
    });
  }

  filterRisks(): void {
    if (this.selectedStatus === 'ALL') {
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
          this.error = 'Failed to delete risk. Please try again.';
          console.error('Error deleting risk:', error);
        }
      });
    }
  }

  getRiskTypeDisplay(type: string): string {
    switch (type) {
      case 'MARKET_PRACTICE': return 'Market Practice';
      case 'CONFLICT_OF_INTEREST': return 'Conflict of Interest';
      case 'PRICING': return 'Pricing';
      case 'REGULATORY': return 'Regulatory';
      case 'GOVERNANCE': return 'Governance';
      default: return type;
    }
  }

  getRiskProbabilityDisplay(probability: string): string {
    switch (probability) {
      case 'LOW': return 'Low';
      case 'MEDIUM': return 'Medium';
      case 'HIGH': return 'High';
      default: return probability;
    }
  }

  getRiskStatusDisplay(status: string): string {
    switch (status) {
      case 'OPEN': return 'Open';
      case 'IN_PROGRESS': return 'In Progress';
      case 'CLOSED': return 'Closed';
      default: return status;
    }
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
