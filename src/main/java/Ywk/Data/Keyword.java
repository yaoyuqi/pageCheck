package Ywk.Data;

public class Keyword {

    private static final int CHUNK_NUMBER = 100;
    private final String[] prefix;
    private final String[] main;
    private final String[] suffix;

    private int curPrefix = -1;
    private int curMain = -1;
    private int curSuffix = -1;

    public Keyword(String[] prefix, String[] main, String[] suffix) {
        this.prefix = prefix;
        this.main = main;
        this.suffix = suffix;
    }

    public void setCurrent(int curPrefix, int curMain, int curSuffix) {
        this.curPrefix = curPrefix;
        this.curMain = curMain;
        this.curSuffix = curSuffix;
    }


    public synchronized String next() {
        if (isFinished()) {
            return null;
        }

        curSuffix++;

        if (curSuffix == suffix.length) {
            curSuffix = -1;
            curMain++;
        }

        if (curMain == main.length) {
            curMain = -1;
            curPrefix++;
        }

        String out = "";
        if (curPrefix != -1 && curPrefix < prefix.length) {
            out += prefix[curPrefix];
        }

        if (curMain != -1 && curMain < main.length) {
            out += main[curMain];
        }

        if (curSuffix != -1 && curSuffix < suffix.length) {
            out += suffix[curSuffix];
        }

        return out;

    }

    private boolean isFinished() {
        return curMain == main.length - 1
                && curPrefix == prefix.length - 1
                && curSuffix == suffix.length - 1;
    }

    public synchronized String[] nextChunk() {
        if (isFinished()) {
            return null;
        }

        String[] words = new String[CHUNK_NUMBER];
        for (int i = 0; i < CHUNK_NUMBER; i++) {
            String next = next();
            if (next != null) {
                words[i] = next;
            } else {
                break;
            }
        }
        return words;
    }

    public int getTotal() {
        if (prefix.length == 0 && main.length == 0 && suffix.length == 0) {
            return 0;
        }
        return (prefix.length + 1) * (main.length + 1) * (suffix.length + 1) - 1;
    }

}
