import { Component, OnDestroy, OnInit } from '@angular/core';
import { Expense } from './expense';
import { ExpenseService } from '../services/expense.service';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.less'],
})
export class ExpensesComponent implements OnInit, OnDestroy {
  newExpense: Expense = {};
  expenses: Expense[];
  expensesSubscription: Subscription;
  expenseSubscription: Subscription;
  expenseAMountSubscription: Subscription;

  constructor(
    private expenseService: ExpenseService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadExpenses();
  }

  ngOnDestroy(): void {
    if (this.expensesSubscription != null) {
      this.expensesSubscription.unsubscribe();
    }
    if (this.expenseSubscription != null) {
      this.expenseSubscription.unsubscribe();
    }
    if (this.expenseAMountSubscription != null) {
      this.expenseAMountSubscription.unsubscribe();
    }
  }

  private loadExpenses(): void {
    this.expensesSubscription = this.expenseService.getExpenses().subscribe(
      (expenses) => {
        this.expenses = expenses;
      },
      (err) => {}
    );
  }

  saveExpense(): void {
    const amount = this.newExpense.amount.toString().trim().split(' ');
    // Check if currency is present
    if (amount.length == 2) {
      this.newExpense.amount = parseFloat(amount[0]);
      this.newExpense.currency = amount[1];
    }
    console.log(this.newExpense)
    this.expenseSubscription = this.expenseService
      .createExpense(this.newExpense)
      .subscribe(
        () => {
          this.clearExpense();
          this.loadExpenses();
        },
        (err) => {
          console.log(err);
        }
      );
  }

  clearExpense(): void {
    this.newExpense = {};
  }

  calculateVatAmount() {
    if (this.isNumeric(this.newExpense?.amount)) {
      this.expenseAMountSubscription = this.expenseService
        .calculateVatAmount(this.newExpense.amount)
        .subscribe((response) => {
          this.newExpense.vat = response.amount;
        });
    }
  }

  isNumeric(str: number) {
    if (typeof str != 'string') return false; // we only process strings!
    return (
      !isNaN(str) && // use type to parse the str of the string (`parseFloat` alone does not do this)
      !isNaN(parseFloat(str))
    ); // ...and ensure strings of whitespace fail
  }

  signOut(): void {
    this.authService.logout();
    // Rediredect to login
    this.router.navigateByUrl('/login');
  }
}
