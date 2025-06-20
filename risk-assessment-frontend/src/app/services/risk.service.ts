import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Risk, RiskEnums } from '../models/risk.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RiskService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAllRisks(): Observable<Risk[]> {
    return this.http.get<Risk[]>(this.apiUrl);
  }

  getRiskById(id: number): Observable<Risk> {
    return this.http.get<Risk>(`${this.apiUrl}/${id}`);
  }

  createRisk(risk: Risk): Observable<Risk> {
    return this.http.post<Risk>(this.apiUrl, risk);
  }

  updateRisk(id: number, risk: Risk): Observable<Risk> {
    return this.http.put<Risk>(`${this.apiUrl}/${id}`, risk);
  }

  deleteRisk(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getRisksByStatus(status: string): Observable<Risk[]> {
    return this.http.get<Risk[]>(`${this.apiUrl}/status/${status}`);
  }

  getEnumValues(): Observable<RiskEnums> {
    return this.http.get<RiskEnums>(`${this.apiUrl}/enums`);
  }
}
