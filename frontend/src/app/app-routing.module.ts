import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PetsComponent } from './pets/pets.component';
import { RegisterComponent } from './register/register.component';
import { HomeLayoutComponent } from './layouts/home-layout/home-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';

const routes: Routes = [
  
  
  {
    path: '',                       
    component: HomeLayoutComponent,
    //canActivate: [AuthGuard],      
    children: [
      {
        path: '', 
        redirectTo: '/pets', 
        pathMatch: 'full'
      },
      { 
        path: 'pets', 
        component: PetsComponent
      }
    ]
  },


  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      { 
        path: 'register', 
        component: RegisterComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
