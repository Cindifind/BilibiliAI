package online.afeibaili.map;

import lombok.Data;

@Data
public class Address {
    private int code;
    private String message;
    private int ttl;
    private DataInfo data;
}
