import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  template: ''
})
export class BaseFormComponent<T> implements OnInit, OnDestroy {

  @Input() isLoading: boolean;

  @Output() submitForm = new EventEmitter<T>();

  protected form!: FormGroup;
  protected submitted = false;

  private onDestroySubject = new Subject<void>();

  ngOnInit(): void {
    this.initForm();
    this.form.valueChanges
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(() => this.submitted = false);
  }

  protected submit(): void {
    if (this.form.invalid) {
      return;
    }

    this.submitted = true;
    this.submitForm.emit({...this.form.value});
  }

  protected initForm(): void {
  };

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
