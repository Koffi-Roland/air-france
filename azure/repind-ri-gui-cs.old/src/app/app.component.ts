
import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DateAdapter } from '@angular/material/core';
import { RefLoaderService } from './core/services/references/ref-loader.service';
import { CommonService } from './core/services/common.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private translate: TranslateService,private commonService: CommonService, private dateAdapter: DateAdapter<Date>, private refLoaderService: RefLoaderService) {
    /* init i18n */
    translate.setDefaultLang('fr');
    translate.use('en');
    this.refLoaderService.loadAllReferences();

  }

  setLanguage(lang: string): void {
    this.translate.use(lang);
    this.dateAdapter.setLocale(lang);
  }

  /**
   * Logs out a user from Habile.
   */
  logout(): void {
    window.location.href = this.commonService.getUrl() + 'ldap/logout.do';
  }
}
