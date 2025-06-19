import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CopilotDashboardData, CopilotDetailsPage } from '../models/copilot-dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class CopilotDashboardService {
  private apiUrl = 'http://localhost:8080/api/copilot-dashboard';

  constructor(private http: HttpClient) {}

  getCopilotDashboardData(reportingMonth: string): Observable<CopilotDashboardData[]> {
    return this.http.get<CopilotDashboardData[]>(`${this.apiUrl}/data?reportingMonth=${reportingMonth}`);
  }

  getAvailableMonths(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/months`);
  }

  getUniqueLobs(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/lobs`);
  }

  getProjectsByLob(lob: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/projects?lob=${lob}`);
  }

  getMonthsByLobAndProject(lob: string, projectName?: string): Observable<string[]> {
    let params = `lob=${lob}`;
    if (projectName) {
      params += `&projectName=${projectName}`;
    }
    return this.http.get<string[]>(`${this.apiUrl}/months-by-filters?${params}`);
  }

  getCopilotDetails(lob?: string, projectName?: string, reportingMonth?: string, page: number = 0, size: number = 20): Observable<CopilotDetailsPage> {
    let params = `page=${page}&size=${size}`;
    if (lob) params += `&lob=${lob}`;
    if (projectName) params += `&projectName=${projectName}`;
    if (reportingMonth) params += `&reportingMonth=${reportingMonth}`;
    
    return this.http.get<CopilotDetailsPage>(`${this.apiUrl}/details?${params}`);
  }
}
