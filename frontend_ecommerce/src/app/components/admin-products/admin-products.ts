import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-products',
  imports: [ FormsModule],
  templateUrl: './admin-products.html',
  styleUrl: './admin-products.css',
})
export class AdminProducts
implements OnInit {

  isEditMode = false;

  editingProductId: number | null = null;

  showForm = false;

  productForm = {

    name: '',

    description: '',

    price: 0,

    imageUrl: '',

    stockQuantity: 0,

    category: ''
  };

  private productService =
    inject(
      ProductService
    );

  products:
    Product[] = [];

  ngOnInit(): void {

    this.loadProducts();
  }

  loadProducts(): void {

    this.productService
      .getAllProductsForAdmin()
      .subscribe({

        next: (
          products
        ) => {

          this.products =
            products;
        }
      });
  }

  deleteProduct(
  id: number
): void {

  const confirmed =
    confirm(
      'Delete this product?'
    );

  if (!confirmed) {
    return;
  }

  this.productService
      .deleteProduct(id)
      .subscribe({

        next: () => {

          this.loadProducts();
        }
      });
  }

  reactivateProduct(
  id: number
): void {

  this.productService
      .reactivateProduct(id)
      .subscribe({

        next: () => {

          this.loadProducts();
        }
      });
  }

  openAddForm(): void {

  this.showForm = true;

  this.isEditMode = false;

  this.editingProductId = null;

  this.productForm = {

    name: '',

    description: '',

    price: 0,

    imageUrl: '',

    stockQuantity: 0,

    category: ''
  };
  }

  saveProduct(): void {

  if (
    this.isEditMode &&
    this.editingProductId
  ) {

    this.productService
        .updateProduct(

          this.editingProductId,

          this.productForm
        )
        .subscribe({

          next: () => {

            this.showForm = false;

            this.loadProducts();
          }
        });

    return;
  }

  this.productService
      .addProduct(
        this.productForm
      )
      .subscribe({

        next: () => {

          this.showForm = false;

          this.loadProducts();
        }
      });
  }

  editProduct(
  product: Product
): void {

  this.showForm = true;

  this.isEditMode = true;

  this.editingProductId =
    product.id;

  this.productForm = {

    name:
      product.name,

    description:
      product.description,

    price:
      product.price,

    imageUrl:
      product.imageUrl || '',

    stockQuantity:
      product.stockQuantity,

    category:
      product.category
  };
  }

}
