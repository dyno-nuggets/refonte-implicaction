import {FeatureKey} from '../enums/feature-key';

export interface Feature {
  id?: string;
  featureKey?: FeatureKey;
  active?: boolean;
}
