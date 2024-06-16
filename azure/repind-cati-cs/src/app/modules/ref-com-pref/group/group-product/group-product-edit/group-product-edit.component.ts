import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EventActionService } from '../../../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { GroupService } from '../../../../../core/services/group.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../../../shared/models/EventActionMessage';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-group-product-edit',
  templateUrl: './group-product-edit.component.html',
  styleUrls: ['./group-product-edit.component.scss']
})
export class GroupProductEditComponent implements OnInit {

 /**
   * Type of data display (and which action will be used)
   * Exemple: PREFERENCE
   */
  public type: string;

  /**
   * Data to display into the modal (similar as the one of tableRef, use same service)
   */
  public groupProductForm: UntypedFormGroup;

  product: any;

  action: string;
  listProducts: any;
  listGroups: any;
  listGroupsToSelect: any;
  listSelectedGroup = [];
  selectedGroup: any;

  /**
   * Construct the modal, check if in options pass some Key are available to use the datas
   * @param data Data to pass to modal
   * @param dialogRef Type of modal to open
   * @param eventActionService Service to send event
   */
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<GroupProductEditComponent>, private eventActionService: EventActionService,
    private groupService: GroupService, private fb: UntypedFormBuilder) {

      this.action = this.data.label;

      this.groupProductForm = this.fb.group({
        productId: ['', Validators.required],
        selectedGroup: [{value: '', disabled: this.product ? false : true}, Validators.required]
      });

      this.listProducts = this.groupService.products
              .sort((a, b) => a.productName > b.productName ? 1 : a.productName === b.productName ? 0 : -1);
      this.listGroups = this.groupService.groups
      .sort((a, b) => a.code > b.code ? 1 : a.code === b.code ? 0 : -1);
      this.listGroupsToSelect = JSON.parse(JSON.stringify(this.listGroups));

   }

  ngOnInit() {
  }

  onProductChange() {
    const productId = this.groupProductForm.get('productId').value;
    if (productId) {
      this.product = productId;
      this.listGroupsToSelect = JSON.parse(JSON.stringify(this.listGroups));
      this.listSelectedGroup = [];
      this.groupProductForm.get('selectedGroup').enable();
      this.groupService.getGroupProduct(productId).then(res => {
        if (res) {
          res.forEach(groupProduct => {
            let groupToAdd = this.listGroups.find(group => group.id === groupProduct.idGroup);
            groupToAdd = JSON.parse(JSON.stringify(groupToAdd));
            groupToAdd.cantBeRemoved = true;
            this.addGroup(groupToAdd);
          });
        }

      });
    }
  }

  addGroup(group?: any) {

    if (!group) {
      group = this.groupProductForm.get('selectedGroup').value;
    }

    if (group !== '') {

      this.listSelectedGroup.push(group);
      const groupToRemove = this.listGroupsToSelect.find(g => g.id === group.id);
      const index = this.listGroupsToSelect.indexOf(groupToRemove);
      if (index !== -1) {
        this.listGroupsToSelect.splice(index, 1);
        this.groupProductForm.get('selectedGroup').setValue('');
      }

    }

  }

  removeGroup(group) {
    const index = this.listSelectedGroup.indexOf(group);
    if (index !== -1) {
      this.listSelectedGroup.splice(index, 1);
      this.listGroupsToSelect.push(group);
    }
  }

  /**
   * Send the Event 'actionSentToBeApply' to execute the action!
   */
  submit() {
    const groupProduct = {
      productId: this.product,
      groupsId: this.listSelectedGroup.map(group => group.id)
    };

    this.eventActionService.actionSentToBeApply(new EventActionMessage(EventActionMessageEnum.CREATE, groupProduct));
  }
}
