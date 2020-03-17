package Ywk.Data;

public interface Writer {
    public void add(Info info);

    public boolean isFull();

    public void flush();

}
