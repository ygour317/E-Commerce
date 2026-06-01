import { CartItem }
from './cart-item';

export interface CartResponse {

  customerId: number;

  items: CartItem[];

  totalAmount: number;
}