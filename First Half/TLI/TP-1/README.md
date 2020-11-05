# Grapher

A software that uses Java's Swing toolkit to visualise functions using a list/table and an interactive graph.

## Function Actions

| List Action                        | Mouse Input        |
| ---------------------------------- | ------------------ |
| Select/Deselect a function         | Left Click         |
| Select/Deselect multiple functions | Ctrl + Left Click  |
| "                                  | Shift + Left Click |

| Table Action                        | Mouse Input        |
| ----------------------------------- | ------------------ |
| Select/Deselect a function          | Left Click         |
| Select/Deselect multiple functions  | Left Drag          |
| "                                   | Ctrl + Left Click  |
| "                                   | Shift + Left Click |
| Change a selected function's colour | Right Click        |

| Menu Action                  | Keyboard Shortcut |
| ---------------------------- | ----------------- |
| Add a function               | ---               |
| Edit a function              | ---               |
| Change a function's colour   | ---               |
| Remove one or more functions | Delete            |
| Undo actions                 | Ctrl + Z          |
| Redo actions                 | Ctrl + Shift + Z  |

> On a Mac, use the modifier `Command` key instead of `Ctrl` when using shortcuts

## Graph Interaction

| Interaction | Mouse Input |
| ----------- | ----------- |
| Move        | Left Drag   |
| Zoom In     | Left Click  |
| "           | Scroll Up   |
| "           | Right Drag  |
| Zoom Out    | Right Click |
| "           | Scroll Down |

## Function View Modes

- List view
- Table view

## Implementation

### Actions

Actions are instanciated in a static instance of `Actions`, allowing them to be called anywhere.

### Graph

...

### List & Table

The list uses `FunctionListModelFromTable` as its model which is populated by the table using `FunctionTableListener`.
When a selection is made in the list, the list transfers its selection to the table through `FunctionActionListeners`.
