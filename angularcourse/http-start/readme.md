vernetto-ed8f3 instead of udemy-ng-http

https://vernetto-ed8f3.firebaseio.com/  instead of https://udemy-ng-http.firebaseio.com/


https://console.firebase.google.com/project/vernetto-ed8f3/database/rules

before:


{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}

or 

{
  "rules": {
    ".read": "true",
    ".write": "true"
  }
}


