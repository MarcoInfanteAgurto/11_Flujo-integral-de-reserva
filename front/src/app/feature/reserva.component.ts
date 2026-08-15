import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Obligatorio para ngModel
import { ReservaService } from '../service/reserva.service';

@Component({
  selector: 'app-reserva',
  standalone: true,
  imports: [CommonModule, FormsModule], // Verificar que esté aquí
  templateUrl: './reserva.component.html',
  styleUrls: ['./reserva.component.scss']
})

export class ReservaComponent {
  // Datos del Evento (Simulados en frontend)
  evento = {
    nombre: 'Concierto de Rock EventPass',
    precioUnitario: 100.0,
    stockDisponible: 50
  };

  // Formulario
  cantidad: number = 1;
  codigoDescuento: string = '';

  // Estados de respuesta
  mensajeRespuesta: string = '';
  totalPagar: number | null = null;
  esExitoso: boolean = false;
  cargando: boolean = false;

  constructor(private reservaService: ReservaService) {}

  procesarReserva(): void {
    this.cargando = true;
    this.mensajeRespuesta = '';

    this.reservaService.confirmarReserva(
      this.cantidad,
      this.evento.precioUnitario,
      this.codigoDescuento,
      this.evento.stockDisponible
    ).subscribe({
      next: (res) => {
        this.cargando = false;
        this.esExitoso = res.exito;
        this.mensajeRespuesta = res.mensaje;
        this.totalPagar = res.totalPagar;
      },
      error: (err) => {
        this.cargando = false;
        this.esExitoso = false;
        this.mensajeRespuesta = err.error?.mensaje || 'Error de conexión con el servidor.';
        this.totalPagar = null;
      }
    });
  }
}