import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJeuxEducatif, JeuxEducatif } from 'app/shared/model/jeux-educatif.model';
import { JeuxEducatifService } from './jeux-educatif.service';
import { JeuxEducatifComponent } from './jeux-educatif.component';
import { JeuxEducatifDetailComponent } from './jeux-educatif-detail.component';
import { JeuxEducatifUpdateComponent } from './jeux-educatif-update.component';

@Injectable({ providedIn: 'root' })
export class JeuxEducatifResolve implements Resolve<IJeuxEducatif> {
  constructor(private service: JeuxEducatifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJeuxEducatif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jeuxEducatif: HttpResponse<JeuxEducatif>) => {
          if (jeuxEducatif.body) {
            return of(jeuxEducatif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JeuxEducatif());
  }
}

export const jeuxEducatifRoute: Routes = [
  {
    path: '',
    component: JeuxEducatifComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.jeuxEducatif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JeuxEducatifDetailComponent,
    resolve: {
      jeuxEducatif: JeuxEducatifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.jeuxEducatif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JeuxEducatifUpdateComponent,
    resolve: {
      jeuxEducatif: JeuxEducatifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.jeuxEducatif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JeuxEducatifUpdateComponent,
    resolve: {
      jeuxEducatif: JeuxEducatifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.jeuxEducatif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
