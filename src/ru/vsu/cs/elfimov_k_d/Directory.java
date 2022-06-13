package ru.vsu.cs.elfimov_k_d;

import java.util.ArrayList;
import java.util.List;

class Directory {
    private List<Directory> directoryList = new ArrayList<>();
    private List<MyVirtualFile> theFiles = new ArrayList<>();
    private Directory parent;
    private String name;

    Directory() {
        name = "File System Root";
        parent = this;
    }

    private Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    boolean mkdir(String dirName) {
        if (getDirByName(dirName, this) != null || getTextFileByName(dirName, this) != null) {
            return false;
        }
        Directory directory = new Directory(dirName, this);
        directoryList.add(directory);
        return true;
    }

    Directory cd(String[] path) {
        return searchingDir(path, this);
    }

    String ls(String[] path) {
        Directory readingDir = searchingDir(path, this);
        StringBuilder string = new StringBuilder();
        for (Directory directories : readingDir.directoryList) {
            string.append(directories.name).append("\t");
        }
        for (MyVirtualFile myVirtualFiles : readingDir.theFiles) {
            string.append(myVirtualFiles.getName()).append("\t");
        }
        string.append("\n");
        return string.toString();
    }

    void rm(String[] path) {
        Directory dir = searchingParentDir(path, this);
        String nameOfRemovingThing = path[path.length - 1];
        if (dir == null) {
            return;
        }
        MyVirtualFile removingFile = getTextFileByName(nameOfRemovingThing, dir);
        if (removingFile != null) {
            dir.theFiles.remove(removingFile);
            return;
        }
        Directory removingDirectory = getDirByName(nameOfRemovingThing, dir);
        if (removingDirectory != null) {
            dir.directoryList.remove(removingDirectory);
        }
    }

    String tree(String[] path) {
        Directory root = searchingDir(path, this);
        if (root == null) {
            return "";
        }
        return treeProcess(root.parent, "");
    }

    private String treeProcess(Directory current, String gap) {
        StringBuilder string = new StringBuilder(gap + current.name + "\n");
        if (gap.trim().equals("")) {
            gap = "|___";
        }
        for (Directory directories : current.directoryList) {
            string.append(treeProcess(directories, "\t" + gap));
        }
        for (MyVirtualFile files : current.theFiles) {
            string.append("\t").append(gap).append(files.getName()).append("\n");
        }
        return string.toString();
    }
    static MyVirtualFile searchingFile(String[] path, Directory directory) {
        return searchingFilePrivate(path, directory);
    }
    private static MyVirtualFile searchingFilePrivate(String[] path, Directory directory) {
        String nameOfFile = path[path.length - 1];
        Directory searching = searchingParentDir(path, directory);
        if (searching == null) {
            return null;
        }
        return getTextFileByName(nameOfFile, searching);
    }
    static void searchingAndEditingFile(StreamingArgs arguments, Directory directory, String string) {
        searchingAndEditingFilePrivate(arguments, directory, string);
    }
    private static void searchingAndEditingFilePrivate(StreamingArgs arguments, Directory directory, String string) {
        String[] redirectTo = arguments.getRedirectTo();
        StreamingArgs.RedirectType redirectType = arguments.getRedirectType();
        if (redirectType == StreamingArgs.RedirectType.NONE) {
            System.out.println(string);
            return;
        }
        Directory searching = searchingParentDir(redirectTo, directory);
        if (searching == null) {
            return;
        }
        String nameOfFile = redirectTo[redirectTo.length - 1];
        MyVirtualFile file = getTextFileByName(nameOfFile, searching);
        if (file != null) {
            file.editing(string, redirectType);
        } else {
            searching.theFiles.add(new MyVirtualFile(nameOfFile, string));
        }
    }

    private static Directory searchingDir(String[] path, Directory directory) {
        return findingDir(path, directory, 0, path.length != 0 ? path.length : 1, true);
    }

    private static Directory searchingParentDir(String[] path, Directory directory) {
        return findingDir(path, directory, 0, path.length - 1 != 0 ? path.length : 1, true);
    }

    private static Directory findingDir(String[] path, Directory current, int indexS, int indexL, boolean accessToMoveBack) {
        if (indexS < indexL) {
            if (indexS == 0 && path.length == 0) {
                return current;
            }
            if (path[indexS].trim().equals("..")) {
                if (accessToMoveBack) {
                    current = current.parent;
                } else {
                    return null;
                }
            } else if (path[indexS].equals("")) {
                while (current != current.parent) {
                    current = current.parent;
                }
            } else if (!path[indexS].trim().equals(".")) {
                current = getDirByName(path[indexS], current);
                accessToMoveBack = false;
                if (current == null) {
                    return null;
                }
            }
            current = findingDir(path, current, indexS + 1, indexL, accessToMoveBack);
        }
        return current;
    }

    private static Directory getDirByName(String name, Directory current) {
        for (Directory directories : current.directoryList) {
            if (directories.name.equals(name)) {
                return directories;
            }
        }
        return null;
    }

    private static MyVirtualFile getTextFileByName(String name, Directory current) {
        for (MyVirtualFile file : current.theFiles) {
            if (file.getName().equals(name)) {
                return file;
            }
        }
        return null;
    }
}
