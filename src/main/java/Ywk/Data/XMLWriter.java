package Ywk.Data;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class XMLWriter implements Writer {

    private static final int CHUNK_TO_WRITE_MAX = 1;
    private XStream xStream = new XStream();

    private BlockingDeque<Info> listPc = new LinkedBlockingDeque<>();
    private BlockingDeque<Info> listMobile = new LinkedBlockingDeque<>();

    private int filePC = 0;
    private int fileMobile = 0;

    public void add(Info info) {
        try {
            if (info.getType() == Info.TYPE_PC) {
                listPc.put(info);
            } else {
                listMobile.put(info);
            }

            if (isFull(Info.TYPE_PC)) {
                flush(Info.TYPE_PC);
            }

            if (isFull(Info.TYPE_MOBILE)) {
                flush(Info.TYPE_MOBILE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void add(String keyword, int type, String[] loc, String time) {
        Info info = new Info();
        info.setKeyword(keyword);
        info.setType(type);
        info.setTime(time);
        info.setLoc(loc);
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

}
