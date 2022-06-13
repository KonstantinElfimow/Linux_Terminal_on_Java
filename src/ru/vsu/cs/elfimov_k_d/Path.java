package ru.vsu.cs.elfimov_k_d;

class Path {
    static String[] setPath(String argOfPath) {
        if (argOfPath.trim().equals("/")) {
            return new String[]{""};
        }
        if (argOfPath.equals("")) {
            return new String[]{};
        }
        return argOfPath.split("/");
    }
}
