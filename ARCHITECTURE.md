# BudgeIt Architecture
BudgeIt is segmented into one domain-specific object: Entry; and three
"layers": the UI Layer, the Logic Layer, and the Database Layer.

![BudgeIt architecture diagram](budgeit_architecture.png)

## Entry DSO
The Entry class stores the data that makes up an entry: an amount in
cents, a description string, and a date.

## UI Layer
The UI layer has three main classes: MainActivity, NewEntryActivity and
DateRangeActivity.

MainActivity is the activity that gets started when the app is launched,
and it displays the main interface. It initializes a singleton LogicLayer
object, and uses that to request lists of entries.

NewEntryActivity gets started when the user taps the button to create a
new entry. It displays a form for the user to enter all of the information
that makes up an entry, and a button to submit the information. If the
user submits, it will make a request to the LogicLayer to create the entry
and finish the activity.

DateRangeActivity gets started when the user taps on a button to filter
entries by date. It displays a form with two fields: a start date and and
end date, and a button to submit them. If the user submits, it will pass
back the date range to the parent, (MainActivity) which will then use it to
fetch entries from the logic layer within that date range.

## Logic Layer
The Logic Layer has three main interfaces, the EntryFetcher, the
EntryCalculator, and the EntryCreator.

The EntryFetcher takes requests from the UI, given as strings, to fetch
list of specific entries. It parses the strings given to it into a
format that can be understood by the Database, then makes a query into
the Database. It processes the results of that query into the final
fetch results.

The EntryCalculator takes requests from the UI, given as strings, to get
meta data, such as the sum of the amounts of each entry, about groups of
entryies. It uses the EntryFetcher to first obtain these group, then it
makes some calculation with them.

The EntryCreator takes requests from the UI, given as strings, to create
a new entry. It first parses those strings into a form that Entry's
constructor requires and generates an id. It then creates a new entry
and stores it in the database.

## Database Layer
The Database Layer has one main interface: Database. Database stores and
updates a counter that is used by the Logic Layer to generate unique IDs
for the entries. It also stores the entries themselves. Queries can be
made by date range or id.
