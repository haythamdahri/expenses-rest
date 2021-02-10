import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExpensesComponent } from './expenses/expenses.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './services/auth/auth-guard.service';
import { AuthenticatedGuard } from './services/auth/authenticated-guard.service';

const routes: Routes = [
  {
    path: '',
    component: ExpensesComponent,
    canActivate: [AuthGuard],
    children: [{ path: '', component: ExpensesComponent }],
  },
  {
      path: 'login', component: LoginComponent, canActivate: [AuthenticatedGuard]
  }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
