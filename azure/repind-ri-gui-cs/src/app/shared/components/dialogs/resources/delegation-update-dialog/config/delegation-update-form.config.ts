import { OptionItem } from "../../../../../models/contents/option-item";
import { Delegation } from "../../../../../models/resources/delegation/delegation";

export class DelegationUpdateFormConfig {

    private static options: Array<OptionItem> = [
        new OptionItem('R', 'R-DELEGATION-STATUS', ''),
        new OptionItem('A', 'A-DELEGATION-STATUS', ''),
        new OptionItem('I', 'I-DELEGATION-STATUS', ''),
        new OptionItem('D', 'D-DELEGATION-STATUS', ''),
        new OptionItem('C', 'C-DELEGATION-STATUS', ''),
    ];

    public static config(delegation: Delegation) {
        return [
            {
                type: 'select',
                name: 'status',
                label: 'STATUS',
                value: delegation.status,
                options: DelegationUpdateFormConfig.options,
            }
        ]
    }
}