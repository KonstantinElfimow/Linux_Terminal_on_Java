package ru.vsu.cs.elfimov_k_d;

class Console {
    private Directory fsRoot = new Directory();
    private Directory current = fsRoot;

    void checkCases(StreamingArgs args) {
        TreatmentArgsFromConsole commons = new TreatmentArgsFromConsole(current, args);
        switch (args.getCommand()) {
            case "mkdir":
                commons.mkdir();
                break;
            case "cd":
                current = commons.cd();
                break;
            case "ls":
                commons.ls();
                break;
            case "rm":
                commons.rm();
                break;
            case "echo":
                commons.echo();
                break;
            case "cat":
                commons.cat();
                break;
            case "tree":
                commons.tree();
                break;
            case "exit":
                commons.exit();
                break;
            default:
                System.err.println("Enter one of this commands: mkdir, cd, ls, rm, echo, cat, tree, exit");
                break;
        }
    }
}
