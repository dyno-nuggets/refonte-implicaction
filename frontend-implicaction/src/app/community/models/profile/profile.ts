import {WorkExperience} from "../../../shared/models/work-experience";
import {Training} from "../../../shared/models/training";
import {Relation} from "../relation";

export interface Profile {
  username?: string;
  email?: string;
  firstname?: string;
  lastname?: string;
  registeredAt?: string;
  birthday?: string;
  hobbies?: string;
  purpose?: string;
  presentation?: string;
  expectation?: string;
  contribution?: string;
  phoneNumber?: string;
  imageUrl?: string;
  experiences?: WorkExperience[];
  trainings?: Training[];
  relationWithCurrentUser?: Relation;
}
