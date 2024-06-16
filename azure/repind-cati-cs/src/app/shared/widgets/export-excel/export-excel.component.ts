import { Component, OnInit, Inject, Input } from '@angular/core';
import * as moment from 'moment';
/**
 * ExportExcelComponent
 * This component is to be initialized as a component by calling the factory method.
 * The component for export an array to excel
 */

declare global {
  interface Navigator {
    msSaveOrOpenBlob?: (blob: Blob, filename: string) => boolean;
    msSaveBlob?: (blob: Blob, name: string) => boolean;
  }
}

@Component({
  selector: 'export-excel',
  templateUrl: './export-excel.component.html'
})
export class ExportExcelComponent implements OnInit  {

  textToDisplay: any;

  @Input("exportList") exportList: any;

  constructor() {

    // this.scope = {
    //   ngModel: "=ngModel",
    //   exportList: "=exportList"
    // };
  }

  ngOnInit(): void {
    this.textToDisplay = {
      refComprefId: "Identifier",
      refComPrefDgtId: "Identifier",
      id: "Identifier",
      description: "Description",
      mandatoryOptin: "Mandatory Optin",
      market: "Market",
      defaultLanguage: "Default Language",
      fieldA: "A",
      fieldN: "N",
      fieldT: "T",
      comGroupeType: "Group Type",
      groupType: "Group Type",
      comType: "Type",
      type: "Type",
      domain: "Domain",
      media: "Media",
      code: "Code",
      libelle: "French name",
      libelleEN: "Name",
      name: "Name",
      question: "French question",
      questionEN: "Question",
      combination: "Combination identifier",
      signatureCreation: "Signature creation",
      signatureModification: "Signature modification",
      dateCreation: "Date creation",
      dateModification: "Date modification",
      siteCreation: "Site creation",
      siteModification: "Site modification",
      groupInfoId: "Group Id",
      mandatoryOption: "Mandatory Optin",
      defaultOption: "Default Optin",
      nbCompref: "Number of Comprefs linked",
      nbProduct: "Number of Products linked",
      productId: "Product Id",
      productType: "Product Type",
      subProductType: "SubProduct Type",
      productName: "Product Name",
      links: "Links"
    };
  }

  /**
   * For download csv file
   */
  exportExcel = function() {
    this.startExport(this.exportList);
  };


  /**
   * Start export of the excel
   */
  startExport(exportList) {

    // Polyfill for IE 11
    if (window.navigator.msSaveOrOpenBlob) {
      var blob = new Blob([decodeURIComponent(encodeURI(this.getCSV(exportList)))], {
        type: "text/csv;charset=utf-8;"
      });
      navigator.msSaveBlob(blob, "ExportCATI.csv");
    }
    // For others browsers
    else {
      var saving = document.createElement("a");
      saving.href = "data:attachment/csv," + encodeURIComponent(this.getCSV(exportList));
      saving.download = "ExportCATI.csv";
      saving.click();
    }

  };

  /**
   * Construct and return a file (csv) for download
   */
  getCSV(arr) {
    var ret = [];
    var headColumns = Object.keys(arr[0]);
    var headLine = [];
    // Construct header and remove $$hashKey column
    for (var j = 0; j < headColumns.length; j++) {
      if (headColumns[j] !== "$$hashKey") {
        headLine.push(this.getHeader(headColumns[j]));
      }
    }
    ret.push(headLine.join(";"));
    // Construct content and remove column $$hashKey
    for (var i = 0, len = arr.length; i < len; i++) {
      var line = [];
      for (var key in arr[i]) {
        // check if key is a property
        if (arr[i].hasOwnProperty(key)) {
          if (key !== "$$hashKey") {
            var value = arr[i][key];
            if (value !== null && typeof value === "object") {
              if (key === "combination") {
                line.push(value);
              } else {
                line.push(value[Object.keys(value)[0]]);
              }
            } else {
              // If value = * => display nothing
              if (value !== "*") {
                if (key === "dateCreation" || key === "dateModification") {
                  line.push(moment(value).format("YYYY-MM-DD | HH:mm:ss"));
                } else {
                  line.push(value);
                }
              } else {
                // If value = * but key is market, display *
                if (key === "market") {
                  line.push(value);
                } else {
                  line.push("");
                }
              }
            }
          }
        }
      }
      ret.push(line.join(";"));
    }
    return ret.join("\n");
  };

  /**
   * Return more convenient header to display => translate property name
   */
  getHeader(header) {

    var text = "";

    // Display for all field defaultLanguage
    if (header.indexOf("defaultLanguage") !== -1) {
      text = this.textToDisplay.defaultLanguage;
      header = header.replace("defaultLanguage", "");
      text = text + " " + header;
    } else if (header.indexOf("code") !== -1 && header.indexOf("Pays") === -1) {
      text = this.textToDisplay.code;
    } else if (header.indexOf("libelle") !== -1) {
      if (header.indexOf("EN") !== -1) {
        text = this.textToDisplay.libelleEN;
      } else {
        text = this.textToDisplay.libelle;
      }
    } else {
      text = this.textToDisplay[header];
    }

    return text;
  };

}
