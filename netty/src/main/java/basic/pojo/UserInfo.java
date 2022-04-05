package basic.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int age;
    private String name;
}
