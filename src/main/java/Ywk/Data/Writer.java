package Ywk.Data;

public interface Writer {
    public void add(Info info);

    public void add(String keyword, int page, int type, String[] loc, String time);

    public boolean isFull(int type);

    public void flush(int type);

}
