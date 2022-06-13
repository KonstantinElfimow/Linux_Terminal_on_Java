package ru.vsu.cs.elfimov_k_d;

class TreatmentArgsFromConsole {
    private Directory current;
    private StreamingArgs args;

    TreatmentArgsFromConsole(Directory current, StreamingArgs args) {
        this.current = current;
        this.args = args;
    }

    void mkdir () {
        for (String arg : args.getArguments()) {
            String[] path =  Path.setPath(arg);
            if (path.length == 1) {
                String name = path[0];
                if (!current.mkdir(name)) {
                    System.out.println("> Found busy name: " + name);
                }
            }
        }
    }

    Directory cd () {
        if (args.getArgumentCount() == 1) {
            String[] path = Path.setPath(args.getArguments()[0]);
            Directory searching = current.cd(path);
            if (searching != null) {
                current = searching;
            }
        }
        return current;
    }

    void ls () {
        String string;
        String[] path = Path.setPath(args.getArguments()[0]);
        string = current.ls(path);
        args.inputStreams();
        Directory.searchingAndEditingFile(args, current, string);
    }

    void rm () {
        if (this.args.getArgumentCount()  == 1) {
            String[] path = Path.setPath(args.getArguments()[0]);
            current.rm(path);
        }
    }

    void echo () {
        if (this.args.getArgumentCount() == 1 || this.args.getArgumentCount() == 3) {
            String string = args.getArguments()[0];
            args.inputStreams();
            Directory.searchingAndEditingFile(args, current, string);
        }
    }

    void cat () {
        if (this.args.getArgumentCount() == 1 || this.args.getArgumentCount() == 3) {
            String[] path = Path.setPath(args.getArguments()[0]);
            MyVirtualFile file = Directory.searchingFile(path, current);
            if (file == null) {
                String fileName = path[path.length - 1];
                System.out.println("> No such file: " + fileName);
                return;
            }
            String string = file.getContent();
            args.inputStreams();
            Directory.searchingAndEditingFile(args, current, string);
        }
    }

    void tree () {
        if (this.args.getArgumentCount() == 1 || this.args.getArgumentCount() == 3) {
            String[] path = Path.setPath(args.getArguments()[0]);
            String string = current.tree(path);
            args.inputStreams();
            Directory.searchingAndEditingFile(args, current, string);
        }
    }

    void exit () {
        if (!(this.args.getArgumentCount() == 1 && args.getCommand().equals("exit"))) {
            System.err.println("Command error");
            return;
        }
        System.exit(0);
    }
}
