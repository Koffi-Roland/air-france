import { ComponentType } from '@angular/cdk/portal';
import { TemplateRef } from '@angular/core';
import { FieldConfig } from '../../models/forms/field-config';
import { StepperFormConfig } from '../../models/forms/stepper-form/stepper-form-config';
import { Validator } from '../../models/forms/validator';
import { TableLinkedOptionConfig } from '../../models/TableLinkedOptionConfig';

/**
 * Class of Option for section Action of ArrayDisplayRefTable
 */
export class ArrayDisplayRefTableActionOption {
  /**
   * Label to display into popover
   */
  private _label: string;

  /**
   * The modal to open when clicked on icon
   */
  private _modal: (ComponentType<any> | TemplateRef<any>);

  /**
   * Icon to display
   */
  private _icon: string;


  /**
   * Other Data_Option to pass to the action section
   * For example:
   * key: type
   * value: PREFERENCE
   *
   * use to display the Type of data into modal
   *
   * Be carefull!!! The data are not the value to display!
   * There are complementary and not provided by the table and not available into the Object.
   */
  private _configs: Array<ArrayDisplayRefTableActionOptionConfig>;

  constructor(label: string, actionFunction: (ComponentType<any> | TemplateRef<any>) = null,
    icon: string, configs: Array<ArrayDisplayRefTableActionOptionConfig> = new Array<ArrayDisplayRefTableActionOptionConfig>()) {
    this._label = label;
    this._modal = actionFunction;
    this._icon = icon;
    this._configs = configs;
  }

  get label(): string {
    return this._label;
  }

  get modal(): (ComponentType<any> | TemplateRef<any>) {
    return this._modal;
  }

  get icon(): string {
    return this._icon;
  }

  get configs(): Array<ArrayDisplayRefTableActionOptionConfig> {
    return this._configs;
  }

  set setLabel(label: string) {
    this._label = label;
  }

  set setModal(modal: (ComponentType<any> | TemplateRef<any>)) {
    this._modal = modal;
  }

  set setIcon(icon: string) {
    this._icon = icon;
  }

  set setConfigs(configs: Array<ArrayDisplayRefTableActionOptionConfig>) {
    this._configs = configs;
  }

  public addConfig(config: ArrayDisplayRefTableActionOptionConfig) {
    this._configs.push(config);
  }

}

/**
 * Class generic (key/value) to pass data throught Options
 */
export class ArrayDisplayRefTableActionOptionConfig {

  /**
   * Key associated to a value.
   * Only theses Keys are used:
   * - type -> type of data display/used in modal
   */
  private _key: string;
  /**
   * Value can be a string, a function or a list of ArrayDisplayRefTableActionOptionConfig.
   * That allow to have a recursive and infinite type of option.
   */
  private _value: string | Function | StepperFormConfig | TableLinkedOptionConfig | Array<ArrayDisplayRefTableActionOptionConfig | FieldConfig | Validator>;

  constructor(key: string, value: string | Function | StepperFormConfig | TableLinkedOptionConfig | Array<ArrayDisplayRefTableActionOptionConfig | FieldConfig | Validator>) {
    this._key = key;
    this._value = value;
  }

  get key(): string {
    return this._key;
  }

  get value(): string | Function | StepperFormConfig | TableLinkedOptionConfig | Array<ArrayDisplayRefTableActionOptionConfig | FieldConfig | Validator> {
    return this._value;
  }
}
