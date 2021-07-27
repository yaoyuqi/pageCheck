package Ywk.Data.Keyword;

import static com.google.common.base.Preconditions.checkNotNull;

public class SingleGenerator implements IGenerator {
    private final String[] words;
    private int idx = 0;

    public SingleGenerator(String[] words) {
        checkNotNull(words);
        this.words = words;
    }

    @Override
    public int count() {
//        if (words.length == 1 && words[0].isEmpty()) {
//            return 0;
//        }
        return words.length;
    }

    @Override
    public void clear() {
        idx = 0;
    }

    @Override
    public boolean hasNext() {
        return idx < count();
    }

    @Override
    public String next() {
        return words[idx++];
    }
}
