**BudgeIt Archictecture**

BudgeIt is implemented using the 3-tier architecture pattern: UI layer,
Logic layer, Database layer and domain specific objects. We have also
added various custom exceptions.

**Domain Specific Objects**

There are two main domain specific objects: Entry and Category.

The Entry class stores data that makes up an entry: Amount, Details and
Date which are all DSOs as well. The Entry has been divided into 2
types: DefaultIncome and DefaultPurchase. All these classes implement
the Entry interface directly or indirectly. Each Entry type has its own
factory class that takes care of different ways by which entries can be
created. The EntryList DSO helps return a list of all entries in
different orders and its size.

The Category class stores data that makes up a category: Amount, Details
and DateModified which are all DSO's as well. The Category has been
divided into 2 types: DefaultSavingsCategory and DefaultBudgetCategory.
All these classes implement the Category interface directly or
indirectly. Each Category type has its own factory that takes care of
different ways by which categories can be created. The CategoryList DSO
helps return a list of all entries in different orders and its size.

**The Logic Layer**

We have added eight new interfaces to the logic layer in iteration 2.
The EntryFlagger takes either the id of an entry or the entry itself and
updates its flag value with the value of passed to it.

The IDManager manages all id's for all entries and categories created.

The UICalculator handles all calculations to be performed for example:
Total income entered over a certain period, Total purchases made over a
certain period, Total money turnover till date.

The UICategoryCreator creates new categories and stores them in the
database.

The UICategoryFetcher fetches all categories from the database. They can
be of a specific type or all of them.

The UICategoryModifier helps modify the category i.e: delete the
category, change its name or the goal(amount \$)

The UIEntryCategorizer takes an entry and adds it to the category that
has been specified.

The UIEntryManager deals with creating, modifying or deleting an entry
from the database.

**The UI Layer**

We have added 5 new classes in the UI layer for Iteration 2. The UI
layer works together with the logic layer and database to present

The MainActivity has two fragments: CategoriesFragment and
EntriesFragment. These are two different views in the main activity.

The CategoriesFragment fetches all the categories from the database with
the help of UICategoryFetcher in the logic layer and the CategoryAdapter
presents the categories in a list. NewCategoryActivity in the
CategoriesFragment allows the user to create new categories.

The EntriesFragment fetches all the entries from the database with the
help of UIEntryFetcher in the logic layer and the EntryAdpater presents
the entries in a list. NewEntryActivity in the EntriesFragment allows
the user to create new entries.

The EntryVisibility helps us present income or expenses or both on our
app.

**The Database Layer**

In this iteration, we have implemented a real database: SQLite. The Real
database implements the database interface which covers all queries made
from the UI layer or Logic layer or in the Database layer itself. The
database has three main tables: Entries, Categories and IDs. Each table
has its matching attributes with their implementation in logic layer or
as DSOs. Some of the database queries include: insertEntry, deleteEntry,
updateEntry, selectById, getIDCounter, updateIDCounter.

The DatabaseFactory class enables us to either implement the database as
a stub database or a real database. The DatabaseHolder makes sure the
only one database object has been initialized and is being passed around
the UI layer or Logic layer (The Singleton object).

**Exceptions**

We have implemented 12 different custom exceptions to provide the user
with the finest error message. The exceptions help detect errors across
all three layers of our application: UI layer, Logic layer and Database
layer.
