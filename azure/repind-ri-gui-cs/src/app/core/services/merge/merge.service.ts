import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CommonService } from '../common.service';


@Injectable()
export class MergeService {

  result: any;
  mergePromise: any;
  firstGin: string;
  secondGin: string;
  force = false;
  blocs: any;

  constructor(private http: HttpClient, private common: CommonService) {}

  getIndividualsForMerge(gin0: string, gin1: string, force: boolean = false): any {
    this.mergePromise = this.http.get(this.common.getUrl() + 'merge/' + gin0 + '/' + gin1 + '/' + force)
        .pipe(map(json => json, err => this.common.handleError(err)))
        .toPromise()
        .then(res => {
          this.result = res;
          return res;
        })
        .catch(err => {
          this.common.handleError(err);
        throw err;
      });
      this.setForce(false);
      return this.mergePromise;
  }

  getMergeResume(gin0: string, gin1: string, blocs: any): any {
    this.mergePromise = this.http.post(this.common.getUrl() + 'merge/resume/' + gin0 + '/' + gin1, blocs)
    .pipe(map(json => json, err => this.common.handleError(err)))
    .toPromise()
    .then(res => {
      this.result = res;
      return res;
    })
    .catch(err => {
      this.common.handleError(err);
    throw err;
  });
  return this.mergePromise;
  }

  mergeIndividuals(gin0: string, gin1: string, blocs: any): any {
    this.mergePromise = this.http.post(this.common.getUrl() + 'merge/' + gin0 + '/' + gin1, blocs)
        .pipe(map(json => json, err => this.common.handleError(err)))
        .toPromise()
        .then(res => {
          this.result = res;
          return res;
        })
        .catch(err => {
          this.common.handleError(err);
        throw err;
      });
      return this.mergePromise;
  }

  getResult() {
    return this.result;
  }

  getFirstGin() {
    return this.firstGin;
  }

  getSecondGin() {
    return this.secondGin;
  }

  getForce() {
    return this.force;
  }

  setSecondGin(gin: string) {
    this.secondGin = gin;
  }

  setFirstGin(gin: string) {
    this.firstGin = gin;
  }

  setBlocsToKeep(blocs: any) {
    this.blocs = blocs;
  }

  setForce(force: boolean) {
    this.force = force;
  }

  getBlocsToKeep() {
    return this.blocs;
  }
}
