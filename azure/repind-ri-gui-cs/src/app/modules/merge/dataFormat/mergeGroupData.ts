import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

export class MergeGroupData {
    blocTitle: string;
    title: string;
    subtitles: Array<string>;
    options: MergeGroupDataOption;

    constructor(blocTitle: string, title: string, subtitles: Array<string>, options: MergeGroupDataOption) {
      this.blocTitle = blocTitle;
      this.title = title;
      this.subtitles = subtitles;
      this.options = options;
    }

    public static copyMergeGroupData(mergeGroupData: MergeGroupData): MergeGroupData {
     return JSON.parse(JSON.stringify(mergeGroupData));
    }
}
/**
 * Internal class of MergeGroupData for the option
 * type -> radio, checkbox or not at all
 */
export class MergeGroupDataOption {
    name: string;
    functionSort: Function;
    icon: string;
    identifiant: string;
    translator: Array<Translator>;
    type: string;
    template: Array<Template>;

    constructor(functionSort: Function, translator: Array<Translator>,
                  name: string, icon: string, identifiant: string, type: string = null, template: Array<Template> = null) {
      this.functionSort = functionSort;
      this.translator = translator;
      this.name = name;
      this.icon = icon;
      this.identifiant = identifiant;
      this.type = type;
      this.template = template;
    }

    public addTemplate(template: Template) {
      if (this.template == null) {
        this. template = new Array<Template>(template);
      } else {
        this.template.push(template);
      }
    }

    public hasTemplate(key: string) {
      if (this.template == null) { return false; }
      for (const template of this.template) {
        if (template.key === key) {
          return true;
        }
      }
      return false;
    }
}


/**
 * Bloc selected
 * value of selected bloc
 */
export class MergeGroupDataSelected {
  type: string;
  identifiants: Array<string>;

  constructor(type: string, identifiants: Array<string>) {
    this.type = type;
    this.identifiants = identifiants.filter(identifiant => identifiant.length > 0);
  }
}
