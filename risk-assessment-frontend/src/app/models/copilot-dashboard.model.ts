export interface CopilotDashboardData {
  lob: string;
  licenseUsage: number;
  actualLicenseUsage: number;
  applicableEfficiency: number;
  totalProjects: number;
}

export interface CopilotDetailsData {
  lob: string;
  lobMinusOne: string;
  projectName: string;
  reportingMonth: string;
  projectHeadcount: number;
  engineeringPopulationPercent: number;
  licenseUsage: number;
  actualLicenseUsage: number;
  applicableEfficiency: number;
}

export interface CopilotDetailsPage {
  content: CopilotDetailsData[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
  pageSize: number;
  hasNext: boolean;
  hasPrevious: boolean;
}
