export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message: string;
  timestamp: string;
}

export interface ErrorResponse {
  success: boolean;
  message: string;
  errors: { [key: string]: string };
  timestamp: string;
}
