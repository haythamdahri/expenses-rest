Goal
====
Produce a simple web-app backend to complement the supplied front-end code.

The task
--------------


Imagine that you come back from 2 weeks of holidays on a Monday. On the team Kanban board, assigned to you, two tasks await :


**User story 1:**

> **As a user, I want to be able to enter my expenses and have them saved for later.**

> _As a user, in the application UI, I can navigate to an expenses page. On this page, I can add an expense, setting:_

> 1. _The date of the expense_
> 0. _The value of the expense_
> 0. _The reason of the expense_

> _When I click "Save Expense", the expense is then saved in the database._
> _The new expense can then be seen in the list of submitted expenses._


**User story 2:**

> **As a user, I want to be able to see a list of my submitted expenses.**


> _As a user, in the application UI, I can navigate to an expenses page. On this page, I can see all the expenses I have already submitted in a tabulated list.
> On this list, I can see:_

> 1. _The date of the expense_
> 0. _The VAT (Value added tax) associated to this expense. VAT is the UK’s sales tax. It is 20% of the value of the expense, and is included in the amount entered by the user._
> 0. _The reason for the expense_
>

By email, the front end developer of the team let you know that she already worked on the stories,  built a UI and also went on holidays to France!

>_"Hi backEndDeveloper,_

>_Hope you had a nice holiday.
>I created a UI and prepared resource calls for those 2 user stories.
>You should only have to create the right endpoints in your service for the frontend application to call them, and everything should then work fine!...
>Unless I forgot something of course, in which case you may be able to reach me on the beach_
>
>_P.S. You can start the frontend by running `ng serve` this will compile the code and launch a webserver on `localhost:4200`. If you want to have a look at the code that is calling the endpoints then you can find this in `src/app/expenses.service.ts`_
>
>_P.P.S. In case you are stuck, you need to prep the project with `npm install -g @angular/cli && npm install`. I'll leave it to you how to get Node on your system ;-)_
>
>_A bientôt !_
>
> _Your Favourite FE Dev_
>"

Mandatory Work
--------------

Create a repository on GitHub and start with the provided bundle. For the provided webapp, create a Java-based REST API that:

1. Provides your solution to user story `1` and user story `2`
0. Create a README containing instructions on how to build and run your app.
0. Place your solution in the subdirectory provided

If you repo is public provide a link to it. If it's private ask for a github user that will be invited as a collaborator so your code can be accessed. Send us an email when you’re done. Feel free to ask questions if anything is unclear, confusing, or just plain missing.

Extra Credit
------------


_All the following work is optional. The described tasks do not need to be fully completed, nor do they need to be done in order.
You could chose to do the front-end part of a story, or the backend one, or only an endpoint of the backend one, as an example.
You could pick one to do completely or bits and pieces of all three, it is up to you, as long as you explain to us what you did (or didn't) choose to do._


You finished way in advance and can't wait to show your work at Wednesday's demo session. But you decide to impress the sales team a bit more and go back to the team Kanban board.
There you find some extra unassigned user stories:


**User story 3:**

> **As a user, I want to be able to save expenses in euros**

> _As a user, in the UI, when I write an expense, I can add the chars_ `EUR` _after it (example : 12,00 EUR).
> When this happens, the application automatically converts the entered value into pounds and saves this new value in the database.
The conversion needs to be accurate. It was decided that our application would call a public API to either realise the conversion or determine the right conversion rate, and then use it._

**User story 4:**

>**As a user, I want to see the VAT calculation update in real time as I enter my expenses**

> _After conversation with the dev team, we decided that the VAT should be calculated client-side as the user enters a new expense, before they save the expense to the database._
> _Robee being on holidays, Can I assign that to you backEndDeveloper?_


Questions
---------

##### What application servers can I use?
It’s totally up to you! Pick something you are good at. We use Dropwizard and we’re gradually moving to Spring.

##### What libraries can I use?
That’s again entirely up to you, as long as they’re Open Source Software (OSS). We’ll ask you to explain the choices you’ve made. Please pick something you're familiar with, as you'll need to be able to discuss it.

##### What database should I use?
Please use MySQL or PostgreSQL. 

##### What will you be grading me on?
Elegance, robustness, understanding of the technologies you use, tests, security.

##### Will I have a chance to explain my choices?
Feel free to comment your code, or put explanations in a pull request within the repo. If we proceed to a phone interview, we’ll be asking questions about why you made the choices you made.

##### Why doesn’t the test include X?
Good question. Feel free to tell us how to make the test better. Or, you know, fork it and improve it!
