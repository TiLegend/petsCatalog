import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { Http, HttpModule } from '@angular/http';
import { AuthService } from './auth.service';
import { PetsComponent } from './pets/pets.component';
import { LocalStorageService } from './LocalStorageService';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptor/AuthInterceptor';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HeaderComponent } from './template/header/header.component';
import { RegisterComponent } from './register/register.component';
import { AccauntService } from './accaunt.service';
import { PetService } from './pet.service';
import { AddPetComponent } from './add-pet/add-pet.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DataTableModule, SharedModule, ButtonModule, DialogModule, DropdownModule, SelectButtonModule, CalendarModule } from 'primeng/primeng';
import { HomeLayoutComponent } from './layouts/home-layout/home-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,    
    HttpModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    DataTableModule, 
    SharedModule, 
    ButtonModule, 
    DialogModule,
    DropdownModule,
    SelectButtonModule,
    BrowserAnimationsModule,
    CalendarModule
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    PetsComponent,
    HeaderComponent,
    RegisterComponent,
    AddPetComponent,
    HomeLayoutComponent,
    LoginLayoutComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    AuthService,
    PetService,
    LocalStorageService,
    AccauntService],
  bootstrap: [AppComponent]
})
export class AppModule { }
