import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PropriedadeFoto } from 'app/shared/model/propriedade-foto.model';
import { PropriedadeFotoService } from './propriedade-foto.service';
import { PropriedadeFotoComponent } from './propriedade-foto.component';
import { PropriedadeFotoDetailComponent } from './propriedade-foto-detail.component';
import { PropriedadeFotoUpdateComponent } from './propriedade-foto-update.component';
import { PropriedadeFotoDeletePopupComponent } from './propriedade-foto-delete-dialog.component';
import { IPropriedadeFoto } from 'app/shared/model/propriedade-foto.model';

@Injectable({ providedIn: 'root' })
export class PropriedadeFotoResolve implements Resolve<IPropriedadeFoto> {
  constructor(private service: PropriedadeFotoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPropriedadeFoto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PropriedadeFoto>) => response.ok),
        map((propriedadeFoto: HttpResponse<PropriedadeFoto>) => propriedadeFoto.body)
      );
    }
    return of(new PropriedadeFoto());
  }
}

export const propriedadeFotoRoute: Routes = [
  {
    path: '',
    component: PropriedadeFotoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'agroplannerApp.propriedadeFoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PropriedadeFotoDetailComponent,
    resolve: {
      propriedadeFoto: PropriedadeFotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeFoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PropriedadeFotoUpdateComponent,
    resolve: {
      propriedadeFoto: PropriedadeFotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeFoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PropriedadeFotoUpdateComponent,
    resolve: {
      propriedadeFoto: PropriedadeFotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeFoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const propriedadeFotoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PropriedadeFotoDeletePopupComponent,
    resolve: {
      propriedadeFoto: PropriedadeFotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedadeFoto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
