package tomcat;

import java.io.IOException;

public abstract class GPServlet {

    public void service(GPRequest request, GPResponse response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public abstract void doGet(GPRequest request, GPResponse response) throws IOException;

    public abstract void doPost(GPRequest request, GPResponse response) throws IOException;
}
