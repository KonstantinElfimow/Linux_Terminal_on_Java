package ru.vsu.cs.elfimov_k_d;

import java.util.ArrayList;
import java.util.List;

class StreamingArgs {
    private String command;
    private String[] arguments;
    private RedirectType redirectType;
    private String[] redirectTo = null;

    StreamingArgs(String line) {
        prepareArgs(line);
    }

    String getCommand() {
        return command;
    }

    String[] getArguments() {
        return arguments;
    }

    int getArgumentCount() {
        return arguments.length;
    }

    String[] getRedirectTo() {
        return redirectTo;
    }

    enum RedirectType {
        NONE, REWRITE, APPEND
    }

    RedirectType getRedirectType() {
        return redirectType;
    }

    private void prepareArgs(String line) {
        String[] array = line.split(String.valueOf((char) 34));
        List<String> listOfArgs = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String value = array[i];
            if (i % 2 == 1) {
                listOfArgs.add(value);
                continue;
            }
            String[] parse = value.trim().split("\\s");
            for (String arg : parse) {
                if (arg.trim().equals("")) {
                    continue;
                }
                listOfArgs.add(arg);
            }
        }
        valueOfArgs(listOfArgs);
    }

    private void valueOfArgs(List<String> listOfArgs) {
        command = listOfArgs.get(0);
        arguments = new String[listOfArgs.size() - 1];
        for (int i = 1; i < listOfArgs.size(); i++) {
            arguments[i - 1] = listOfArgs.get(i);
        }
        if (arguments.length == 0) {
            arguments = new String[]{""};
        }
    }

    void inputStreams() {
        if (getArgumentCount() == 3) {
            switch (arguments[1].trim()) {
                case ">>":
                    redirectType = RedirectType.REWRITE;
                    break;
                case ">":
                    redirectType = RedirectType.APPEND;
                    break;
                default:
                    redirectType = RedirectType.NONE;
                    break;
            }
            redirectTo = Path.setPath(arguments[2]);
        } else {
            redirectType = RedirectType.NONE;
        }
    }
}
