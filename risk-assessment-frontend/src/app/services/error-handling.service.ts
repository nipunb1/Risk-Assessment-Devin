import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  handleError(error: any, userMessage: string): void {
    console.error('Application error:', error);
    
    if (error?.error?.message) {
      console.error('Server error message:', error.error.message);
    }
    
    if (error?.status) {
      console.error('HTTP status:', error.status);
    }
  }

  getErrorMessage(operation: string): string {
    return `Failed to ${operation}. Please try again.`;
  }
}
