import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Dashboard } from './models';
// feat: apply ISP to ApiService — extract DashboardService
@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  dashboard() { return this.http.get<Dashboard>(`${this.base}/dashboard`); }
}
