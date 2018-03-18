import { Injectable } from '@angular/core';

import 'rxjs/add/operator/map';

import {Headers, Http, HttpModule} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { TokenParams } from './classes/TokenParams';
import { HttpClient } from '@angular/common/http';
import { LocalStorageService } from './LocalStorageService';
import { Router } from '@angular/router';


@Injectable()
export class AuthService {

  constructor(private http: Http,
              private localStorageService: LocalStorageService,
              private httpClient: HttpClient,
              private router: Router) {}

  private tokenAPI = 'http://localhost:8181/oauth/token';

  loginByHttpClient(username:string, password:string): Observable<TokenParams> {
    var data = "grant_type=password&username=" +username + "&password=" + password + "&client_secret=secret&client_id=web";
    //this.username.next(username);
    return this.httpClient.post<TokenParams>(this.tokenAPI, data);
  }

  public getNewAcessToken(): Observable<TokenParams> {
    let tokenData = this.localStorageService.getAuthorizationData();

    let data = 'grant_type=refresh_token&client_secret=secret&client_id=web&refresh_token=' + tokenData.refresh_token;
    if(tokenData == null || tokenData.refresh_token == null) this.router.navigate(['login']);
    return this.httpClient.post<TokenParams>(this.tokenAPI, data);
    
    }

    logout(){
      this.httpClient.delete<TokenParams>(this.tokenAPI);
      this.localStorageService.deleteAuthorizationData();
      this.router.navigate(['login']);
    }



}
