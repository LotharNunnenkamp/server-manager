import { Component, OnInit, signal } from '@angular/core';
import { ServerService } from './service/server.service';
import { BehaviorSubject, catchError, map, Observable, of, shareReplay, startWith, tap } from 'rxjs';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { DataState } from './enum/data-state.enum';
import { CommonModule } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { Status } from './enum/status.enum';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  readonly DataState = DataState;
  readonly Status = Status;
  appState$!: Observable<AppState<CustomResponse>>;
  private filterSubject = new BehaviorSubject<string>('');
  filterSubject$ = this.filterSubject.asObservable();
  private dataSubject = new BehaviorSubject<CustomResponse>({} as CustomResponse);

  constructor(private serverService: ServerService) { }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$()
      .pipe(
        map(response => {
          this.dataSubject.next(response);
          return {
            dataState: DataState.LOADED_STATE,
            appData: response,
          }
        }),
        startWith({
          dataState: DataState.LOADING_STATE
        }),
        catchError((error: string) => {
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        }),
        shareReplay(1)
      )
  }

  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);
    this.appState$ = this.serverService.ping$(ipAddress)
      .pipe(
        map(response => {
          const servers = this.dataSubject.value.data.servers;
          const server = response.data.server;
          if (servers && server) {
            const index = servers?.findIndex(s => s.id === server.id);
            servers[index] = server;
          }
          this.filterSubject.next('');
          return {
            dataState: DataState.LOADED_STATE,
            appData: this.dataSubject.value,
          }
        }),
        startWith({
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value
        }),
        catchError((error: string) => {
          this.filterSubject.next('');
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        })
      )
  }

}
