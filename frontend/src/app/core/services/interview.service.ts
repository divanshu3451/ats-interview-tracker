import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Interview } from '../models/interview.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class InterviewService {
  private apiUrl = 'http://localhost:8080/api/interviews';

  constructor(private http: HttpClient) {}

  getInterviewById(id: number): Observable<Interview> {
    return this.http.get<ApiResponse<Interview>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.data));
  }

  getInterviewsByApplication(applicationId: number): Observable<Interview[]> {
    return this.http.get<ApiResponse<Interview[]>>(`${this.apiUrl}/application/${applicationId}`)
      .pipe(map(response => response.data));
  }

  getUpcomingInterviews(daysAhead: number = 7): Observable<Interview[]> {
    const params = new HttpParams().set('daysAhead', daysAhead.toString());
    return this.http.get<ApiResponse<Interview[]>>(`${this.apiUrl}/upcoming`, { params })
      .pipe(map(response => response.data));
  }

  createInterview(applicationId: number, interview: Partial<Interview>): Observable<Interview> {
    return this.http.post<ApiResponse<Interview>>(`${this.apiUrl}/application/${applicationId}`, interview)
      .pipe(map(response => response.data));
  }

  updateInterview(id: number, interview: Partial<Interview>): Observable<Interview> {
    return this.http.put<ApiResponse<Interview>>(`${this.apiUrl}/${id}`, interview)
      .pipe(map(response => response.data));
  }

  deleteInterview(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(() => undefined));
  }
}
