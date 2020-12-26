import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivres, Livres } from 'app/shared/model/livres.model';
import { LivresService } from './livres.service';
import { LivresComponent } from './livres.component';
import { LivresDetailComponent } from './livres-detail.component';
import { LivresUpdateComponent } from './livres-update.component';

@Injectable({ providedIn: 'root' })
export class LivresResolve implements Resolve<ILivres> {
  constructor(private service: LivresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivres> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livres: HttpResponse<Livres>) => {
          if (livres.body) {
            return of(livres.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Livres());
  }
}

export const livresRoute: Routes = [
  {
    path: '',
    component: LivresComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.livres.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LivresDetailComponent,
    resolve: {
      livres: LivresResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.livres.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LivresUpdateComponent,
    resolve: {
      livres: LivresResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.livres.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LivresUpdateComponent,
    resolve: {
      livres: LivresResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.livres.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
