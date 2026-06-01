import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { passwordMatchValidator } from '../../validators/password-match.validator';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ CommonModule, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})

// CHANGED RegisterComponent to Register
export class Register {

  private fb = inject(FormBuilder);
  private customerService = inject(CustomerService);
  private router = inject(Router);

  successMessage = '';
  errorMessage = '';

  registerForm = this.fb.group(
    {
      name: [ '', [ Validators.required, Validators.maxLength(50)] ],

      email: [ '', [ Validators.required, Validators.email]],

      password: [ '', [ Validators.required, Validators.minLength(10) ] ],

      confirmPassword: [ '',  Validators.required ]
    },
    {
      validators: passwordMatchValidator()
    }
  );

  onSubmit(): void {

    this.successMessage = '';
    this.errorMessage = '';

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.customerService
      .registerCustomer(
        this.registerForm.value as any
      )
      .subscribe({

        next: (response) => {
          this.successMessage = `Customer Registered Successfully! Customer ID: ${response.customerId}`;
          this.registerForm.reset();
          this.router.navigate(['/']);
        },

        error: (error) => {

          if (error.error?.message) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage ='Something went wrong';
          }
        }
      });
  }
}