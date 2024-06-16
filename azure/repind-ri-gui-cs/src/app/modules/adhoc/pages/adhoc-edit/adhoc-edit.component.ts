import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import readXlsxFile from 'read-excel-file';
import {MatStepper} from '@angular/material/stepper';
import {ArrayDisplayRefTableOption} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import {AdhocService} from '../../../../core/services/adhoc/adhoc.service';
import {HttpErrorResponse} from '@angular/common/http';
import {CommonService} from '../../../../core/services/common.service';
import {AdhocRequest} from '../../../../shared/models/requests/adhoc/adhoc-request';
import {LoaderService} from '../../../../core/loader/_services/loader.service';
import {MatDialog} from '@angular/material/dialog';
import {AdhocHeaderErrorModalComponent} from '../../components/adhoc-header-error-modal/adhoc-header-error-modal.component';
import {Subject, Subscription} from 'rxjs';
import {EventActionService} from '../../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import {EventActionMessage, EventActionMessageEnum} from '../../../../shared/models/EventActionMessage';
import {MAPPER_COLUMNS} from '../../adhoc.const';
import {ConfirmationModalComponent} from '../../../../shared/components/confirmation-modal/confirmation-modal.component';
import {MIME_TYPES} from '../../../../shared/constant/common.const';
import {DateHelper} from '../../../../shared/utils/helpers/date.helper';
import {TranslateService} from '@ngx-translate/core';


@Component({
  selector: 'app-adhoc-edit',
  templateUrl: './adhoc-edit.component.html',
  styleUrls: ['./adhoc-edit.component.scss']
})
export class AdhocEditComponent implements OnInit {

  public currentAirline!: 'AF' | 'KL';
  private readonly MAPPER_COLUMNS = MAPPER_COLUMNS;
  private readonly COLUMNS = Object.keys(this.MAPPER_COLUMNS);
  public dataSourceInvalidAdhoc: any;
  public dataSourceAdhoc: any;
  public confirmUpload = false;
  public option: ArrayDisplayRefTableOption;
  public subscription: Subscription;
  private refreshInvalidActionSource = new Subject<EventActionMessage>();
  public refreshInvalidActionSource$ = this.refreshInvalidActionSource.asObservable();
  private refreshValidActionSource = new Subject<EventActionMessage>();
  public refreshValidActionSource$ = this.refreshValidActionSource.asObservable();
  public adhocReferences = {
    domains: [],
    groupTypes: [],
    civilities: [],
    subscriptionTypes: [],
    status: []
  };
  @ViewChild('stepper') stepper: MatStepper;

