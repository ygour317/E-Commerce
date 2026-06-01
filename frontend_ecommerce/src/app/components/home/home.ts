import {
  Component,
  inject,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  ProductService
} from '../../services/product.service';

import {
  CartService
} from '../../services/cart.service';

import {
  Product
} from '../../models/product';

import {
  Navbar
} from '../navbar/navbar';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    Navbar
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home
implements OnInit {

  private productService =
    inject(ProductService);

  private cartService =
    inject(CartService);

  products:
    Product[] = [];

  cartItems:
    any[] = [];

  loggedInUser =
    JSON.parse(
      localStorage.getItem(
        'loggedInUser'
      ) || 'null'
    );

  ngOnInit(): void {

    this.loadProducts();

    if (
      this.loggedInUser
    ) {

      this.loadUserCart();
    }
  }

  loadProducts(): void {

    this.productService
      .getProducts()
      .subscribe({

        next: response => {

          this.products =
            response;
        }
      });
  }

  loadUserCart(): void {

    this.cartService
      .getCart(
        this.loggedInUser
          .customerId
      )
      .subscribe({

        next: response => {

          this.cartItems =
            response.items;
        }
      });
  }

  getQuantity(
  productId: number
): number {

  const user =
    JSON.parse(
      localStorage.getItem(
        'loggedInUser'
      ) || 'null'
    );

  if (user) {

    const item =
      this.cartItems.find(
        x =>
          x.productId ===
            productId
      );

    return item
      ? item.quantity
      : 0;
  }

  return this.cartService
    .getQuantity(
      productId
    );
}

  addToCart(
    productId: number
  ): void {

    const user =
  JSON.parse(
    localStorage.getItem(
      'loggedInUser'
    ) || 'null'
  );

    if (user){

      this.cartService
        .addToCart(
          user.customerId,
          productId
        )
        .subscribe({

          next: () => {

            this.loadUserCart();

             this.cartService
                .updateCartCount(
                  user.customerId
                );
          }
        });

      return;
    }

    this.cartService
      .addGuestCart(
        productId
      );
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

            this.loadUserCart();
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

            this.loadUserCart();
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
  }
}