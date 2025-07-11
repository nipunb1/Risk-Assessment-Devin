import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RiskService } from '../services/risk.service';
import { Risk, RiskType, RiskProbability, RiskStatus, RiskImpact, RiskEnums } from '../models/risk.model';

@Component({
  selector: 'app-risk-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './risk-form.html',
  styleUrl: './risk-form.css'
})
export class RiskFormComponent implements OnInit {
  risk: Risk = {
    riskDate: new Date().toISOString().split('T')[0],
    riskType: RiskType.MARKET_PRACTICE,
    riskProbability: RiskProbability.LOW,
    riskDesc: '',
    riskStatus: RiskStatus.OPEN,
    riskRemarks: '',
    riskImpact: RiskImpact.LOW
  };
  
  isEditMode = false;
  loading = false;
  error: string | null = null;
  enumValues: RiskEnums | null = null;

  constructor(
    private riskService: RiskService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadEnumValues();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadRisk(+id);
    }
  }

  loadEnumValues(): void {
    this.riskService.getEnumValues().subscribe({
      next: (enums) => {
        this.enumValues = enums;
      },
      error: (error) => {
        console.error('Error loading enum values:', error);
      }
    });
  }

  loadRisk(id: number): void {
    this.loading = true;
    this.riskService.getRiskById(id).subscribe({
      next: (risk) => {
        this.risk = risk;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load risk. Please try again.';
        this.loading = false;
        console.error('Error loading risk:', error);
      }
    });
  }

  onSubmit(): void {
    if (!this.validateForm()) {
      return;
    }

    this.loading = true;
    this.error = null;

    const operation = this.isEditMode 
      ? this.riskService.updateRisk(this.risk.riskId!, this.risk)
      : this.riskService.createRisk(this.risk);

    operation.subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.error = this.isEditMode 
          ? 'Failed to update risk. Please try again.'
          : 'Failed to create risk. Please try again.';
        this.loading = false;
        console.error('Error saving risk:', error);
      }
    });
  }

  validateForm(): boolean {
    if (!this.risk.riskDesc.trim()) {
      this.error = 'Risk description is required.';
      return false;
    }
    if (!this.risk.riskDate) {
      this.error = 'Risk date is required.';
      return false;
    }
    return true;
  }

  onCancel(): void {
    this.router.navigate(['/dashboard']);
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

  getRiskImpactDisplay(impact: string): string {
    switch (impact) {
      case 'LOW': return 'Low';
      case 'MEDIUM': return 'Medium';
      case 'HIGH': return 'High';
      default: return impact;
    }
  }
}
