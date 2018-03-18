import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders, HttpSentEvent, HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpErrorResponse, HttpUserEvent} from '@angular/common/http';
import { Observable } from 'rxjs/Rx';//'rxjs/Observable';
import { LocalStorageService } from '../LocalStorageService';
import { AuthService } from '../auth.service';
import { TokenParams } from '../classes/TokenParams';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private localStorageService:LocalStorageService,
                private authService: AuthService,
                private router: Router) {}

                addToken(req: HttpRequest<any>, tokenParams: TokenParams): HttpRequest<any> {
                    return req.clone({ setHeaders: { Authorization: 'Bearer ' + tokenParams.access_token }})
                }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>>
    //Observable<HttpEvent<any>>
    {
        console.log(req);
        console.log('hello test');

        if(req.url.indexOf('/registration_check')>0
            || req.url.indexOf('/registration')>0) {
            return next.handle(req);
        }

        if(req.url.indexOf('/oauth/token')>0) {
            var headersForTokenAPI = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
            req = req.clone({headers: headersForTokenAPI});
            return next.handle(req);
        }

        var tokenParams = this.localStorageService.getAuthorizationData();
        if(tokenParams == null || tokenParams.access_token == null) this.cleanLocalStorageAndRouteToLoginPage();
        var authHeader = 'Bearer ' + tokenParams.access_token;
        const authReq = req.clone({setHeaders: {Authorization: authHeader}});

        return next.handle(authReq)
        .catch(error => {
            if (error instanceof HttpErrorResponse) {
                switch ((<HttpErrorResponse>error).status) {
                    case 400:
                        return this.handle400Error(error);
                    case 401:
                        return this.handle401Error(req, next);
                }
            } else {
                return Observable.throw(error);
            }
        });
    }

    handle400Error(error) {
        if (error && error.status === 400 && error.error && error.error.error === 'invalid_grant') {
            return this.cleanLocalStorageAndRouteToLoginPage();
        }

        return Observable.throw(error);
    }

    handle401Error(req: HttpRequest<any>, next: HttpHandler) {
            return this.authService.getNewAcessToken()
                .switchMap((tokenParams: TokenParams) => {
                    if (tokenParams != null && tokenParams.access_token) {
                        this.localStorageService.setAuthorizationData(tokenParams);
                        return next.handle(this.addToken(req, tokenParams));
                    }
                    return this.cleanLocalStorageAndRouteToLoginPage();
                })
                .catch(error => {
                    return this.cleanLocalStorageAndRouteToLoginPage();
                })
                .finally(() => {

                });
    }

    cleanLocalStorageAndRouteToLoginPage() {
        this.localStorageService.deleteAuthorizationData();
        this.router.navigate(['login']);
        return Observable.throw("");
    }
} 