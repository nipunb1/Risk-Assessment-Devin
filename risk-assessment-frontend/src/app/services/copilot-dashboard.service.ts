import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CopilotDashboardData } from '../models/copilot-dashboard.model';

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
}
