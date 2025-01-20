import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomResponse } from '../interface/custom-response';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Server } from '../interface/server';

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

  save$ = (server: Server): Observable<CustomResponse> =>{
    return this.http.post<CustomResponse>(`${this.apiUrl}/server/save`, server)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  }

  ping$ = (ipAddress: string): Observable<CustomResponse> =>{
    return this.http.get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  }

  delete$ = (serverId: number): Observable<CustomResponse> =>{
    return this.http.delete<CustomResponse>(`${this.apiUrl}/server/ping/${serverId}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(() => new Error(`An error occurred - Error code: ${error.status}`));
  }
}
