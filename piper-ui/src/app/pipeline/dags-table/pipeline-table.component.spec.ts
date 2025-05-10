import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PipelineTableComponent } from './pipeline-table.component';

describe('PipelineTableComponent', () => {
  let component: PipelineTableComponent;
  let fixture: ComponentFixture<PipelineTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PipelineTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PipelineTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
