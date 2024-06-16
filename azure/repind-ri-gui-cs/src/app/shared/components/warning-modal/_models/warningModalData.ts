export class WarningModalData {

  'title': WarningModalTitle;
  'body': WarningModalBody;

  constructor(title: WarningModalTitle, body: WarningModalBody) {
    this.title = title;
    this.body = body;
  }
}

/**
 * Object for Title of WarningModal
 * - text: is the key to translate to display in modal title
 * - icon: If present, the code of the icon to display in title
 */
export class WarningModalTitle {

  'text': string;
  'icon': string;

  constructor(text: string, icon: string = null) {
    this.text = text;
    this.icon = icon;
  }
}

/**
 * Object for Body of WarningModal
 * - text: is the key to translate to display in modal body
 * - checkboxValidationText: If present, a checkbox will be display and should be checked to enable the button
 * - buttonCancelRoot: If present, a Cancel button should be display and will root to the route indicated
 */
export class WarningModalBody {

  'text': string;
  'checkboxValidationText': string;
  'buttonCancelRoot': string;

  constructor(text: string, checkboxValidationText: string = null, buttonCancelRoot: string = null) {
    this.text = text;
    this.checkboxValidationText = checkboxValidationText;
    this.buttonCancelRoot = buttonCancelRoot;
  }
}
