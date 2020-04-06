**BudgeIt Archictecture**

BudgeIt is implemented using the 3-tier architecture pattern: UI layer, Logic layer, Database layer and domain specific objects. We have done lots of tests: unit tests, integration tests and acceptance tests.

**Domain Specific Objects**

There are two main domain specific objects: Entry and Category.

We have refactored the Entry DSO since we have implemented a new type of entry "RecurringEntries".The Entry still stores data that makes up an entry: Amount, Details and Date which are all DSOs as well. The Entry has now been divided into 2 types: DefaultEntry and DefaultRecurringEntry. These two types of entries can be implemented either as Purchase or Income by extending their implementation to BaseIncome and BasePurchase.
All these classes implement the BaseEntry interface directly or indirectly. The DefaultRecurringEntry consists of a RecurrencePeriod(a DSO as well) which enbales an Entry to be recurring. The RecurrencePeriod consists of the frequency in years/months/weeks/days that an Entry should recur. The EntryList DSO helps return a list of all entries in different orders and its size.

The Category class stores data that makes up a category: Amount, Details and DateModified which are all DSO's as well. The Category has been divided into 2 types: DefaultSavingsCategory and DefaultBudgetCategory. All these classes implement the Category interface directly or indirectly. Each Category type has its own factory that takes care of different ways by which categories can be created. The CategoryList DSO helps return a list of all entries in different orders and its size.

**The Logic Layer**

We have added four new interfaces to the logic layer in iteration 3.

The UIRecurringEntryManager creates new recurring entries, checks for recurring entries that will be due and schedule them to be posted. It also uses DateSource that gives detailed time upto milliseconds at the moment.

The UICategoryColourizer and UIEntryColourizer gives the visual effects that helps indicate whether its on the good side(wrt to the budget set by the user) or the bad side. It gets the colour of the amount field and the colour of the monthly progress bar.

The UIEntryBuilder builds a basic entry and adds any modifications to it, if needed e.g make it a recurring entry.

**The UI Layer**

We have added several new classes in the UI layer for Iteration 3.
The UI layer works together with the logic layer and database to provide a good working graphical user interface.

Each field on the user interface i.e: entry date, entry amount, entry description, category amount, category description has its own class that handles the data entered by the user and passes it to the logic layer and the database as well.

**The Database Layer**

In this iteration, we are still implementing a real database: SQLite.
The Real database implements the database interface which covers all queries made from the UI layer or Logic layer or in the Database layer itself. The database has five main tables: DefaultEntries, RecurringEntries, DateLastChecked, Categories and IDs. Each table has its matching attributes with their implementation in logic layer or as DSOs. Some of the database queries include: insertEntry, deleteEntry, getRecurringEntries, getDateLastChecked, updateEntry, selectById, getIDCounter, updateIDCounter.

The DatabaseFactory class enables us to either implement the database as a stub database or a real database(implemented using BudApplication class) or a test database. The DatabaseHolder makes sure the only one database object has been initialized and is being passed around the UI layer or Logic layer (The Singleton object).

**Tests**

Each UI element, logic layer element and database function has its own unit test. We have also implemented several integration tests to ensure there are no faults between integrated units. In addition to that we have implemented acceptance tests to meet the system's compliance with the business requirements and deliver a good, working product.
