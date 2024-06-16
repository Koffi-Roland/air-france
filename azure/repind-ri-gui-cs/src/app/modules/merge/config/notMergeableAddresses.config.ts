import { MergeGroupData, MergeGroupDataOption } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

export class NotMergeableAddressesConfig {

  private static subtitles: Array<string> = ['type', 'usages', 'status'];

  private static translator: Array<Translator> = [
    new Translator('type', 'ADDRESS-CODE-'),
    new Translator('status', 'ADDRESS-STATUS-')];

  private static template: Array<Template> = [
    new Template('usages', ['listChips'])/*, new Template('signature', ['collapse'])*/];

  private static options: MergeGroupDataOption =
    new MergeGroupDataOption(NotMergeableAddressesConfig.functionSort, NotMergeableAddressesConfig.translator,
      'address', 'place', 'identifiant', null, NotMergeableAddressesConfig.template);

  public static config: MergeGroupData = new MergeGroupData('NOT-MERGEABLE-ADDRESSES', 'numberAndStreet',
  NotMergeableAddressesConfig.subtitles, NotMergeableAddressesConfig.options);

  private static  functionSort(object1: any, object2: any): boolean {
    return (object1.type === object2.type);
  }

}
