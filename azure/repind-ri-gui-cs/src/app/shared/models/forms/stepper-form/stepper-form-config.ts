import { StepConfig } from "./steps-config";

export interface StepperFormConfig {
    steps: Array<StepConfig>;
    isWithSummary: boolean;
}