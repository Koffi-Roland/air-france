import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
import { FieldConfig } from 'src/app/shared/models/forms/field-config';
import { SelectTableComponent } from '../../select-table/select-table.component';

@Component({
  selector: 'app-table-multiselect',
  templateUrl: './table-multiselect.component.html',
  styleUrls: ['./table-multiselect.component.scss']
})
export class TableMultiselectComponent implements OnInit, AfterViewInit, OnDestroy {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  @ViewChild('selectTable') select: SelectTableComponent;
  public selectionChangeSubscription: Subscription;

  constructor() { }

  ngOnInit(): void { }

  ngAfterViewInit(): void {
    const controlForm = this.group.get(this.field.name);

    // listen once then unsubscribe automaticcally
    controlForm.valueChanges.pipe(first()).subscribe((e) => {
      this.select.selectedIds = e;
      this.select.checkByDefault();
    });

    this.selectionChangeSubscription = this.select.selection.changed.subscribe(() => {
      controlForm.patchValue(this.select.selection.selected.map(e => e[this.field.tableMultiselectIdName]));
    });
  }

  ngOnDestroy(): void {
    this.selectionChangeSubscription.unsubscribe();
  }
}
