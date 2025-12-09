import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An unknown error occurred';

      if (error.error && error.error.message) {
        // Backend returned an ErrorResponse
        errorMessage = error.error.message;
        
        // Log field errors if present
        if (error.error.errors && Object.keys(error.error.errors).length > 0) {
          console.error('Validation errors:', error.error.errors);
        }
      } else if (error.status === 0) {
        errorMessage = 'Unable to connect to the server. Please check your connection.';
      } else if (error.status >= 500) {
        errorMessage = 'Server error occurred. Please try again later.';
      } else if (error.message) {
        errorMessage = error.message;
      }

      console.error('HTTP Error:', errorMessage);
      return throwError(() => new Error(errorMessage));
    })
  );
};
