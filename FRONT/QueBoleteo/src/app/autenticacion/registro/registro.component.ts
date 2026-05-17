import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { PasswordModule } from 'primeng/password';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { AuthService, RegisterRequest } from '../../core/services/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [
    CommonModule, RouterModule, FormsModule,
    InputTextModule, InputMaskModule, PasswordModule,
    DatePickerModule, ButtonModule, CheckboxModule
  ],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {

  maxDate = new Date();

  // Campos del formulario
  form = {
    correo: '',
    nombreUsuario: '',
    clave: '',
    documentoIdentidad: '',
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    fechaNacimiento: null as Date | null,
    numeroTelefono: ''
  };

  errorMsg: string  = '';
  successMsg: string = '';
  loading: boolean   = false;

  constructor(private authService: AuthService, private router: Router) {}

  onRegistro(): void {
    this.errorMsg  = '';
    this.successMsg = '';
    this.loading    = true;

    // Convertir Date → "YYYY-MM-DD" que espera el backend (LocalDate)
    const fechaStr = this.form.fechaNacimiento
      ? this.form.fechaNacimiento.toISOString().split('T')[0]
      : '';

    const payload: RegisterRequest = {
      correo:            this.form.correo,
      nombreUsuario:     this.form.nombreUsuario,
      clave:             this.form.clave,
      documentoIdentidad: this.form.documentoIdentidad,
      primerNombre:      this.form.primerNombre,
      segundoNombre:     this.form.segundoNombre || undefined,
      primerApellido:    this.form.primerApellido,
      segundoApellido:   this.form.segundoApellido || undefined,
      fechaNacimiento:   fechaStr,
      numeroTelefono:    this.form.numeroTelefono || undefined
    };

    this.authService.register(payload).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Registro exitoso. Revisa tu correo para verificar tu cuenta.';
        // Opcional: redirigir al login después de 2 segundos
        setTimeout(() => this.router.navigate(['/autenticacion/login']), 2000);
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 409) {
          this.errorMsg = err.error; // "El nombre de usuario ya existe" o "Este correo ya tiene cuenta"
        } else {
          this.errorMsg = 'Error al registrar. Intenta de nuevo.';
        }
      }
    });
  }
}
