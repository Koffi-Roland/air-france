import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Email } from '../../../shared/models/resources/email';
import { EmailSerializer } from '../../../shared/models/serializer/email-serializer';

@Injectable({
  providedIn: 'root'
})
export class EmailService extends ResourceService<Email> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/mails`,
      `individual/:gin/mail`,
      `individual/:gin/mail/:id`,
      `individual/:gin/mail`,
      new EmailSerializer(),
      injector
    );
   }
}