  public constructor(
    private eventActionService: EventActionService,
    private loaderService: LoaderService,
    public dialog: MatDialog,
    private commonService: CommonService,
    private activatedRoute: ActivatedRoute,
    private adhocService: AdhocService,
    private translateService: TranslateService) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe((data) => {
      this.currentAirline = data.airline;
    });
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = this.eventActionService.actionSentToBeApply$.subscribe(
      message => {

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          this.adhocValidationAndRefresh(message.data);
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          this.removeAdhocData(message.data);
        }
      });
    this.adhocService.getReferencesAdhoc().then(
      (result) => {
        this.adhocReferences.domains = result[0];
        this.adhocReferences.groupTypes = result[1];
        this.adhocReferences.civilities = result[2];
        this.adhocReferences.subscriptionTypes = result[3];
        this.adhocReferences.status = result[4];
      }
    ).catch((err: HttpErrorResponse) => this.commonService.handleError(err));
  }

  /**
   * Function used to load data from excel file
   *
   */

  loadAdhoc() {

    this.commonService.selectFile(MIME_TYPES.EXCEL_FILE, false).then((file: File) => {
        this.loaderService.show();
        DateHelper.startTimer();
        readXlsxFile(file).then((rows: string[][]) => {
          const headers = rows.shift();
          DateHelper.endTimer('read excel file');
          const invalidHeaders = this.getInvalidHeaders(headers);
          if (invalidHeaders.length) {
            this.loaderService.hide();
            this.dialog.open(AdhocHeaderErrorModalComponent, {
              width: '60vw',
              data: {element: invalidHeaders, headers: this.COLUMNS},
              autoFocus: false
            });
          } else {
            DateHelper.startTimer();
            this.dataSourceAdhoc = rows.map((row, id) => {
                const mapping = row.reduce((result, cell, idx) => {
                  result[this.MAPPER_COLUMNS[headers[idx]]] = cell;
                  return result;
                }, {});
                return {...mapping, id: id + 1, errors: []};
              },
            );
            DateHelper.endTimer('formatting excel file');

            DateHelper.startTimer();
            this.adhocService.validation(new AdhocRequest(this.dataSourceAdhoc), this.currentAirline)
              .toPromise()
              .then((data: any) => {
                DateHelper.endTimer('validating json');
                this.dataSourceInvalidAdhoc = data?.result;
                const invalidIds = this.dataSourceInvalidAdhoc.map(item => item.id);
                DateHelper.startTimer();
                this.dataSourceAdhoc = this.dataSourceAdhoc.filter(itemAdhoc => !invalidIds.includes(itemAdhoc.id));
                DateHelper.endTimer('filtering invalid json');
                if(this.dataSourceAdhoc.length) {
                  this.refreshValidActionSource.next(new EventActionMessage(EventActionMessageEnum.REFRESH, this.dataSourceAdhoc));
                  this.changeStep('NEXT');
                } else {
                  this.commonService.showMessage(this.translateService.instant('NO-DATA-VALID-ADHOC-AT-UPLOAD'));
                }
              })
              .catch((err: HttpErrorResponse) => this.commonService.handleError(err));
          }
        });

      }
    );

  }


  /**
   * Get the headers that are invalid compared to the this.COLUMNS
   *
   * @param headers array content column title
   * @returns array return array that contains invalid headers
   */

  private getInvalidHeaders(headers: string[]): any[] {
    return !headers.length ? [...this.COLUMNS] : this.COLUMNS.filter((col, index) => col !== headers[index]);
  }

  /**
   * Method used to Upload adhoc data
   *
   */
  uploadConfirm() {
    const modalRef = this.dialog.open(ConfirmationModalComponent, {
      width: '40vw',
      data: {title: 'UPLOAD', message: this.dataSourceInvalidAdhoc.length ? 'UPLOAD-ADHOC-MESSAGE' : 'UPLOAD-ADHOC-MESSAGE-CONFIRM'},
      autoFocus: false
    });
    modalRef.afterClosed().subscribe(reason => {
      if (reason) {
        this.upload();
      }
    });

  }

  /**
   * Method used to Upload adhoc data
   *
   */
  upload() {
    this.adhocService.upload(new AdhocRequest(this.dataSourceAdhoc), this.currentAirline)
      .toPromise()
      .then((data: any) => {
        const isOk = data;
        if (isOk) {
          this.changeStep('RESET');
          this.commonService.showMessage('SUCCESS-UPLOAD-MESSAGE');
        }
      })
      .catch((err: HttpErrorResponse) => this.commonService.handleError(err));
  }

  /**
   * Edit upload
   *
   * @param stepUpload boolean
   */
  goToUploadStep(stepUpload?: boolean) {
    this.confirmUpload = !stepUpload;
    stepUpload ? this.changeStep('PREVIOUS') : this.changeStep('NEXT');
  }

  /**
   * Change Step function
   *
   * @param nextState
   */
  private changeStep(nextState: 'NEXT' | 'PREVIOUS' | 'RESET') {
    window.setTimeout(() => {
      switch (nextState) {
        case 'NEXT':
          this.stepper.next();
          break;
        case 'PREVIOUS':
          this.stepper.previous();
          break;
        case 'RESET':
          this.stepper.reset();
          break;
      }
    }, 100);
  }

  /**
   * Reload file and fetch data
   *
   * @param stepUpload
   */

  public reloadAdhoc(stepUpload?: boolean) {
    if (stepUpload) {
      this.confirmUpload = false;
    }

    const modalRef = this.dialog.open(ConfirmationModalComponent, {
      width: '40vw',
      data: {title: 'RELOAD', message: 'RELOAD-ADHOC-MESSAGE'},

      autoFocus: false
    });

    modalRef.afterClosed().subscribe(reason => {
      if (reason) {
        this.dataSourceInvalidAdhoc = [];
        this.dataSourceAdhoc = [];
        this.changeStep('RESET');
      }
    });

  }


  /**
   * Method used to select file
   *
   * @param contentType  file type
   * @param multiple multiple selection or not
   * @returns
   */


  /**
   * Validation of adhoc data
   * And refresh adhoc data source
   *
   * @param request Data to be checked
   */
  adhocValidationAndRefresh(request: any) {
    this.adhocService.validation(new AdhocRequest([request]), this.currentAirline)
      .toPromise()
      .then((data: any) => {
        const dataInvalid = data?.result;
        if (!dataInvalid.length) {
          this.updateValidAdhocDataSource(request);
          this.removeInvalidAdhocDataSource(request);
        } else {
          this.updateInvalidAdhocDataSource(dataInvalid[0]);
        }

      })
      .catch((err: HttpErrorResponse) => this.commonService.handleError(err));
  }

  /**
   * Remove data from  invalid adhoc and valid data source
   *
   * @param deleteData Data to be removed
   */
  removeAdhocData(deleteData: any) {
    this.removeInvalidAdhocDataSource(deleteData);
    this.removeValidAdhocDataSource(deleteData);

  }

  /**
   * Remove invalid data according to the given delete data
   *
   * @param deleteData data to be deleted
   */
  removeInvalidAdhocDataSource(deleteData: any) {
    const indexToReplace = this.dataSourceInvalidAdhoc.findIndex(item => item.id === deleteData.id);
    if (indexToReplace !== -1) {
      this.dataSourceInvalidAdhoc.splice(this.dataSourceInvalidAdhoc.findIndex(item => item.id === deleteData.id), 1);
      this.refreshInvalidActionSource.next(new EventActionMessage(EventActionMessageEnum.REFRESH, this.dataSourceInvalidAdhoc));
    }
  }

  /**
   * Remove valid data according to the given delete data
   *
   * @param deleteData data to be deleted
   */
  removeValidAdhocDataSource(deleteData: any) {
    const indexToReplace = this.dataSourceAdhoc.findIndex(item => item.id === deleteData.id);
    if (indexToReplace !== -1) {
      this.dataSourceAdhoc.splice(this.dataSourceAdhoc.findIndex(item => item.id === deleteData.id), 1);
      this.refreshValidActionSource.next(new EventActionMessage(EventActionMessageEnum.REFRESH, this.dataSourceAdhoc));
    }
  }

  /**
   * Update data source
   *
   * @param updateData data to be updated
   */

  updateAdhocDataSource(updateData: any) {
    this.updateInvalidAdhocDataSource(updateData);
    this.updateValidAdhocDataSource(updateData);
  }

  /**
   * Update invalid data source
   *
   * @param updateData data to be updated
   */
  updateInvalidAdhocDataSource(updateData: any) {
    const indexToReplace = this.dataSourceInvalidAdhoc.findIndex((item) => item.id === updateData.id);
    if (indexToReplace !== -1) {
      this.dataSourceInvalidAdhoc.splice(indexToReplace, 1, updateData);
      this.refreshInvalidActionSource.next(new EventActionMessage(EventActionMessageEnum.REFRESH, this.dataSourceInvalidAdhoc));

    }

  }

  /**
   * Update valid data source
   *
   * @param updateData data to be updated
   */
  updateValidAdhocDataSource(updateData: any) {
    const indexToReplace = this.dataSourceAdhoc.findIndex((item) => item.id === updateData.id);
    if (indexToReplace === -1) {
      updateData['errors'] = [];
      this.dataSourceAdhoc.push(updateData);
      this.refreshValidActionSource.next(new EventActionMessage(EventActionMessageEnum.REFRESH, this.dataSourceAdhoc));
    }
  }


}
