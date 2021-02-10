import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense } from '../expenses/expense';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  private expensesUrl = `${environment.api}/api/v1/expenses`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.expensesUrl}/users/current`);
  }

  calculateVatAmount(amount: number): Observable<{amount: number}> {
    return this.http.get<{amount: number}>(`${this.expensesUrl}/vat`, {params: new HttpParams().append('amount', amount?.toString())});
  }

  createExpense(expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(`${this.expensesUrl}/`, expense, this.httpOptions);
  }
}
