import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private http = inject(HttpClient);

  private baseUrl = 'http://localhost:8080/api/products';

  private adminUrl = 'http://localhost:8080/api/admin/products';

  getProducts():
    Observable<Product[]> {

    return this.http.get<Product[]>(
      this.baseUrl
    );
  }

  getAllProductsForAdmin():
  Observable<Product[]> {

  return this.http.get<Product[]>(
    this.adminUrl
  );
  }

  addProduct(
  product: any
) {

  return this.http.post(

    this.adminUrl,

    product
  );
  }

  updateProduct(
  id: number,
  product: any
) {

  return this.http.put(

    `${this.adminUrl}/${id}`,

    product
  );
  }

  deleteProduct(
  id: number
) {

  return this.http.delete(

    `${this.adminUrl}/${id}`
  );
  }

  reactivateProduct(
  id: number
): Observable<any> {

  return this.http.put(

    `${this.adminUrl}/${id}/reactivate`,

    {}
  );
  }
}   