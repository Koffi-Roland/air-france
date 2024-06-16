import { ToggleSliderOption } from "../../../../../models/contents/toggle-slider-option";
import { FieldConfig } from "../../../../../models/forms/field-config";
import { Alert } from "../../../../../models/resources/alert";

export class AlertUpdateFormConfig {

    private static alertOptions = [new ToggleSliderOption('Y', 'N')];

    public static config(alert: Alert): FieldConfig[] {
        const titleDetail = alert.type + '-ALERT-TYPE';
        const detail = new Map();

        alert.alertdata.forEach(data => { detail.set(data.key, data.value) });

        return [
            {
                type: 'toggleslider',
                name: 'alert',
                label: 'OPT-IN',
                value: alert.optin,
                options: this.alertOptions,
                details: new Map().set(titleDetail, detail)
            }
        ]
    }
}