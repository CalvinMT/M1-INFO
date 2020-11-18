# Downloader

A software that uses Java's Swing toolkit to download files using URLs while showing their progression.

## Download Actions

| Action | Mouse Input |
| ------ | ----------- |
| Context menu | Right Click |

| Context Menu Action |
| ------------------- |
| Pause               |
| Resume              |
| Cancel              |
| Remove              |
| Clear all           |

## Layout

The frame uses a border layout with a scroll pane in its center and a horizontal split pane in its south.

The scroll pane ... stacklayout (provided)

The split pane contains a text field on its left, in which the user enters the URL from which to download, and a button on its right, which adds a download using the given URL from the text field.

## Implementation

...
