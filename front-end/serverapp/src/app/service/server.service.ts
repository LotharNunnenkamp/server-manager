import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomResponse } from '../interface/custom-response';
import { catchError, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  private readonly apiUrl = 'any';

  servers$: Observable<CustomResponse> | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.servers$ = this.http.get<CustomResponse>(`${this.apiUrl}/server/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  }

  handleError(handleError: any): Observable<never> {
    return throwError(() => new Error('Method not implemented.'));
  }
}
