import { ArrayDisplayRefTableColumn } from "../arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn";

/**
 * config for AddRemoveManyActionComponents (manage the association between two table linked by an association table)
 * 
 * NB: for the term used 
 *  - table linked : the table on the other side of the association table 
 *  - calling table : the table used in the calling component 
 * 
 * schema: 
 * 
 * ┌─────────────────┐        ┌────────────────────┐        ┌────────────────┐
 * │                 │        │                    │        │                │
 * │  Calling table  ├────────┤ association table  ├────────┤  Table linked  │
 * │                 │        │                    │        │                │
 * └─────────────────┘        └────────────────────┘        └────────────────┘
 * 
 */
export class TableLinkedOptionConfig {

    private _tableLinkedName: string;

    private _tableLinkedIdName: string;

    private _tableLinkedData: any[];

    private _tableLinkedColumnName: ArrayDisplayRefTableColumn[];

    private _listNameForRequestBody: string;


    private _callingTableIdName: string;


    constructor(tableLinkedName: string, tableLinkedIdName: string, tableLinkedData: any[],
        tableLinkedColumnName: ArrayDisplayRefTableColumn[], listNameForRequestBody: string, callingTableIdName: string) {
        this._tableLinkedName = tableLinkedName;
        this._tableLinkedIdName = tableLinkedIdName;
        this._tableLinkedData = tableLinkedData;
        this._tableLinkedColumnName = tableLinkedColumnName;
        this._listNameForRequestBody = listNameForRequestBody;
        this._callingTableIdName = callingTableIdName;
    }

    public get tableLinkedIdName(): string {
        return this._tableLinkedIdName;
    }

    public get tableLinkedData(): any[] {
        return this._tableLinkedData;
    }

    public get tableLinkedColumnName(): ArrayDisplayRefTableColumn[] {
        return this._tableLinkedColumnName;
    }

    public get callingTableIdName(): string {
        return this._callingTableIdName;
    }

    public get listNameForRequestBody(): string {
        return this._listNameForRequestBody;
    }

    public get tableLinkedName(): string {
        return this._tableLinkedName;
    }


}