import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/login-request';
import {CartService} from '../../services/cart.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  private authService = inject(AuthService);

  private router = inject(Router);

  private cartService = inject(CartService);

  request:
    LoginRequest = {

    email: '',
    password: ''
  };

  errorMessage =
    '';

  login(): void {

    this.errorMessage =
      '';

    this.authService
      .login(this.request)
      .subscribe({

        next: (response) => {

        const guestCart =
          this.cartService
            .getGuestCart();

        // No guest cart

        if (
          guestCart.length === 0
        ) {

          localStorage.setItem(
            'loggedInUser',
            JSON.stringify(response)
          );

          this.cartService
            .updateCartCount(
              response.customerId
            );

          this.router.navigate(
            ['/']
          );

          return;
        }

        // Merge guest cart

        this.cartService
          .mergeGuestCart(
            response.customerId
          )
          .subscribe({

            next: () => {

              this.cartService
                .clearGuestCart();

              localStorage
                .setItem(
                  'loggedInUser',
                  JSON.stringify(
                    response
                  )
                );

              this.cartService
                .updateCartCount(
                  response.customerId
                );

              this.router
                .navigate(
                  ['/']
                );
            }
          });
      },

        error: () => {

          this.errorMessage =
            'Invalid email or password';
        }
      });
  }
}