import {
  Component,
  inject,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  Router,
  RouterLink
} from '@angular/router';

import {
  AuthService
} from '../../services/auth.service';

import {
  CartService
} from '../../services/cart.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar
implements OnInit {

  private authService =
    inject(AuthService);

  private cartService =
    inject(CartService);

  private router =
    inject(Router);

  cartCount = 0;

  loggedInUser =
    JSON.parse(
      localStorage.getItem(
        'loggedInUser'
      ) || 'null'
    );

  ngOnInit(): void {

    this.cartService
      .cartCount$
      .subscribe({

        next: count => {

          this.cartCount =
            count;
        }
      });

    if (
      this.loggedInUser
    ) {

      this.cartService
        .updateCartCount(

          this.loggedInUser
            .customerId
        );

      return;
    }

    this.cartService
      .updateCartCount();
  }

  isLoggedIn():
  boolean {

    return this.authService
      .isLoggedIn();
  }

  logout():
  void {

    this.authService
      .logout();

    this.cartService
      .updateCartCount();

    this.router.navigate(
      ['/']
    );
  }
}