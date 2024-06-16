import { BasicIndividualData } from '../individual/basic-individual-data';

export class SearchIndividualResponse {

    constructor(private _count: number, private _data: BasicIndividualData[]) { }

    get count(): number {
        return this._count;
    }

    get data(): BasicIndividualData[] {
        return this._data;
    }

}
