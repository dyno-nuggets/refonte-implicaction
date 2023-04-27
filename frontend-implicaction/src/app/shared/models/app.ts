import {AppStatusEnum} from '../enums/app-status-enum';
import {Feature} from './feature';

export interface App {
  status?: AppStatusEnum;
  features?: Feature[];
}
