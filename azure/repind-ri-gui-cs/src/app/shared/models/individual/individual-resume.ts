export class IndividualResume {

    constructor(private _flyingBlueContractNumber: string, private _flyingBlueContractsCount: number,
        private _otherContractsCount: number, private _hasMyAccountContract: boolean,
        private _hasFlyingBlueContract: boolean, private _personnalAddressesCount: number,
        private _professionalAddressesCount: number, private _personnalEmailsCount: number,
        private _professionalEmailsCount: number, private _personnalTelecomsCount: number,
        private _professionalTelecomsCount: number, private _externalIdentifiersCount: number,
        private _optInCommPrefCount: number, private _optOutCommPrefCount: number,
        private _optInAlertsCount: number, private _optOutAlertsCount: number,
        private _preferencesCount: number, private _delegatesCount: number, private _delegatorsCount: number,
        private _UCCRRolesCount: number, private _isAFEmployee: boolean, private _isDoctor: boolean,
        private _isRestrictedType: boolean, private _hasAccountDataDeleted: boolean) {}


    get isAFEmployee(): boolean {
        return this._isAFEmployee;
    } 
    get isDoctor(): boolean {
        return this._isDoctor;
    }
    get flyingBlueContractNumber(): string {
        return this._flyingBlueContractNumber;
    }
    get flyingBlueContractsCount(): number {
        return this._flyingBlueContractsCount;
    }
    get otherContractsCount(): number {
        return this._otherContractsCount;
    }
    get hasMyAccountContract(): boolean {
        return this._hasMyAccountContract;
    }
    get hasFlyingBlueContract(): boolean {
        return this._hasFlyingBlueContract;
    }
    get personnalAddressesCount(): number {
        return this._personnalAddressesCount;
    }
    get professionalAddressesCount(): number {
        return this._professionalAddressesCount;
    }
    get personnalEmailsCount(): number {
        return this._personnalEmailsCount;
    }
    get professionalEmailsCount(): number {
        return this._professionalEmailsCount;
    }
    get personnalTelecomsCount(): number {
        return this._personnalTelecomsCount;
    }
    get professionalTelecomsCount(): number {
        return this._professionalTelecomsCount;
    }
    get externalIdentifiersCount(): number {
        return this._externalIdentifiersCount;
    }
    get optInCommPrefCount(): number {
        return this._optInCommPrefCount;
    }
    get optOutCommPrefCount(): number {
        return this._optOutCommPrefCount;
    }
    get optInAlertsCount(): number {
        return this._optInAlertsCount;
    }
    get optOutAlertsCount(): number {
        return this._optOutAlertsCount;
    }
    get preferencesCount(): number {
        return this._preferencesCount;
    }
    get delegatesCount(): number {
        return this._delegatesCount;
    }
    get delegatorsCount(): number {
        return this._delegatorsCount;
    }
    get UCCRRolesCount(): number {
        return this._UCCRRolesCount;
    }
    get isResctrictedType(): boolean{
        return this._isRestrictedType;
    }
    get hasAccountDataDeleted(): boolean{
        return this._hasAccountDataDeleted;
    }
    
}
