import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import{ map } from 'rxjs/operators';
import { Application, ApplicationCreate, ApplicationUpdate } from '../models/application.model';
import { ApiResponse } from '../models/api-response.model';
import { ApplicationStatus } from '../models/enums.model';

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private apiUrl = 'http://localhost:8080/api/applications';

  constructor(private http: HttpClient) {}

  getApplications(
    status?: ApplicationStatus,
    page: number = 0,
    size: number = 10,
    sortBy: string = 'createdAt',
    sortDir: string = 'DESC'
  ): Observable<Page<Application>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<ApiResponse<Page<Application>>>(this.apiUrl, { params })
      .pipe(map(response => response.data));
  }

  getApplicationById(id: number): Observable<Application> {
    return this.http.get<ApiResponse<Application>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.data));
  }

  createApplication(application: ApplicationCreate): Observable<Application> {
    return this.http.post<ApiResponse<Application>>(this.apiUrl, application)
      .pipe(map(response => response.data));
  }

  updateApplication(id: number, application: ApplicationUpdate): Observable<Application> {
    return this.http.put<ApiResponse<Application>>(`${this.apiUrl}/${id}`, application)
      .pipe(map(response => response.data));
  }

  updateStatus(id: number, status: ApplicationStatus): Observable<Application> {
    const params = new HttpParams().set('status', status);
    return this.http.patch<ApiResponse<Application>>(`${this.apiUrl}/${id}/status`, null, { params })
      .pipe(map(response => response.data));
  }

  deleteApplication(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(() => undefined));
  }
}
