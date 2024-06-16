import { MergeGroupData, MergeGroupDataOption } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

export class TelecomConfig {

  private static  subtitles: Array<string> = ['type', 'terminal', 'status'];

  private static  translator: Array<Translator> = [
    new Translator('type', 'PHONE-CODE-'),
    new Translator('status', 'PHONE-STATUS-'),
    new Translator('terminal', 'TERMINAL-TYPE-')];

    /*private static template: Array<Template> = [
      new Template('signature', ['collapse'])];

  private static options: MergeGroupDataOption =
    new MergeGroupDataOption(TelecomConfig.functionSort, TelecomConfig.translator, 'telecom', 'phone', 'identifiant', 'radio'
  , TelecomConfig.template);*/

    private static options: MergeGroupDataOption =
    new MergeGroupDataOption(TelecomConfig.functionSort, TelecomConfig.translator, 'telecom', 'phone', 'identifiant', 'radio'
  , null);

  public static config: MergeGroupData = new MergeGroupData('TELECOMS', 'phoneNumber', TelecomConfig.subtitles, TelecomConfig.options);

  private static  functionSort(object1: any, object2: any): boolean {
    return (object1.type === object2.type && object1.terminal === object2.terminal);
  }

}
