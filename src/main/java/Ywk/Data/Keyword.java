package Ywk.Data;

public class Keyword {

    private static final int CHUNK_NUMBER = 100;
    private MixType type = MixType.PREFIX_MAIN_SUFFIX;
    private String[] custom = new String[]{};
    private String[] prefix = new String[]{};
    ;
    private String[] main = new String[]{};
    ;
    private String[] suffix = new String[]{};
    ;
    private int maxRun = 0;

    private int curPrefix = -1;
    private int curMain = -1;
    private int curSuffix = -1;
    private int curRun = 0;


    public void setMaxRun(int maxRun) {
        this.maxRun = maxRun;
    }

    public void setCurRun(int curRun) {
        this.curRun = curRun;
    }

    public int calculateChunkNumber() {
        int total = getTotal();
        if (total > 100000) {
            return 1000;
        }
        else if (total > 10000) {
            return 500;
        }
        else if (total > 1000) {
            return 100;
        }
        else {
            return 50;
        }
    }

    public synchronized String next() {
        if (isFinished() || isMax()) {
            System.out.println("keyword next finished");
            return null;
        }

        String out = "";

        switch (type) {
            case MAIN:
                curMain++;
                if (curMain != -1 && curMain < main.length) {
                    out += main[curMain];
                }
                break;
            case PREFIX_MAIN:
                curMain++;
                if (curMain == main.length) {
                    curMain = -1;
                    curPrefix++;
                }
                if (curPrefix != -1 && curPrefix < prefix.length) {
                    out += prefix[curPrefix];
                }
                if (curMain != -1 && curMain < main.length) {
                    out += main[curMain];
                }
                break;
            case PREFIX_MAIN_SUFFIX:
                curSuffix++;

                if (curSuffix == suffix.length) {
                    curSuffix = -1;
                    curMain++;
                }

                if (curMain == main.length) {
                    curMain = -1;
                    curPrefix++;
                }


                if (curPrefix != -1 && curPrefix < prefix.length) {
                    out += prefix[curPrefix];
                }

                if (curMain != -1 && curMain < main.length) {
                    out += main[curMain];
                }

                if (curSuffix != -1 && curSuffix < suffix.length) {
                    out += suffix[curSuffix];
                }
                break;
            case MAIN_SUFFIX:
                curSuffix++;

                if (curSuffix == suffix.length) {
                    curSuffix = -1;
                    curMain++;
                }
                if (curMain != -1 && curMain < main.length) {
                    out += main[curMain];
                }

                if (curSuffix != -1 && curSuffix < suffix.length) {
                    out += suffix[curSuffix];
                }
                break;
            case CUSTOM:
                if (curSuffix < custom.length) {
                    out = custom[curRun];

                }
                break;
        }
        curRun++;

        return out;


    }


    public void setWords(String[] prefix, String[] main, String[] suffix) {
        this.prefix = prefix;
        this.main = main;
        this.suffix = suffix;
    }

    public void setCurrent(int curPrefix, int curMain, int curSuffix) {
        this.curPrefix = curPrefix;
        this.curMain = curMain;
        this.curSuffix = curSuffix;
    }

    private boolean isFinished() {
        switch (type) {
            case MAIN:
                return curMain == main.length;
            case PREFIX_MAIN:
                return curMain == main.length - 1
                        && curPrefix == prefix.length - 1;
            case PREFIX_MAIN_SUFFIX:
                return curMain == main.length - 1
                        && curPrefix == prefix.length - 1
                        && curSuffix == suffix.length - 1;
            case MAIN_SUFFIX:
                return curMain == main.length - 1
                        && curSuffix == suffix.length - 1;
            case CUSTOM:
                return curRun == custom.length;
            default:
                return true;
        }
    }

    public synchronized String[] nextChunk() {
        if (isFinished()) {
            return null;
        }

        int chunkNumber = 0;
        if (!isMax()) {
            if (maxRun == 0) {
                chunkNumber = calculateChunkNumber();
            } else {
                if (maxRun - curRun < calculateChunkNumber()) {
                    chunkNumber = maxRun - curRun;
                } else {
                    chunkNumber = calculateChunkNumber();
                }
            }
        }

        if (chunkNumber > 0) {
            String[] words = new String[chunkNumber];
            for (int i = 0; i < chunkNumber; i++) {
                String next = next();
                if (next != null) {
                    words[i] = next;
                } else {
                    break;
                }
            }
            return words;
        }

        return null;


    }

    public void setType(MixType type) {
        this.type = type;
    }

    public void setCustom(String[] custom) {
        this.custom = custom;
    }

    public int getTotal() {
        if (type != MixType.CUSTOM && (prefix.length == 0 && main.length == 0 && suffix.length == 0)) {
            return 0;
        }

        int total = 0;

        switch (type) {
            case MAIN:
                total = main.length;
                break;
            case PREFIX_MAIN_SUFFIX:
                total = (prefix.length + 1) * (main.length + 1) * (suffix.length + 1) - 1;
                break;
            case MAIN_SUFFIX:
                total = (main.length + 1) * (suffix.length + 1) - 1;
                break;
            case PREFIX_MAIN:
                total = (prefix.length + 1) * (main.length + 1) - 1;
                break;
            case CUSTOM:
                total = custom.length;
                break;
        }
        return total;
    }

    private boolean isMax() {
        if (maxRun == 0) {
            return false;
        } else {
            return curRun >= maxRun;
        }
    }

    public enum MixType {
        PREFIX_MAIN_SUFFIX,
        PREFIX_MAIN,
        MAIN,
        MAIN_SUFFIX,
        CUSTOM
    }

}
