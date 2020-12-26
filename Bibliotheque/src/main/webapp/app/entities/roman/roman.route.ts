import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRoman, Roman } from 'app/shared/model/roman.model';
import { RomanService } from './roman.service';
import { RomanComponent } from './roman.component';
import { RomanDetailComponent } from './roman-detail.component';
import { RomanUpdateComponent } from './roman-update.component';

@Injectable({ providedIn: 'root' })
export class RomanResolve implements Resolve<IRoman> {
  constructor(private service: RomanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoman> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((roman: HttpResponse<Roman>) => {
          if (roman.body) {
            return of(roman.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Roman());
  }
}

export const romanRoute: Routes = [
  {
    path: '',
    component: RomanComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.roman.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RomanDetailComponent,
    resolve: {
      roman: RomanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.roman.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RomanUpdateComponent,
    resolve: {
      roman: RomanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.roman.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RomanUpdateComponent,
    resolve: {
      roman: RomanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bibliothequeApp.roman.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
