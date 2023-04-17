import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-floating-input-with-validation',
  templateUrl: './floating-input-with-validation.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FloatingInputWithValidationComponent implements OnInit {

  @Input() controlName: string;
  @Input() formGroup: FormGroup;
  @Input() submitted: boolean;
  @Input() placeholder = '';
  @Input() controls: Map<string, string>;
  @Input() styleClass: string;
  @Input() label: string;
  @Input() inputType: 'text' | 'password' | 'email' | 'date' | 'hidden' | 'number' | 'range' | 'url' = 'text';

  protected inputId: string;
  protected describedby: string;
  protected required: boolean;

  ngOnInit(): void {
    this.inputId = 'floating' + this.controlName.charAt(0).toUpperCase() + this.controlName.slice();
    this.describedby = this.inputId + 'FeedBack';
    this.required = this.controls?.has('required');
  }

  get control(): AbstractControl<string, string> {
    return this.formGroup.get(this.controlName);
  }

}
