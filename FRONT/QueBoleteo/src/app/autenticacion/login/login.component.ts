import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { AuthService, LoginRequest } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule, RouterModule, FormsModule,
    InputTextModule, PasswordModule, ButtonModule, CheckboxModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials: LoginRequest = {
    nombreUsuario: '',
    clave: ''
  };

  errorMsg: string = '';
  loading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    console.log('Enviando:', this.credentials);
    this.errorMsg = '';
    this.loading = true;

    this.authService.login(this.credentials).subscribe({
      next: (res) => {
        this.loading = false;
        // Redirige según el rol
        if (res.role === 'ADMINISTRADOR') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/principal/paginaprincipal']);
        }
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = 'Credenciales inválidas o cuenta no verificada.';
      }
    });
  }
}
