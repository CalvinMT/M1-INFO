# Grapher

A software that uses Java's Swing toolkit to visualise functions using a list/table and an interactive graph.

## Actions

| Action                       | Keyboard Shortcut |
| ---------------------------- | ----------------- |
| Add a function               | ---               |
| Edit a function              | ---               |
| Change a function's colour   | ---               |
| Remove one or more functions | Delete            |
| Undo actions                 | Ctrl + Z          |
| Redo actions                 | Ctrl + Shift + Z  |

> On a Mac, use the modifier `Command` key instead of `Ctrl` when using shortcuts

## Function View Modes

- List view
- Table view

## Implementation

### Actions

Actions are instanciated in a static instance of `Actions`, allowing them to be called anywhere.

### List & Table

The list uses `FunctionListModelFromTable` as its model which is populated by the table using `FunctionTableListener`.
When a selection is made in the list, the list transfers its selection to the table through `FunctionActionListeners`.
