import { Component, OnInit } from '@angular/core';
import { AccauntService } from '../accaunt.service';
import { CheckUserName } from '../classes/checkUserName';
import { Router } from '@angular/router';
import { LocalStorageService } from '../LocalStorageService';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { User } from '../classes/User';
import { PasswordValidator } from '../validators/PasswordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  private regDataFormGroup: FormGroup;
  private passwordFormGroup: FormGroup;
  private usernameIsExist = false;
  private userNameChecked = false;
  private user:User;

  constructor(private accauntService: AccauntService,
              private LocalStorageService: LocalStorageService,
              private router: Router,
              private formBuilder: FormBuilder) { 
                
                
               }

  ngOnInit() {
    this.passwordFormGroup = this.formBuilder.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, {
      validator: PasswordValidator.validate.bind(this)
    });
    this.regDataFormGroup = this.formBuilder.group({
      username: ['',Validators.required],
      passwordFormGroup: this.passwordFormGroup
    });


  }

  usernameChange(): void {
    this.usernameIsExist = false;
    this.userNameChecked = false;
  }

  doCheckUserName(): void {
    this.accauntService.checkUserName(this.regDataFormGroup.value.username).subscribe(
      data => {
        this.usernameIsExist = data.isExist;
        this.userNameChecked = !data.isExist;;
      });
  }

  regAccount(): void {
    this.doCheckUserName();

    if (this.usernameIsExist) {
      alert(this.regDataFormGroup.value.username + " is exist");
      return;
    }

    this.accauntService.regAccount(this.regDataFormGroup.value.username, this.passwordFormGroup.value.password)
      .subscribe(
      data => {
        //this.tokenParam = data;
        this.LocalStorageService.setAuthorizationData(data);
        //this.autServive.accessToken = this.tokenParam.access_token;
        this.router.navigate(['/pets']);
      }
      );






  }
}
