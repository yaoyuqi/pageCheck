package Ywk.Client.RequestBuilder;

import Ywk.Client.SearchPlatform;
import okhttp3.Request;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestBuilder {

    private List<AbstractBuilder> builders = Arrays.asList(
            new BaiduBuilder(),
            new ShenmaBuilder()
    );


    public RequestBuilder(List<SearchPlatform> platforms) {
        builders = builders.stream().peek(builder -> {
            Optional<SearchPlatform> searchPlatform = platforms.stream().filter(platform -> {
                try {
                    return platform.isSameHost(builder.host());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return false;
                }
            }).findFirst();
            builder.setPlatform(searchPlatform.orElse(null));
        })
                .filter(builder -> builder.getPlatform() != null)
                .collect(Collectors.toList());
    }

    public Request build(String url) {
        Optional<Request> request = builders.stream().filter(builder -> builder.canBuild(url))
                .map(builder -> builder.build(url))
                .findFirst();

        return request.orElse(null);

    }
}
