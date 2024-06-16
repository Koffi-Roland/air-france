import { MatDateFormats } from '@angular/material/core';
export const LOCALIZED_DATE_FORMATS: MatDateFormats = {
  parse: {
    dateInput: ['l', 'LL'],
  },
  display: {
    dateInput: 'L',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};
