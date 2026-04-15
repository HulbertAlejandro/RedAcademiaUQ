import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecuperarPasswordDTO } from '../dto/cuenta/recuperar-password.dto';
import { VerificarCodigoDTO } from '../dto/cuenta/verificar-codigo.dto';
import { RestablecerPasswordDTO } from '../dto/cuenta/restablecer-password.dto';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  recuperarPassword(dto: RecuperarPasswordDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/recuperar-password`, dto);
  }

  verificarCodigo(dto: VerificarCodigoDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/verificar-codigo-recuperacion`, dto);
  }

  restablecerPassword(dto: RestablecerPasswordDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/restablecer-password`, dto);
  }
}