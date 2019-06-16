import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';
import { PropriedadeContratadaService } from './propriedade-contratada.service';
import { PropriedadeContratadaComponent } from './propriedade-contratada.component';
import { PropriedadeContratadaDetailComponent } from './propriedade-contratada-detail.component';
import { PropriedadeContratadaUpdateComponent } from './propriedade-contratada-update.component';
import { PropriedadeContratadaDeletePopupComponent } from './propriedade-contratada-delete-dialog.component';
import { IPropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';

@Injectable({ providedIn: 'root' })
export class PropriedadeContratadaResolve implements Resolve<IPropriedadeContratada> {
  constructor(private service: PropriedadeContratadaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPropriedadeContratada> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PropriedadeContratada>) => response.ok),
        map((propriedadeContratada: HttpResponse<PropriedadeContratada>) => propriedadeContratada.body)
      );
    }
    return of(new PropriedadeContratada());
  }
}

export const propriedadeContratadaRoute: Routes = [
  {
    path: '',
    component: PropriedadeContratadaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'agroplannerApp.propriedadeContratada.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PropriedadeContratadaDetailComponent,
    resolve: {
      propriedadeContratada: PropriedadeContratadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeContratada.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PropriedadeContratadaUpdateComponent,
    resolve: {
      propriedadeContratada: PropriedadeContratadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeContratada.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PropriedadeContratadaUpdateComponent,
    resolve: {
      propriedadeContratada: PropriedadeContratadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeContratada.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const propriedadeContratadaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PropriedadeContratadaDeletePopupComponent,
    resolve: {
      propriedadeContratada: PropriedadeContratadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeContratada.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
