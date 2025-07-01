export interface Risk {
  riskId?: number;
  riskDate: string;
  riskType: RiskType;
  riskProbability: RiskProbability;
  riskStatus: RiskStatus;
  riskRemarks: string;
  riskImpact: RiskImpact;
}

export enum RiskType {
  MARKET_PRACTICE = 'MARKET_PRACTICE',
  CONFLICT_OF_INTEREST = 'CONFLICT_OF_INTEREST',
  PRICING = 'PRICING',
  REGULATORY = 'REGULATORY',
  GOVERNANCE = 'GOVERNANCE'
}

export enum RiskProbability {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export enum RiskStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  CLOSED = 'CLOSED'
}

export enum RiskImpact {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export interface RiskEnums {
  riskTypes: { value: string; displayName: string }[];
  riskProbabilities: { value: string; displayName: string }[];
  riskStatuses: { value: string; displayName: string }[];
  riskImpacts: { value: string; displayName: string }[];
}
