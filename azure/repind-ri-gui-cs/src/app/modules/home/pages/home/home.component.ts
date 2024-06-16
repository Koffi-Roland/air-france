import { Router } from '@angular/router';
import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 's') {
      this.router.navigate(['/individuals/search/gin']);
    }
    if (event.key === 'm') {
      this.router.navigate(['/individuals/merge']);
    }
  }

}
