import { MergeGroupData, MergeGroupDataOption } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

export class AccountDataConfig {

  private static  subtitles: Array<string> = ['emailIdentifier'];

  private static  translator: Array<Translator> = [];

  /*private static template: Array<Template> = [
    new Template('signature', ['collapse'])];

  private static options: MergeGroupDataOption =
    new MergeGroupDataOption(AccountDataConfig.functionSort, AccountDataConfig.translator, 'account', 'fingerprint',
     'identifiant', null, AccountDataConfig.template);*/

     private static options: MergeGroupDataOption =
     new MergeGroupDataOption(AccountDataConfig.functionSort, AccountDataConfig.translator, 'account', 'fingerprint',
      'identifiant');



  public static config: MergeGroupData =
    new MergeGroupData('ACCOUNT', 'fbIdentifier', AccountDataConfig.subtitles, AccountDataConfig.options);

  private static functionSort(object1: any, object2: any): boolean {
    return true;
  }

}
