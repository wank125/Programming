package tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class GPRequest {
    private String method;
    private String url;

//    public GPRequest(InputStream in) {
//        try {
//            String content = "";
//            byte[] buff = new byte[1024];
//            int len = 0;
//            if ((len = in.read(buff)) > 0) {
//                content = new String(buff, 0, len);
//            }
//            String line = content.split("\\n")[0];
//            String[] arr = line.split("\\s");
//            this.method = arr[0];
//            this.url = arr[1].split("\\?")[0];
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public String getUrl() {
//        return url;
//    }

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public GPRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getUrl() {
        return this.request.uri();
    }

    public String getMethod() {
        return this.request.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> parameters = getParameters();
        List<String> param = parameters.get(name);
        if (null == param) {
            return null;
        } else {
            return param.get(0);
        }
    }
}
