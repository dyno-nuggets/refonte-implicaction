export enum AlertType {
  SUCCESS = 'success', DANGER = 'danger'
}

export interface Alert {
  title?: string;
  message?: string;
  type?: AlertType;
}
