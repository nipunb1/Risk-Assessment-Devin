import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RiskService } from '../services/risk.service';
import { EnumUtilityService } from '../services/enum-utility.service';
import { ErrorHandlingService } from '../services/error-handling.service';
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
    private enumUtility: EnumUtilityService,
    private errorHandler: ErrorHandlingService,
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
        this.errorHandler.handleError(error, 'Failed to load form options');
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
        this.error = this.errorHandler.getErrorMessage('load risk');
        this.loading = false;
        this.errorHandler.handleError(error, this.error);
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
        const operation = this.isEditMode ? 'update risk' : 'create risk';
        this.error = this.errorHandler.getErrorMessage(operation);
        this.loading = false;
        this.errorHandler.handleError(error, this.error);
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
}
