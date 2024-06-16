import {
  MergeGroupData,
  MergeGroupDataOption
} from '../dataFormat/mergeGroupData';
import {
  Translator
} from '../dataFormat/translator';

import { Template } from '../dataFormat/template';

export class EmailConfig {

  private static subtitles: Array < string > = ['type', 'status'];

  private static translator: Array < Translator > = [
    new Translator('type', 'EMAIL-CODE-'),
    new Translator('status', 'EMAIL-STATUS-')
  ];

  /*private static template: Array<Template> = [
    new Template('signature', ['collapse'])];

    private static options: MergeGroupDataOption =
    new MergeGroupDataOption(EmailConfig.functionSort, EmailConfig.translator, 'email', 'email', 'identifiant', 'radio',
    EmailConfig.template);*/

    private static options: MergeGroupDataOption =
    new MergeGroupDataOption(EmailConfig.functionSort, EmailConfig.translator, 'email', 'email', 'identifiant', 'radio',
    null);

  public static config: MergeGroupData = new MergeGroupData('EMAILS', 'email', EmailConfig.subtitles, EmailConfig.options);

  private static functionSort(object1: any, object2: any): boolean {
    return (object1.type === object2.type);
  }

}
