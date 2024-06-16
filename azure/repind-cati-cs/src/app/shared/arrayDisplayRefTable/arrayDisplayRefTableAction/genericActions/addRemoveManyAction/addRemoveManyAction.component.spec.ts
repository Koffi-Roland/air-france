import { query } from '@angular/animations';
import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { TableLinkedOptionConfig } from 'src/app/shared/models/TableLinkedOptionConfig';
import { SharedModule } from 'src/app/shared/shared.module';
import { ArrayDisplayRefTableColumn } from '../../../_models/ArrayDisplayRefTableColumn';
import { EventActionService } from '../../../_services/eventAction.service';

import { AddRemoveManyActionComponent } from './addRemoveManyAction.component';

describe('AddRemoveManyActionComponent', () => {
    let component: AddRemoveManyActionComponent;
    let fixture: ComponentFixture<AddRemoveManyActionComponent>;

    let eventActionService: EventActionService;
    let debugElement: DebugElement;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AddRemoveManyActionComponent],
            imports: [TranslateModule.forRoot(), MaterialModule, MatDialogModule, SharedModule, BrowserAnimationsModule],
            providers: [{
                provide: MAT_DIALOG_DATA, useValue: {
                    options: [
                        { key: 'type', value: 'MAIN_TABLE_NAME' },
                        { key: 'id', value: 'idOfMainTable' },
                        {
                            key: 'tableLink', value: new TableLinkedOptionConfig(
                                'TABLE_LINKED_NAME',
                                'tableLinkedIdName',
                                [
                                    { tableLinkedIdName: 'AZE', firstValue: 'hello first', secondValue: 'hello second' },
                                    { tableLinkedIdName: 'WXC', firstValue: 'hello first', secondValue: 'hello second' },
                                    { tableLinkedIdName: 'QSD', firstValue: 'hello first', secondValue: 'hello second' }
                                ],
                                [
                                    new ArrayDisplayRefTableColumn('tableLinkedIdName', 'ID_OF_ASSOCIATION_TABLE'),
                                    new ArrayDisplayRefTableColumn('firstValue', 'FIRST_VALUE'),
                                    new ArrayDisplayRefTableColumn('secondValue', 'SECOND_VALUE'),

                                ],
                                'listNameForRequestBody',
                                'callingTableIdName'
                            )
                        }
                    ],
                    element:
                        { idOfMainTable: 'AAA', value: '', idList: 'AZE' }
                }
            },
            { provide: MatDialogRef, useValue: { close: () => { } } },
                EventActionService]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AddRemoveManyActionComponent);
        component = fixture.componentInstance;
        debugElement = fixture.debugElement;
        eventActionService = debugElement.injector.get(EventActionService);

        spyOn(eventActionService, 'actionSentToBeApply').and.callFake((event) => event);

        spyOn(component.dialogRef, 'close').and.callThrough();

        fixture.autoDetectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should display title', () => {
        const titleElement: HTMLElement = fixture.nativeElement.querySelector('h1');
        expect(titleElement.textContent.trim()).toEqual('UPDATE_ASSOCIATION_TITLE');
    });

    it('should display associated table', async(() => {
        fixture.detectChanges();
        const dataInTable: string[] = component.associatedTable.dataSource.data.map(d => d.tableLinkedIdName);

        expect(dataInTable.length).toEqual(3);
        expect(dataInTable.includes('AZE')).toBeTrue();
        expect(dataInTable.includes('WXC')).toBeTrue();
        expect(dataInTable.includes('QSD')).toBeTrue();
    }));

});
