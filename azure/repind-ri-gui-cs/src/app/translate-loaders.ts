import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
export function HttpLoaderFactory(http: HttpClient) {
// Local
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
// From REST call
//  return new TranslateHttpLoader(http, 'https://myApp.airfrance.fr/api-i18n/rest/resources/translate/', '');
}
