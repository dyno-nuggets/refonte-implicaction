export enum AlertType {
  SUCCESS = 'success', DANGER = 'danger',
  INFO = 'info',
}

export interface Alert {
  title?: string;
  message?: string;
  type?: AlertType;
  code?: number;
}
