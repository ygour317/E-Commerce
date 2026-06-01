import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private http = inject(HttpClient);

  private baseUrl =
    'http://localhost:8080/api/products';

  getProducts():
    Observable<Product[]> {

    return this.http.get<Product[]>(
      this.baseUrl
    );
  }
}   