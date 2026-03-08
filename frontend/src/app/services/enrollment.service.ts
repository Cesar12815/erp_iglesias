import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Enrollment, EnrollmentPayload } from './models';
// feat: apply ISP to ApiService — extract EnrollmentService (frontend)
@Injectable({ providedIn: 'root' })
export class EnrollmentService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  listEnrollments() { return this.http.get<Enrollment[]>(`${this.base}/enrollments`); }
  createEnrollment(payload: EnrollmentPayload) { return this.http.post<Enrollment>(`${this.base}/enrollments`, payload); }
}
