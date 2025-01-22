import { Component, OnInit, signal } from '@angular/core';
import { ServerService } from './service/server.service';
import { catchError, map, Observable, of, startWith } from 'rxjs';
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

  appState$: Observable<AppState<CustomResponse>> = of({
    dataState: DataState.LOADED_STATE,
    });

  constructor(private serverService: ServerService) { }

  ngOnInit(): void {
    if (this.serverService.servers$) {
      this.appState$ = this.serverService.servers$
        .pipe(
          map(response => {
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
          })
        )
    } else {
      this.appState$ = of({
        dataState: DataState.ERROR_STATE,
        error: 'No data available'
      })
    }
  }
}
