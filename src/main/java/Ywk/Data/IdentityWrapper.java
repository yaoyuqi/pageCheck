package Ywk.Data;

import Ywk.Api.ApiInstance;
import Ywk.Api.ApiStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityWrapper implements ApiInstance {

    private static IdentityWrapper instance;
    private Map<String, IdentityData.DataBean> map = new HashMap<>();
    private ApiStatus initStatus = ApiStatus.WAITING;


    private IdentityWrapper() {
    }

    public static IdentityWrapper getInstance() {
        if (instance == null) {
            instance = new IdentityWrapper();
        }
        return instance;
    }

    public void initList(List<IdentityData.DataBean> list) {
        for (IdentityData.DataBean item :
                list) {
            map.put(item.getIdentity(), item);
        }

        initStatus = ApiStatus.SUCCESS;

    }

    public String name(String identity) {
        return map.get(identity).getName();
    }

    public List<String> identities() {
        return Arrays.asList(map.keySet().toArray(new String[0]));
    }

    @Override
    public ApiStatus inited() {
        return initStatus;
    }

    @Override
    public void initFailed() {
        initStatus = ApiStatus.FAILED;
    }
}
