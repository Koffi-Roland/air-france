import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UntypedFormControl, UntypedFormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { MediaService } from '../../../../core/services/media.service';
import { ValidatorsCustom } from '../../../../shared/widgets/validators/validators-custom.component';
import { UserConnectedService } from '../../../../core/services/user-connected.service';
import * as moment from 'moment';
import { TranslateService } from '@ngx-translate/core';
import { MyErrorStateMatcher } from '../../../../shared/widgets/my-error-state-matcher/my-error-state-matcher.component';


@Component({
  selector: 'app-media-edit',
  templateUrl: './media-edit.component.html',
  styleUrls: ['./media-edit.component.css']
})
export class MediaEditComponent implements OnInit {

  title: string;
  mediaForm: UntypedFormGroup;
  media: any;
  matcher = new MyErrorStateMatcher();
  checkList: any;
  user: any;

  constructor(private snackBar: MatSnackBar,
    private translate: TranslateService,
    public dialogRef: MatDialogRef<MediaEditComponent>,
    private service: MediaService,
    private validatorsCustom: ValidatorsCustom,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userConnectedService: UserConnectedService) { }

  ngOnInit() {

    this.media = {};

    this.mediaForm = new UntypedFormGroup({
      codeMedia: new UntypedFormControl({ value: '', disabled: this.data.code }, [Validators.required, Validators.maxLength(1), ValidatorsCustom.isUnique()]),
      libelleMediaEN: new UntypedFormControl('', [Validators.required, Validators.maxLength(25)]),
      libelleMedia: new UntypedFormControl('', Validators.maxLength(25))
    });

    if (this.data && this.data.code) {
      this.service.getMedia(this.data.code).then(res => {
        this.media = res;
        this.mediaForm.patchValue(res);
      });
    }

    this.userConnectedService.getUser().then((user) => {
      this.user = user;
    });

    this.updateTitle();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /**
   * Updates the title of the page depending on if we update or create a resource.
   */
  updateTitle() {
    if (this.data !== undefined && this.data.code !== undefined) {
      this.title = "UPDATE_TYPE";
    } else {
      this.title = "CREATE_NEW_TYPE";
    }
  }

  submit() {
    var values = this.mediaForm.value;

    if (!this.data.code) {
      this.media.codeMedia = values.codeMedia;
    }
    this.media.libelleMediaEN = values.libelleMediaEN;
    this.media.libelleMedia = values.libelleMedia;

    this.media.dateCreation = moment().utc().format("YYYY-MM-DD HH:mm:ss");
    this.media.dateModification = moment().utc().format("YYYY-MM-DD HH:mm:ss");

    this.media.signatureCreation = this.user.username;
    this.media.signatureModification = this.user.username;

    this.media.siteCreation = "QVI";
    this.media.siteModification = "QVI";

    if (!this.media.libelleMedia || this.media.libelleMedia === "") {
      this.media.libelleMedia = this.media.libelleMediaEN;
    }

    // If it is a create
    if (!this.data.code) {
      this.service.postMedia(this.media).then(res => {
        this.dialogRef.close(true);
        this.translate.get("MEDIA").subscribe((type: string) => {
          let parameters = { type: type };
          this.translate.get("EDIT_CREATED", parameters).subscribe((res: string) => {
            this.snackBar.open(res, '', { duration: 3000 });
          });
        });
      });
    } else { // If it is an update
      this.service.updateMedia(this.data.code, this.media).then(res => {
        this.dialogRef.close(true);
        this.translate.get("MEDIA").subscribe((type: string) => {
          let parameters = { type: type };
          this.translate.get("EDIT_UPDATED", parameters).subscribe((res: string) => {
            this.snackBar.open(res, '', { duration: 3000 });
          });
        });
      });
    }
  }

}
