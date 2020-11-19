# Downloader

A software that uses Java's Swing toolkit to download files using URLs while showing their progression.

## Actions

| Download Box Action | Mouse Input |
| ------------------- | ----------- |
| Context menu        | Right Click |

| Context Menu Action |
| ------------------- |
| Pause               |
| Resume              |
| Cancel              |
| Remove              |
| Clear all           |

| Text Field Action | Keyboard Input |
| ----------------- | -------------- |
| Add download      | Enter          |

## Layout

The frame uses a border layout with a scroll pane in its center and a horizontal split pane (`SplitPaneAddURL`) in its South.

The scroll pane uses a stack layout (provided) in which added downloads will appear as a JPanel (`DownloadBox`) with a border layout. In the JPanel's North, a label with the URL. In its East, a button to control the state of the download. And in its center, a progress bar showing the progression of the download.

The split pane contains a text field (`URLTextField`) on its left, in which the user enters the URL from which to download, and a button on its right, which adds a download using the given URL from the text field.

## Implementation

### Actions

Actions are instanciated in a static instance of Actions, allowing them to be used anywhere.

### Download Box

A `DownloadBox` is the graphical representation of a download. It is made of:

- A `JLabel` which gets the URL string from `URLListModel`;
- A `JProgressBar` which is updated through `DownloadPropertyChangeListener` by `Downloader`;
- And a `JButton` which has different actions depending on the download's `DownloadState` state. The button is updated through `DownloadStatePropertyChangeListener` by `DownloadWorker`.

### Download Manager

Creates and manages the state of all `DownloadWorker`s. Pauses and resumes a download using a `Downloader`'s `ReentrantLock`.

### Download Worker

Runs an instance of `Downloader` and inherites from `SwingWorker` so that each download is run on a separate thread.

### Downloader

Downlads a file from the given URL.

### URLs

URLs are stored in `URLListModel`, listened to by `PanelDownloads` and by each `DownloadBox` to manage the display of the stack layout. When a download is removed by the `DownloadManager`, the objects instanciated from both of the previous classes will be called through the `ListDataListener`.

### Notifications

A finished download brings the window to the front.
> On Windows, `toFront()` enables a flash notification on the application's icon in the taskbar if its window is not visible
