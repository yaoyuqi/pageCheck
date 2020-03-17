package Ywk.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityWrapper {

    private static IdentityWrapper instance;
    private Map<String, IdentityData.DataBean> map = new HashMap<>();
    private boolean init = false;

    private IdentityWrapper() {
    }

    public static IdentityWrapper getInstance() {
        if (instance == null) {
            instance = new IdentityWrapper();
        }
        return instance;
    }

    public boolean isInit() {
        return init;
    }

    public void initList(List<IdentityData.DataBean> list) {
        for (IdentityData.DataBean item :
                list) {
            map.put(item.getIdentity(), item);
        }

        init = true;

    }

    public String name(String identity) {
        return map.get(identity).getName();
    }

    public List<String> identities() {
        return Arrays.asList(map.keySet().toArray(new String[0]));
    }
}
