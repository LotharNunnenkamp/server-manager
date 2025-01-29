import { ChangeDetectionStrategy, Component, OnInit, signal } from '@angular/core';
import { ServerService } from './service/server.service';
import { BehaviorSubject, catchError, map, Observable, of, shareReplay, startWith, tap } from 'rxjs';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { DataState } from './enum/data-state.enum';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Status } from './enum/status.enum';
import { Server } from './interface/server';
import { NotificationService } from './service/notification.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit {

  readonly DataState = DataState;
  readonly Status = Status;
  appState$!: Observable<AppState<CustomResponse>>;
  private filterSubject = new BehaviorSubject<string>('');
  filterSubject$ = this.filterSubject.asObservable();
  private dataSubject = new BehaviorSubject<CustomResponse>({} as CustomResponse);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();
  selectedPrintFormat: string | null = null;

  constructor(private serverService: ServerService, private notifier: NotificationService) { }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$()
      .pipe(
        map(response => {
          this.dataSubject.next(response);
          this.notifier.showSuccess(response.message);
          return {
            dataState: DataState.LOADED_STATE,
            appData: response,
          }
        }),
        startWith({
          dataState: DataState.LOADING_STATE
        }),
        catchError((error: string) => {
          this.notifier.showError(error, 'Error');
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
          this.notifier.showInfo(response.message);
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
          this.notifier.showError(error, 'Error');
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        })
      )
  }

  filterServers(status: Status): void {
    this.appState$ = this.serverService.filter$(status, this.dataSubject.value)
      .pipe(
        map(response => {
          this.notifier.showSuccess(response.message);
          return {
            dataState: DataState.LOADED_STATE,
            appData: response,
          }
        }),
        startWith({
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value
        }),
        catchError((error: string) => {
          this.filterSubject.next('');
          this.notifier.showError(error, 'Error');
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        }),
        shareReplay(1)
      )
  }

  saveServer(form: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.serverService.save$(form.value as Server)
      .pipe(
        map(response => {
          const servers = this.dataSubject.value.data.servers || [];
          const server = response.data.server;
          if (server) {
            this.dataSubject.next(
              {
                ...response,
                data: {
                  servers: [
                    server,
                    ...servers
                  ]
                }
              });
          }
          document.getElementById('closeModal')?.click();
          this.isLoading.next(false);
          form.resetForm({ status: this.Status.SERVER_DOWN });
          this.notifier.showSuccess(response.message);
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
          this.isLoading.next(false);
          this.notifier.showError(error, 'Error');
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        }),
        shareReplay(1)
      )
  }

  deleteServer(server: Server): void {
    this.appState$ = this.serverService.delete$(server.id)
      .pipe(
        map(response => {
          this.dataSubject.next({
            ...response,
            data: {
              servers: this.dataSubject.value.data.servers?.filter(s => s.id !== server.id)
            }
          });
          this.notifier.showSuccess(response.message);
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
          this.notifier.showError(error, 'Error');
          return of({
            dataState: DataState.ERROR_STATE,
            error
          })
        }),
        shareReplay(1)
      )
  }

  printReport(printType: string): void {
    this.notifier.showSuccess("Printing report...");
    if(printType === 'PDF'){
      window.print();
    } else {
      let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
      let tableSelect = document.getElementById('servers');
      let tableHtml = tableSelect?.outerHTML.replace(/ /g, '%20');
      let downloadLink = document.createElement('a');
      document.body.appendChild(downloadLink);
      downloadLink.href = 'data:' + dataType + ', ' + tableHtml;
      downloadLink.download = 'servers-report.xls';
      downloadLink.click();
      document.body.removeChild(downloadLink);
    }
    setTimeout(() => { this.selectedPrintFormat = null; }, 500);
  }
}


