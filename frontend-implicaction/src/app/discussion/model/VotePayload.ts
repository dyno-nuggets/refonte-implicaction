export enum VoteType {
  UPVOTE,
  DOWNVOTE
}

export interface VotePayload {
  voteType?: VoteType;
  postId?: number;
}
