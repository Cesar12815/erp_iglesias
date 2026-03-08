import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Offering, OfferingPayload } from './models';
// feat: apply ISP to ApiService — extract OfferingService
@Injectable({ providedIn: 'root' })
export class OfferingService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  listOfferings() { return this.http.get<Offering[]>(`${this.base}/offerings`); }
  createOffering(payload: OfferingPayload) { return this.http.post<Offering>(`${this.base}/offerings`, payload); }
}
