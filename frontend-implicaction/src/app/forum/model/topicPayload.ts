export interface TopicPayload {
  id?: number;
  title: string;
  message: string;
  locked: boolean;
  pinned: boolean;
  categoryId: number;
}
