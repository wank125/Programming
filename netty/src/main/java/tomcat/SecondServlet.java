package tomcat;

import java.io.IOException;

public class SecondServlet extends GPServlet {
    @Override
    public void doGet(GPRequest request, GPResponse response) throws IOException {
        this.doPost(request, response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) throws IOException {
        response.write("This is second Servlet");
    }
}
