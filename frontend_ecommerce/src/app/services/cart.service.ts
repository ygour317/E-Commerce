import {
  inject,
  Injectable
} from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  BehaviorSubject,
  forkJoin,
  Observable
} from 'rxjs';

import {
  CartResponse
} from '../models/cart-response';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private http =
    inject(HttpClient);

  private baseUrl =
    'http://localhost:8080/api/cart';

  private storageKey =
    'guestCart';

    private cartCountSubject = new BehaviorSubject<number>(0);

    cartCount$ = this.cartCountSubject.asObservable();

  addToCart(
    customerId: number,
    productId: number
  ): Observable<CartResponse> {

    return this.http.post<CartResponse>(
      `${this.baseUrl}/add`,
      {
        customerId,
        productId,
        quantity: 1
      }
    );
  }

  increase(
    customerId: number,
    productId: number
  ): Observable<CartResponse> {

    return this.http.put<CartResponse>(
      `${this.baseUrl}/increase`,
      {
        customerId,
        productId
      }
    );
  }

  decrease(
    customerId: number,
    productId: number
  ): Observable<CartResponse> {

    return this.http.put<CartResponse>(
      `${this.baseUrl}/decrease`,
      {
        customerId,
        productId
      }
    );
  }

  getCart(
    customerId: number
  ): Observable<CartResponse> {

    return this.http.get<CartResponse>(
      `${this.baseUrl}/${customerId}`
    );
  }

  // Guest cart localStorage

  getGuestCart(): any[] {

    return JSON.parse(
      localStorage.getItem(
        this.storageKey
      ) || '[]'
    );
  }

  addGuestCart(
  product: any
): void {

  const cart =
    this.getGuestCart();

  const existing =
    cart.find(
      x =>
        x.productId ===
        product.productId
    );

  if (existing) {

    existing.quantity++;

  } else {

    cart.push({

      productId:
        product.id,

      productName:
        product.name,

      imageUrl:
        product.imageUrl,

      price:
        product.price,

      quantity: 1
    });
  }

  localStorage.setItem(
    this.storageKey,
    JSON.stringify(cart)
  );

  this.updateCartCount();
}


  increaseGuest(
    productId: number
  ): void {

    const cart =
      this.getGuestCart();

    const item =
      cart.find(
        x =>
          x.productId ===
          productId
      );

    if (item) {

      item.quantity++;
    }

    localStorage.setItem(
      this.storageKey,
      JSON.stringify(cart)
    );

    this.updateCartCount();
  }

  decreaseGuest(
    productId: number
  ): void {

    let cart =
      this.getGuestCart();

    const item =
      cart.find(
        x =>
          x.productId ===
          productId
      );

    if (!item) {
      return;
    }

    if (
      item.quantity === 1
    ) {

      cart =
        cart.filter(
          x =>
            x.productId !==
            productId
        );

    } else {

      item.quantity--;
    }

    localStorage.setItem(
      this.storageKey,
      JSON.stringify(cart)
    );

    this.updateCartCount();
  }

  getQuantity(
    productId: number
  ): number {

    const cart =
      this.getGuestCart();

    const item =
      cart.find(
        x =>
          x.productId ===
          productId
      );

    return item
      ? item.quantity
      : 0;
  }







  updateCartCount(
  customerId?: number
): void {

  // logged user

  if (customerId) {

    this.getCart(
      customerId
    ).subscribe({

      next:
      (response) => {

        const total =
          response.items
            .reduce(

              (
                sum,
                item
              ) =>

                sum +
                item.quantity,

              0
            );

        this
          .cartCountSubject
          .next(
            total
          );
      }
    });

    return;
  }

  // guest

  const cart =
    this.getGuestCart();

  const total =
    cart.reduce(

      (sum, item) =>

        sum +
        item.quantity,

      0
    );

  this.cartCountSubject
    .next(total);
}

clearGuestCart(): void {

  localStorage.removeItem(
    this.storageKey
  );

  this.updateCartCount();
}

mergeGuestCart(
  customerId: number
): Observable<boolean> {

  const guestCart =
    this.getGuestCart();

  const requests:
    Array<
      () => Observable<CartResponse>
    > = [];

  for (
    const item
    of guestCart
  ) {

    for (
      let i = 0;
      i < item.quantity;
      i++
    ) {

      requests.push(
        () =>
          this.addToCart(
            customerId,
            item.productId
          )
      );
    }
  }

  return new Observable<boolean>(
    observer => {

      const runSequentially =
        (
          index: number
        ): void => {

          if (
            index >=
            requests.length
          ) {

            observer.next(
              true
            );

            observer.complete();

            return;
          }

          requests[
            index
          ]()
          .subscribe({

            next: () => {

              runSequentially(
                index + 1
              );
            },

            error:
            (
              error: unknown
            ) => {

              observer.error(
                error
              );
            }
          });
        };

      runSequentially(
        0
      );
    }
  );
}

}