import { PatternMergeAccordionData } from '../dataFormat/patternMergeAccordionData';

/**
 * Class of data displayed into merge-accordion-date component
 * title -> title of the accordion
 * subtitles -> list of subtitles of the accordion
 * icon -> icon to display into header
 * id -> id in case of checkbox or radio button, can be null
 * idName -> name of the id field
 * content -> data to display in content section of accordion
 */
export class MergeAccordionData {
  title: string;
  subtitles: MergeAccordionDataArray;
  icon: string;
  idName: string;
  id: string;
  options: MergeAccordionDataOption;
  content: MergeAccordionDataArray;

  constructor(pattern: PatternMergeAccordionData, data: Object, options: MergeAccordionDataOption) {
    this.title = data[pattern.title];
    this.icon = pattern.icon;
    this.id = data[pattern.identifiant];
    this.options = options;
    // tslint:disable-next-line: no-use-before-declare
    this.content = new MergeAccordionDataArray();
    // tslint:disable-next-line: no-use-before-declare
    this.subtitles = new MergeAccordionDataArray();
  }

  addContent(key: string, value: any, isTranslable = false, isSubtitle = false, template: Array<string> = null) {
    // tslint:disable-next-line: no-use-before-declare
    this.content.addElement(new MergeAccordionDataContent(key, value, isTranslable, template));
    if (isSubtitle) {
      // tslint:disable-next-line: no-use-before-declare
      this.subtitles.addElement(new MergeAccordionDataContent(key, value, isTranslable, template));
    }
  }

  addArray(value: MergeAccordionDataArray, isSubtitle = false) {
    this.content.addElement(value);
    if (isSubtitle) {
      this.subtitles.addElement(value);
    }
  }

}

/**
 * Internal class of MergeAccordionData for the content
 */
export class MergeAccordionDataContent {
  'key': string;
  'value': any;
  'isTranslable': boolean;
  'template': Array<string>;

  constructor(key: string, value: any, isTranslable = false, template: Array<string> = null) {
    this.key = key;
    this.value = value;
    this.isTranslable = isTranslable;
    this.template = template;
  }
}

/**
 * Internal class of MergeAccordionData for the option
 * selected -> data is selected or not
 * type -> radio, checkbox or not at all
 */
  export class MergeAccordionDataOption {
    'selected': boolean;
    'type': string;

    constructor(selected: boolean, type: string = null) {
      this.selected = selected;
      this.type = type;
    }

  }

/**
 * Internal class of MergeAccordionData for the content when content is an array
 */
export class MergeAccordionDataArray {
  'key': string;
  'values': Array<MergeAccordionDataArray|MergeAccordionDataContent>;
  'template': Array<string>;

  constructor() {
    this.values = new Array<MergeAccordionDataArray|MergeAccordionDataContent>();
  }
  setKey(key: string) {
    this.key = key;
  }
  setTemplate(template: Array<string>) {
    this.template = template;
  }
  addElement(value: MergeAccordionDataArray|MergeAccordionDataContent) {
    this.values.push(value);
  }

}
