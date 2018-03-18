import { Injectable } from '@angular/core';
import { TokenParams } from './classes/TokenParams';

@Injectable()
export class LocalStorageService {

    public setAuthorizationData(auth: TokenParams):void{
        localStorage.setItem("Authorization", JSON.stringify(auth));
    }

    public getAuthorizationData():TokenParams{
        let tokenData = JSON.parse(localStorage.getItem("Authorization"));
        return tokenData == null ? null:tokenData;
    }

    public deleteAuthorizationData(){
        localStorage.removeItem("Authorization");
    }


}