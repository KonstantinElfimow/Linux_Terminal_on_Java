package ru.vsu.cs.elfimov_k_d;

class MyVirtualFile {
    private StringBuilder content = new StringBuilder();
    private String name;

    MyVirtualFile(String name, String string) {
        this.name = name;
        content.append(string).append("\n");
    }

    void editing(String string, StreamingArgs.RedirectType redirectType) {
        switch (redirectType) {
            case REWRITE:
                content = new StringBuilder();
            case APPEND:
                content.append(string).append("\n");
                break;
        }
    }

    String getName() {
        return name;
    }
    String getContent() {
        return content.toString();
    }
}
