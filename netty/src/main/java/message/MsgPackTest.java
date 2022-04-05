package message;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MsgPackTest {
    public static void main(String[] args) throws IOException {
        ArrayList<String> src = new ArrayList<>();
        src.add("aa");
        src.add("bb");
        src.add("dd");

        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(src);

        List<String> read = messagePack.read(raw, Templates.tList(Templates.TString));
        System.out.println(read);
    }
}
