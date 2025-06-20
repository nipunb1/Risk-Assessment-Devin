import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnumUtilityService {

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
