export interface Group {
  id?: string;
  name: string;
  description?: string;
  numberOfPosts?: number;
  numberOfComments?: number;
  numberOfUsers?: number;
  imageUrl?: string;
  createdAt?: Date;
  active?: boolean;
  username?: string;
  userId?: string;
}
