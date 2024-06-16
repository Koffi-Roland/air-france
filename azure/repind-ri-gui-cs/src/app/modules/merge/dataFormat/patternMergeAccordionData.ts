import { MergeAccordionData, MergeAccordionDataOption, MergeAccordionDataContent, MergeAccordionDataArray } from './mergeAccordionData';
import { MergeGroupData } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

/**
 * Class to help to transform specific data to Generic to be display in merge-accordion-data component
 * title -> the key of the data to display in title
 * subtitles -> list of key from data to display in subtitle
 * icon -> the code of icon to display
 * identifiant -> the id to use in case of checkbox or radio button, can be null
 * translator -> list of value to translate
 * name -> name of property for the bloc
 * type -> type of balise (radio, checkbox, null)
 */
export class PatternMergeAccordionData {
  title: string;
  subtitles: Array<string>;
  icon: string;
  identifiant: string;
  translator: Array<Translator>;
  name: string;
  type: string;
  template: Array<Template>;

  constructor(config: MergeGroupData) {
    this.title = config.title;
    this.subtitles = config.subtitles;
    this.icon = config.options.icon;
    this.identifiant = config.options.identifiant;
    this.translator = config.options.translator;
    this.name = config.options.name;
    this.type = config.options.type;
    this.template = config.options.template;
  }

  /** Transform a data with given pattern to data readable by MergeTransformAccordionData :
   * @param data -> data to transform
   * @param pattern -> values to get from data and to inject in 'generic' readable data by MergeTransformAccordionData.
   * @param selected -> value for know if the bloc is selected by default or not.
   *Example:
   *data: {'phoneNumber': '123456789', 'countryCode': '33', 'type': 'D', 'terminal': 'M', 'identifiant': '12345667'}
   *pattern: {'title': 'phoneNumber', 'subtitles': ['type', 'terminal'], 'icon': 'phone', 'id': 'identifiant'}
   *return:
   *{'title': '123456789', 'subtitles': 'D M (translated)', 'icon': 'phone', 'id': '12345667',
   *'content' [
   * {'key': 'phoneNumber', 'value: '123456789'}, {'key': 'countryCode', 'value: '33'},
   * {'key': 'type', 'value: 'D'}, {'key': 'terminal', 'value: 'M'}}
   *]}
   * Identifiant is not displayed on content, just keep for know bloc selected
   */
  public transformDataToMergeAccordionData(data: Object, pattern: PatternMergeAccordionData, selected: boolean) {
    const result = new MergeAccordionData(pattern, data, new MergeAccordionDataOption(selected, this.type));

    this.dataToGenericFormat(data, pattern, result);

    return result;
  }

  public dataToGenericFormat(data: Object, pattern: PatternMergeAccordionData, result: MergeAccordionData, father: string = '') {

    const dataArray: MergeAccordionDataArray = new MergeAccordionDataArray();

    for (const property in data) {
      if (data.hasOwnProperty(property)) {
        if (property !== pattern.identifiant) {
          let value = data[property];
          let isTranslable = false;
          let isSubtitle = false;
          let templateOptions = null;
          if(pattern.subtitles) {
            isSubtitle = pattern.subtitles.filter(x => x === property).length > 0;
          }
          if (pattern.template) {
            const template = pattern.template.filter(x => x.key === property)[0];
            if (template) {
              templateOptions = template.options;
            }
          }

            if (value instanceof Array) {
              const listAccordionDataArray: MergeAccordionDataArray = new MergeAccordionDataArray();
              listAccordionDataArray.setKey(property);
              listAccordionDataArray.setTemplate(templateOptions);
              for (let index = 0; index < value.length; index++) {
                const element = value[index];
                const content = this.dataToGenericFormat(element, pattern, result, property);
                listAccordionDataArray.addElement(content);
              }
              result.addArray(listAccordionDataArray, isSubtitle);
            } else if(value instanceof Object) {
              // If not an array, it's an object
                 this.dataToGenericFormat(value, pattern, result);
            } else {
            // We add traduction code if available
            if (isTranslable = (this.translator && this.translator.filter(x => x.key === property).length > 0)) {
              value = this.translator.filter(x => x.key === property)[0].code + value;
            }
            if (father) {
              dataArray.addElement(new MergeAccordionDataContent(property, value, isTranslable, templateOptions));
            } else {
              result.addContent(property, value, isTranslable, isSubtitle, templateOptions);
            }
          }
        }
      }
    }
    return dataArray;
  }
}

