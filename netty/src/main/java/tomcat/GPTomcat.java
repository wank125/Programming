package tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, GPServlet> servletMapping = new HashMap<>();
    private Properties webxml = new Properties();

    private void init() {
        try {
            String WEB_INI = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INI + "web.properties");
            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        init();
        server = new ServerSocket(this.port);
        System.out.println("服务已经启动,端口号 : " + this.port);
        while (true) {
            Socket client = server.accept();
            process(client);
        }
    }

    private void process(Socket client) throws IOException {
        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();

        GPRequest gpRequest = new GPRequest(in);
        GPResponse gpResponse = new GPResponse(out);

        String url = gpRequest.getUrl();
        if (servletMapping.containsKey(url)) {
            servletMapping.get(url).service(gpRequest, gpResponse);
        } else {
            gpResponse.write("404-Not Found");
        }

        out.flush();
        out.close();
        in.close();
        client.close();
    }

    public static void main(String[] args) throws IOException {
        new GPTomcat().start();
    }
}
