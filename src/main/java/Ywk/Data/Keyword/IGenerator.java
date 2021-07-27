package Ywk.Data.Keyword;

import java.util.Iterator;

public interface IGenerator extends Iterator<String> {
    int count();

    void clear();
}
