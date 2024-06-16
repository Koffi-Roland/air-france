import { ToggleSliderOption } from "../../../../../models/contents/toggle-slider-option";
import { FieldConfig } from "../../../../../models/forms/field-config";
import { ReferenceDataType } from "../../../../../models/references/ReferenceDataType.enum";
import { Consent } from "../../../../../models/resources/consent";

export class ConsentUpdateConfig {

    private static consentOptions = [new ToggleSliderOption('Y', 'N')];

    public static config(consent: Consent): FieldConfig[] {

        const titleDetail = ReferenceDataType.CONSENT_DATA_TYPE + '-' + consent.consentDataType;
        const detail = new Map();
        detail.set('Id', consent.id);
        detail.set('Date', consent.consentData.dateConsent);
        detail.set('Type', ReferenceDataType.CONSENT_TYPE + '-' + consent.type);

        return [
            {
                type: 'toggleslider',
                name: 'consent',
                label: 'OPT-IN',
                value: consent.consentData.isConsent,
                options: ConsentUpdateConfig.consentOptions,
                details: new Map().set(titleDetail, detail)
            }
        ]
    }


}