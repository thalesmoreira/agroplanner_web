import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';
import { PropriedadeCaracteristicaService } from './propriedade-caracteristica.service';
import { PropriedadeCaracteristicaComponent } from './propriedade-caracteristica.component';
import { PropriedadeCaracteristicaDetailComponent } from './propriedade-caracteristica-detail.component';
import { PropriedadeCaracteristicaUpdateComponent } from './propriedade-caracteristica-update.component';
import { PropriedadeCaracteristicaDeletePopupComponent } from './propriedade-caracteristica-delete-dialog.component';
import { IPropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';

@Injectable({ providedIn: 'root' })
export class PropriedadeCaracteristicaResolve implements Resolve<IPropriedadeCaracteristica> {
  constructor(private service: PropriedadeCaracteristicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPropriedadeCaracteristica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PropriedadeCaracteristica>) => response.ok),
        map((propriedade_caracteristica: HttpResponse<PropriedadeCaracteristica>) => propriedade_caracteristica.body)
      );
    }
    return of(new PropriedadeCaracteristica());
  }
}

export const propriedade_caracteristicaRoute: Routes = [
  {
    path: '',
    component: PropriedadeCaracteristicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PropriedadeCaracteristicaDetailComponent,
    resolve: {
      propriedade_caracteristica: PropriedadeCaracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PropriedadeCaracteristicaUpdateComponent,
    resolve: {
      propriedade_caracteristica: PropriedadeCaracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PropriedadeCaracteristicaUpdateComponent,
    resolve: {
      propriedade_caracteristica: PropriedadeCaracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const propriedade_caracteristicaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PropriedadeCaracteristicaDeletePopupComponent,
    resolve: {
      propriedade_caracteristica: PropriedadeCaracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
