export interface Risk {
  riskId?: number;
  riskDate: string;
  riskType: RiskType;
  riskProbability: RiskProbability;
  riskDesc: string;
  riskStatus: RiskStatus;
  riskRemarks: string;
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

export interface RiskEnums {
  riskTypes: { value: string; displayName: string }[];
  riskProbabilities: { value: string; displayName: string }[];
  riskStatuses: { value: string; displayName: string }[];
}
