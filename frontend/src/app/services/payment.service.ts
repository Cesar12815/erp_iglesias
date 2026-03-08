import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Payment } from './models';
// feat: apply ISP to ApiService — extract PaymentService
@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  listPayments(status?: string) {
    const params = status ? `?status=${status}` : '';
    return this.http.get<Payment[]>(`${this.base}/payments${params}`);
  }
  confirmPayment(id: number) { return this.http.post<Payment>(`${this.base}/payments/${id}/confirm`, {}); }
  failPayment(id: number) { return this.http.post<Payment>(`${this.base}/payments/${id}/fail`, {}); }
  retryPayment(id: number) { return this.http.post<Payment>(`${this.base}/payments/${id}/retry`, {}); }
}
