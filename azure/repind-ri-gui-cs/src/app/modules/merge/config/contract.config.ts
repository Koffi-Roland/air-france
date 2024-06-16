import { MergeGroupData, MergeGroupDataOption } from '../dataFormat/mergeGroupData';
import { Translator } from '../dataFormat/translator';
import { Template } from '../dataFormat/template';
import { CommonService } from '../../../core/services/common.service';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';

export class ContractConfig {

  private static  subtitles: Array<string> = ['productType', 'status'];

  private static  translator: Array<Translator> = [
    new Translator('status', CommonService.getTransformEnumTypeStat(ReferenceDataType.STATES_ROLE_CONTRACT))];

    /*private static template: Array<Template> = [
      new Template('signature', ['collapse'])];

  private static options: MergeGroupDataOption =
    new MergeGroupDataOption(ContractConfig.functionSort, ContractConfig.translator, 'contract', 'account_balance_wallet', 'identifiant',
    null, ContractConfig.template);*/

    private static options: MergeGroupDataOption =
    new MergeGroupDataOption(ContractConfig.functionSort, ContractConfig.translator, 'contract', 'account_balance_wallet', 'identifiant');

  public static config: MergeGroupData =
    new MergeGroupData('CONTRACTS', 'contractNumber', ContractConfig.subtitles, ContractConfig.options);

  private static  functionSort(object1: any, object2: any): boolean {
    return (object1.contractType === object2.contractType && object1.status === object2.status);
  }

}
