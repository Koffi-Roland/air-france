import { TestBed, ComponentFixture, getTestBed, waitForAsync } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { TranslateService, TranslateModule } from '@ngx-translate/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SharedModule } from './shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CapitalizePipe } from './shared/pipes/capitalize.pipe';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Injector } from '@angular/core';
import { HomeComponent } from './modules/home/pages/home/home.component';
describe('AppComponent', () => {

  const appRoutes: Routes = [
    {
      path: 'home',
      component: HomeComponent
    },
    { path: '',
      redirectTo: '/home',
      pathMatch: 'full'
    },
    {
      path: '**',
      component: PageNotFoundComponent
    }
  ];

  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let injector:  Injector;
  let translate: TranslateService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot(),
        SharedModule,
        RouterModule.forRoot(appRoutes, {}),
        FormsModule,
        BrowserModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ],
      declarations: [
        AppComponent,
        HomeComponent,
        PageNotFoundComponent,
        CapitalizePipe,
      ],
      providers: [
        TranslateService,
        { provide: APP_BASE_HREF, useValue : '/sic2' }
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.debugElement.componentInstance;
    injector = getTestBed();
    translate = injector.get(TranslateService);
    fixture.detectChanges();
  });

  it('should create the app', waitForAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('should set the language to French', waitForAsync(() => {
    component.setLanguage('en');
    expect(translate.currentLang).toEqual('en');
  }));
});
