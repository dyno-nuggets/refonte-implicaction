export interface Group {
  id?: string;
  name: string;
  description?: string;
  numberOfPosts?: number;
  imageUrl?: string;
  createdAt?: Date;
  active?: boolean;
  username?: string;
  userId?: string;
}
