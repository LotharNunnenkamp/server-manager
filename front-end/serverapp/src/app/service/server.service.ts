import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomResponse } from '../interface/custom-response';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Server } from '../interface/server';
import { Status } from '../enum/status.enum';

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

  filter$ = (status: Status, response: CustomResponse): Observable<CustomResponse> => {
    return new Observable<CustomResponse>(
      subscriber => {
        console.log(response);
        subscriber.next(
          status === Status.ALL ? {...response, message: `Servers filter by ${status} status`} :
          {
            ...response,
            message: response.data.servers!
            .filter(server => server.status === status).length > 0 ?
            `Servers filter by ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status` :
            `No servers of ${status} found`,
            data: {
              servers: response.data.servers!.filter(server => server.status === status)
            }
          }
        );
        subscriber.complete();
      }
    )
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
