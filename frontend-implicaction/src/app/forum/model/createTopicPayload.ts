export interface CreateTopicPayload {
  title: string;
  message: string;
  isLocked: boolean;
  isPinned: boolean;
  categoryId: number;
}
