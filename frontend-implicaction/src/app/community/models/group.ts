export interface Group {
  id?: string;
  name: string;
  description?: string;
  createdAt?: Date;
  creator?: string;
  imageUrl?: string;
  enabled?: boolean;
  numberOfUsers?: number;
}
