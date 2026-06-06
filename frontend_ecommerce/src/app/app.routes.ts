import { Routes } from '@angular/router';

// import { RegisterComponent}
import { Register } from './components/register/register';

import { Login } from './components/login/login';

import { Home } from './components/home/home';

import { Cart} from './components/cart/cart';
import { AdminProducts } from './components/admin-products/admin-products';

export const routes:
Routes = [

  {
    path: '',
    component: Home
  },
  {
    path: 'admin/products',
    component: AdminProducts
},

  {
    path: 'register',
    component: Register
  },

  {
    path: 'login',
    component: Login
  },

  {
    path: 'cart',
    component: Cart
  },

  {
    path: '**',
    redirectTo: ''
  }
];