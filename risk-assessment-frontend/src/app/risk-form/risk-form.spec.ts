import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskForm } from './risk-form';

describe('RiskForm', () => {
  let component: RiskForm;
  let fixture: ComponentFixture<RiskForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiskForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
