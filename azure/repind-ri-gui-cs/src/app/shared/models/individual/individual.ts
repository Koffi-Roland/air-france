import { ManagementData } from './management-data';
import { ProfileData } from './profile-data';
import { IndividualResume } from './individual-resume';
import { IndividualTypes } from '../common/individual-types';

export class Individual {

    constructor(private _gin: string, private _type: string, private _civility: string, private _status: string,
        private _lastname: string, private _firstname: string, private _lastNameAlias: string, private _firstNameAlias: string,
        private _secondFirstName: string, private _sexe: string, private _birthdate: Date, private _title: string,
        private _resume?: IndividualResume, private _profile?: ProfileData, private _managementData?: ManagementData) { }

    get gin(): string {
        return this._gin;
    }
    get type(): string {
        return this._type;
    }
    get civility(): string {
        return this._civility;
    }
    get status(): string {
        return this._status;
    }
    get lastname(): string {
        return this._lastname;
    }
    get firstname(): string {
        return this._firstname;
    }
    get lastNameAlias(): string {
        return this._lastNameAlias;
    }
    get firstNameAlias(): string {
        return this._firstNameAlias;
    }
    get secondFirstName(): string {
        return this._secondFirstName;
    }
    get sexe(): string {
        return this._sexe;
    }
    get birthdate(): Date {
        return this._birthdate;
    }
    get title(): string {
        return this._title;
    }
    get profile(): ProfileData {
        return this._profile;
    }
    get managementData(): ManagementData {
        return this._managementData;
    }
    get resume(): IndividualResume {
        return this._resume;
    }
    get fullname(): string {
        if (this._lastname === null && this._firstname === null) {
            return this._type + '-INDIVIDUAL-TYPE';
        } else {
            return this._lastname + ' ' + this._firstname;
        }
    }

}
