import { AutocompleteComponent } from '../../components/forms/autocomplete/autocomplete.component';
import { UntypedFormGroup } from '@angular/forms';
import { FieldConfig } from '../../models/forms/field-config';
import { CheckboxComponent } from '../../components/forms/checkbox/checkbox.component';
import { DateComponent } from '../../components/forms/date/date.component';
import { ButtonComponent } from '../../components/forms/button/button.component';
import { InputComponent } from '../../components/forms/input/input.component';
import { Directive, Input, ComponentFactoryResolver, ViewContainerRef, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { SelectComponent } from '../../components/forms/select/select.component';
import { RadiobuttonComponent } from '../../components/forms/radiobutton/radiobutton.component';
import { TogglesliderComponent } from '../../components/forms/toggleslider/toggleslider.component';
import { TableMultiselectComponent } from '../../components/forms/table-multiselect/table-multiselect.component';

const componentMapper = {
  input: InputComponent,
  button: ButtonComponent,
  select: SelectComponent,
  date: DateComponent,
  radiobutton: RadiobuttonComponent,
  checkbox: CheckboxComponent,
  autocomplete: AutocompleteComponent,
  toggleslider: TogglesliderComponent,
  tablemultiselect: TableMultiselectComponent
};

@Directive({
  selector: '[appDynamicField]'
})
export class DynamicFieldDirective implements OnInit {

  @Input() field: FieldConfig;
  @Input() group: UntypedFormGroup;

  private componentRef: any;

  constructor(private resolver: ComponentFactoryResolver, private container: ViewContainerRef) { }

  ngOnInit() {

    const factory = this.resolver.resolveComponentFactory(componentMapper[this.field.type]);
    this.componentRef = this.container.createComponent(factory);
    this.componentRef.instance.field = this.field;
    this.componentRef.instance.group = this.group;

    // Getting the component's HTML
    const element: HTMLElement = <HTMLElement>this.componentRef.location.nativeElement;

    // Add style
    element.style.width = (this.field.width) ? this.field.width : '100%';
  }

}
