import { EnumCodeLabelAbstract } from 'src/app/shared/enums/enum-code-label-abstract.enum';
import { Group } from '../model/group';
import { Post } from '../model/post';

export enum SortParameterCode {
  Commentaires = 'COMMENTS',
  Posts = 'POSTS',
  Membres = 'MEMBERS',
  Likes = 'LIKES',
  Vues = 'VIEWS',
  Messages = 'POST_COMMENT',
}

export enum SortDirectionNumberEnum {
  ASC = 1,
  DESC = -1,
}

export class SortParametersEnum extends EnumCodeLabelAbstract<SortParameterCode> {
  static readonly FORUM = new SortParametersEnum(
    SortParameterCode.Commentaires,
    'numberOfComments'
  );

  static readonly POST = new SortParametersEnum(
    SortParameterCode.Posts,
    'numberOfPosts'
  );
  static readonly MEMBERS = new SortParametersEnum(
    SortParameterCode.Membres,
    'numberOfUsers'
  );

  static readonly LIKES = new SortParametersEnum(
    SortParameterCode.Likes,
    'voteCount'
  );
  static readonly VIEWS = new SortParametersEnum(
    SortParameterCode.Vues,
    'duration'
  );
  static readonly POST_COMMENT = new SortParametersEnum(
    SortParameterCode.Messages,
    'commentCount'
  );

  constructor(
    readonly code: SortParameterCode,
    readonly label: keyof Group | keyof Post
  ) {
    super(code, label);
  }

  static all(): SortParametersEnum[] {
    return this.values();
  }

  static from(code: SortParameterCode): SortParametersEnum {
    return this.fromCode(code);
  }
}
