import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CopilotDashboardComponent } from './copilot-dashboard/copilot-dashboard';
import { DoraDashboardComponent } from './dora-dashboard/dora-dashboard';
import { DevManifestoDashboardComponent } from './dev-manifesto-dashboard/dev-manifesto-dashboard';

@Component({
  selector: 'app-engineering-excellence',
  imports: [CommonModule, CopilotDashboardComponent, DoraDashboardComponent, DevManifestoDashboardComponent],
  templateUrl: './engineering-excellence.html',
  styleUrl: './engineering-excellence.css'
})
export class EngineeringExcellenceComponent {
  activeTab: string = 'copilot';

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }
}
