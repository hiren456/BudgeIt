# Logic Layer
The Logic Layer has three main classes, the EntryFetcher, the
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

# Storage Layer
The Database stores and updates a counter that is used by the Logic
Layer to generate unique id's for the entries. It also stores the
entries themselves. Queries can be made by date range or id.
