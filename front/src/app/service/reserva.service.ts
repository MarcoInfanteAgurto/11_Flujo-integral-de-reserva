import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private apiUrl = 'http://localhost:8080/api/reservas/confirmar';

  constructor(private http: HttpClient) {}

  confirmarReserva(cantidad: number, precio: number, codigo: string, stock: number): Observable<any> {
    const params = new HttpParams()
      .set('cantidad', cantidad.toString())
      .set('precio', precio.toString())
      .set('codigo', codigo)
      .set('stock', stock.toString());

    return this.http.post<any>(this.apiUrl, null, { params });
  }
}