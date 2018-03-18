import { FormArray, FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { AccauntService } from '../accaunt.service';
import { Injectable } from '@angular/core';

@Injectable()
export class CustomValidators {
    static nospaceValidator(control: FormControl): { [s: string]: boolean } {
        let re = / /;
        if (control.value && control.value.match(re)) {
            return { nospace: true };
        }
    }

}