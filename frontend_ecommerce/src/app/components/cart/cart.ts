import {
  Component,
  inject,
  OnInit
} from '@angular/core';

import { RouterLink} from '@angular/router';

import {
  CommonModule
} from '@angular/common';

import {
  Navbar
} from '../navbar/navbar';

import {
  CartService
} from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, Navbar,RouterLink],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class Cart
implements OnInit {

  private cartService =
    inject(CartService);

  cartItems: any[] = [];

  totalAmount = 0;

  ngOnInit(): void {

    this.loadCart();
  }

  loadCart(): void {

    const user =
      JSON.parse(
        localStorage.getItem(
          'loggedInUser'
        ) || 'null'
      );

    if (user) {

      this.cartService
        .getCart(
          user.customerId
        )
        .subscribe({

          next:
          response => {

            this.cartItems =
              response.items;

            this.totalAmount =
              response.totalAmount;
          }
        });

      return;
    }

    const guestCart =
      this.cartService
        .getGuestCart();

    this.cartItems =
      guestCart.map(
        item => ({

          ...item,

          subtotal:
            item.price *
            item.quantity
        })
      );

    this.calculateGuestTotal();
  }

  increase(
    productId: number
  ): void {

    const user =
      JSON.parse(
        localStorage.getItem(
          'loggedInUser'
        ) || 'null'
      );

    if (user) {

      this.cartService
        .increase(
          user.customerId,
          productId
        )
        .subscribe({

          next: () => {

            this.loadCart();

            this.cartService
              .updateCartCount(
                user.customerId
              );
          }
        });

      return;
    }

    this.cartService
      .increaseGuest(
        productId
      );

    this.loadCart();
  }

  decrease(
    productId: number
  ): void {

    const user =
      JSON.parse(
        localStorage.getItem(
          'loggedInUser'
        ) || 'null'
      );

    if (user) {

      this.cartService
        .decrease(
          user.customerId,
          productId
        )
        .subscribe({

          next: () => {

            this.loadCart();

            this.cartService
              .updateCartCount(
                user.customerId
              );
          }
        });

      return;
    }

    this.cartService
      .decreaseGuest(
        productId
      );

    this.loadCart();
  }

  getSubtotal(
    item: any
  ): number {

    return (
      item.price *
      item.quantity
    );
  }

  private calculateGuestTotal(): void {

    this.totalAmount =
      this.cartItems.reduce(

        (
          total,
          item
        ) =>

          total +
          (
            item.price *
            item.quantity
          ),

        0
      );
  }

  removeItem( productId: number, quantity: number): void {

    for ( let i = 0; i < quantity; i++) {

      this.decrease(productId); 
    }
 }

 getInitial(name: string): string {

    return name ? name[0].toUpperCase() : '?';
 }

}