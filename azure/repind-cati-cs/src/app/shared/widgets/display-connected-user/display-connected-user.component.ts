import {Component, OnInit} from '@angular/core';
import {UserConnectedService} from '../../../core/services/user-connected.service';
import {User} from "@airfranceklm/permission";

/**
 * DisplayConnectedUserComponent
 * This component is to be initialized as a component by calling the factory method.
 * The component for display connected user
 */
@Component({
    selector: 'display-connected-user',
    templateUrl: './display-connected-user.component.html'
})
export class DisplayConnectedUserComponent implements OnInit {

    user: Pick<User, 'firstname' |'lastname'>;

    constructor(private userConnectedService: UserConnectedService) {

    }

    /**
     * Called at link stage of the directive
     */
    ngOnInit(): void {
        this.userConnectedService.getUserFirstNameAndLastName().subscribe((result: Pick<User, 'firstname' |'lastname'>) => {
            this.user = result;
        });
    }

}
