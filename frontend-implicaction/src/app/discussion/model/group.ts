export interface Group {
  id?: string;
  name: string;
  description?: string;
  numberOfPosts?: number;
  imageUrl?: string;
  createdAt?: Date;
  active?: boolean;
}
