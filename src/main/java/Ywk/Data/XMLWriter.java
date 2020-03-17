package Ywk.Data;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLWriter implements Writer {

    static final int CHUNK_TO_WRITE_MAX = 3000;
    protected List<Info> list = new ArrayList<>();
    private XStream xStream = new XStream();
    private int fileCnt = 0;


    public synchronized void add(Info info) {
        list.add(info);

        if (isFull()) {
            flush();
        }

    }


    public boolean isFull() {
        return list.size() >= CHUNK_TO_WRITE_MAX;
    }

    public synchronized void flush() {
        try {

            if (!list.isEmpty()) {
                fileCnt++;
                String name = "result_" + fileCnt + ".xml";
                OutputStream out = new FileOutputStream(name);
                xStream.toXML(list, out);
                list.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        list.clear();
    }

}
