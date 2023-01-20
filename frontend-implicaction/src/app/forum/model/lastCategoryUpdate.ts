import {User} from '../../shared/models/user';

export interface LastCategoryUpdate {
  type: string;
  title: string;
  author: User;
  createdAt: Date;
  topicId: number;
  responseId: number;
}
