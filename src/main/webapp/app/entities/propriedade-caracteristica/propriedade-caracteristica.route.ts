import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Propriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';
import { Propriedade_caracteristicaService } from './propriedade-caracteristica.service';
import { Propriedade_caracteristicaComponent } from './propriedade-caracteristica.component';
import { Propriedade_caracteristicaDetailComponent } from './propriedade-caracteristica-detail.component';
import { Propriedade_caracteristicaUpdateComponent } from './propriedade-caracteristica-update.component';
import { Propriedade_caracteristicaDeletePopupComponent } from './propriedade-caracteristica-delete-dialog.component';
import { IPropriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';

@Injectable({ providedIn: 'root' })
export class Propriedade_caracteristicaResolve implements Resolve<IPropriedade_caracteristica> {
  constructor(private service: Propriedade_caracteristicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPropriedade_caracteristica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Propriedade_caracteristica>) => response.ok),
        map((propriedade_caracteristica: HttpResponse<Propriedade_caracteristica>) => propriedade_caracteristica.body)
      );
    }
    return of(new Propriedade_caracteristica());
  }
}

export const propriedade_caracteristicaRoute: Routes = [
  {
    path: '',
    component: Propriedade_caracteristicaComponent,
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
    component: Propriedade_caracteristicaDetailComponent,
    resolve: {
      propriedade_caracteristica: Propriedade_caracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Propriedade_caracteristicaUpdateComponent,
    resolve: {
      propriedade_caracteristica: Propriedade_caracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Propriedade_caracteristicaUpdateComponent,
    resolve: {
      propriedade_caracteristica: Propriedade_caracteristicaResolve
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
    component: Propriedade_caracteristicaDeletePopupComponent,
    resolve: {
      propriedade_caracteristica: Propriedade_caracteristicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade_caracteristica.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
