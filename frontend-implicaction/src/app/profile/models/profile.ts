import {WorkExperience} from "../../shared/models/work-experience";
import {Training} from "../../shared/models/training";

export interface Profile {
  username: string;
  email: string;
  firstname: string;
  lastname: string;
  birthday: string;
  hobbies: string;
  purpose: string;
  presentation: string;
  expectation: string;
  contribution: string;
  phoneNumber: string;
  avatar: string;
  experiences: WorkExperience[];
  trainings: Training[];
}
