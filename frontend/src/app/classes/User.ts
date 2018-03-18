export class User {
 
    username: string;
    password: { 
      pwd: string;
      confirmPwd: string;
    };
 
    constructor(values: Object = {}) {
      //Constructor initialization
      Object.assign(this, values);
  }
 
}