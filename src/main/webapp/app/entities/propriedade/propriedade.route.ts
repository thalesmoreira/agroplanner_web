import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Propriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from './propriedade.service';
import { PropriedadeComponent } from './propriedade.component';
import { PropriedadeDetailComponent } from './propriedade-detail.component';
import { PropriedadeUpdateComponent } from './propriedade-update.component';
import { PropriedadeDeletePopupComponent } from './propriedade-delete-dialog.component';
import { IPropriedade } from 'app/shared/model/propriedade.model';

@Injectable({ providedIn: 'root' })
export class PropriedadeResolve implements Resolve<IPropriedade> {
  constructor(private service: PropriedadeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPropriedade> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Propriedade>) => response.ok),
        map((propriedade: HttpResponse<Propriedade>) => propriedade.body)
      );
    }
    return of(new Propriedade());
  }
}

export const propriedadeRoute: Routes = [
  {
    path: '',
    component: PropriedadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'agroplannerApp.propriedade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PropriedadeDetailComponent,
    resolve: {
      propriedade: PropriedadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PropriedadeUpdateComponent,
    resolve: {
      propriedade: PropriedadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PropriedadeUpdateComponent,
    resolve: {
      propriedade: PropriedadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const propriedadePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PropriedadeDeletePopupComponent,
    resolve: {
      propriedade: PropriedadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'agroplannerApp.propriedade.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
