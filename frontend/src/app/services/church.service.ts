import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Church } from './models';
// feat: apply ISP to ApiService — extract ChurchService
// ADR-08: cada componente inyecta solo el servicio que necesita

@Injectable({ providedIn: 'root' })
export class ChurchService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}
  getChurch() { return this.http.get<Church>(`${this.base}/church`); }
  createChurch(name: string, address: string) { return this.http.post<Church>(`${this.base}/church`, { name, address }); }
}
