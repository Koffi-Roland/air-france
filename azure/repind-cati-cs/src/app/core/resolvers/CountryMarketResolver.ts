import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { CountryMarketService } from '../services/country-market.service';

@Injectable()
export class CountryMarketResolver implements Resolve<any> {
  constructor(private countryMarket: CountryMarketService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.countryMarket.getCountryMarkets(true).then(res => {
        return res;
      }).catch(err => {
        return this.errorRoot();
      });
  }

  private errorRoot() {
    this.router.navigate(['/']);
    return null;
  }
}
