
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MergeRoutingModule } from './merge-routing.module';
import { MergeComponent } from './pages/merge/merge.component';
import { SharedModule } from '../../shared/shared.module';
import { MergeResultComponent } from './pages/merge-result/merge-result.component';
import { MergeResumeComponent } from './components/merge-resume/merge-resume.component';
import { MergeDetailsIndividualComponent } from './components/merge-details-individual/merge-details-individual.component';
import { MergeAccordionDataComponent } from './components/merge-accordeon-data/merge-accordion-data.component';
import { MergeAccordionDataContentComponent } from './components/merge-accordeon-data-content/merge-accordion-data-content.component';
import { MergeAccordionDataSubtitlesComponent } from './components/merge-accordeon-data-subtitles/merge-accordion-data-subtitles.component';
import { MergeGroupDataComponent } from './components/merge-group-data/merge-group-data.component';
import { MergeDetailsComponent } from './pages/merge-details/merge-details.component';
import { MergeResolver } from '../../core/resolvers/merge.resolver';
import { MergeResultResolver } from '../../core/resolvers/merge-result.resolver';

@NgModule({
  declarations: [MergeComponent, MergeResultComponent, MergeResumeComponent, MergeDetailsIndividualComponent,
    MergeAccordionDataComponent, MergeAccordionDataContentComponent, MergeAccordionDataSubtitlesComponent,
    MergeGroupDataComponent, MergeDetailsComponent],
  imports: [
    CommonModule,
    MergeRoutingModule,
    SharedModule,
    FlexLayoutModule
  ],
  providers: [
    MergeResolver,
    MergeResultResolver
  ]
})
export class MergeModule { }
