import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GenTokenService {

  private  baseURL = 'http://localhost:8080/auth/signin';

  constructor() { }

  async getToken(email: string, password: string): Promise<string> {
    console.log(JSON.stringify({ email, password }))
    const response = await fetch(this.baseURL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    });

    const data = await response.json();
    return data.token;
  }
}
