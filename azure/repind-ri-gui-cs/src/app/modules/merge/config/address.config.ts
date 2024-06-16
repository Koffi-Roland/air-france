import { MergeGroupData, MergeGroupDataOption } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';

export class AddressConfig {

  private static subtitles: Array<string> = ['type', 'usages', 'status'];

  private static translator: Array<Translator> = [
    new Translator('type', 'ADDRESS-CODE-'),
    new Translator('status', 'ADDRESS-STATUS-'),
    new Translator('role1', 'USAGE-ROLE-TYPE-')];

  private static template: Array<Template> = [
    new Template('usages', ['listChips'])/*, new Template('signature', ['collapse'])*/];

  private static options: MergeGroupDataOption =
    new MergeGroupDataOption(AddressConfig.functionSort, AddressConfig.translator,
      'address', 'place', 'identifiant', 'radio', AddressConfig.template);

  public static config: MergeGroupData = new MergeGroupData('ADDRESSES', 'numberAndStreet', AddressConfig.subtitles, AddressConfig.options);

  private static  functionSort(object1: any, object2: any): boolean {
    return (object1.type === object2.type);
  }

}
