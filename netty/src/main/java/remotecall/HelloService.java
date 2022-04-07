package remotecall;

import java.util.Date;

public interface HelloService {

    public String echo(String msg);

    public Date getTime();

    public String toString();
}
