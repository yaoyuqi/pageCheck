package Ywk.Data;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLWriter implements Writer {

    static final int CHUNK_TO_WRITE_MAX = 3;
    protected List<Info> listPc = new ArrayList<>();
    protected List<Info> listMobile = new ArrayList<>();
    private XStream xStream = new XStream();
    private int filePC = 0;
    private int fileMobile = 0;

    public synchronized void add(Info info) {
        if (info.getType() == Info.TYPE_PC) {
            listPc.add(info);
        } else {
            listMobile.add(info);
        }

        if (isFull(Info.TYPE_PC)) {
            flush(Info.TYPE_PC);
        }

        if (isFull(Info.TYPE_MOBILE)) {
            flush(Info.TYPE_MOBILE);
        }

    }

    public synchronized void add(String keyword, int page, int type, String[] loc, String time) {
        Info info = new Info();
        info.setKeyword(keyword);
        info.setType(type);
        info.setTime(time);
        info.setLoc(loc);
        info.setPage(page);
        add(info);
    }

    public boolean isFull(int type) {
        if (type == Info.TYPE_PC) {
            return listPc.size() >= CHUNK_TO_WRITE_MAX;
        } else {
            return listMobile.size() >= CHUNK_TO_WRITE_MAX;
        }
    }

    public synchronized void flush(int type) {
        try {
            if (type == Info.TYPE_PC && !listPc.isEmpty()) {

                filePC++;
                String name = "pc_" + filePC + ".xml";

                OutputStream out = new FileOutputStream(name);
                xStream.toXML(listPc, out);
                listPc.clear();
            } else {
                if (!listMobile.isEmpty()) {
                    fileMobile++;
                    String name = "mobile_" + fileMobile + ".xml";

                    OutputStream out = new FileOutputStream(name);
                    xStream.toXML(listMobile, out);
                    listMobile.clear();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        listMobile.clear();
        listPc.clear();
    }

}
