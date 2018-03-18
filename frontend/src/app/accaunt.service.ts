import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CheckUserName } from './classes/checkUserName';
import { Observable } from 'rxjs/Observable';
import { TokenParams } from './classes/TokenParams';

@Injectable()
export class AccauntService {

  constructor(private httpClient: HttpClient) { }

  private checkUserNameUrl = 'http://localhost:8181/registration_check/';
  private regAccountApi = 'http://localhost:8181/registration';

  checkUserName(username:string): Observable<CheckUserName>{
    return this.httpClient.get<CheckUserName>(this.checkUserNameUrl + username);
  }

  regAccount(username:string, password:string): Observable<TokenParams>{
    let data = {
      "username":username,
      "password":password
    }
    return this.httpClient.post<TokenParams>(this.regAccountApi, data);
  }

}
