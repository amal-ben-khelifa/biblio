import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuteurs, Auteurs } from 'app/shared/model/auteurs.model';
import { AuteursService } from './auteurs.service';
import { AuteursComponent } from './auteurs.component';
import { AuteursDetailComponent } from './auteurs-detail.component';
import { AuteursUpdateComponent } from './auteurs-update.component';

@Injectable({ providedIn: 'root' })
export class AuteursResolve implements Resolve<IAuteurs> {
  constructor(private service: AuteursService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuteurs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auteurs: HttpResponse<Auteurs>) => {
          if (auteurs.body) {
            return of(auteurs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Auteurs());
  }
}

export const auteursRoute: Routes = [
  {
    path: '',
    component: AuteursComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.auteurs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuteursDetailComponent,
    resolve: {
      auteurs: AuteursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.auteurs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuteursUpdateComponent,
    resolve: {
      auteurs: AuteursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.auteurs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuteursUpdateComponent,
    resolve: {
      auteurs: AuteursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.auteurs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
