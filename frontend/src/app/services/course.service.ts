import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course, CoursePayload } from './models';
// feat: apply ISP to ApiService — extract CourseService
@Injectable({ providedIn: 'root' })
export class CourseService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  listCourses() { return this.http.get<Course[]>(`${this.base}/courses`); }
  createCourse(payload: CoursePayload) { return this.http.post<Course>(`${this.base}/courses`, payload); }
}
