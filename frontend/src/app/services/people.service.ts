import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Person, PersonPayload } from './models';
// feat: apply ISP to ApiService — extract PeopleService
@Injectable({ providedIn: 'root' })
export class PeopleService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  listPeople() { return this.http.get<Person[]>(`${this.base}/people`); }
  createPerson(payload: PersonPayload) { return this.http.post<Person>(`${this.base}/people`, payload); }
}
