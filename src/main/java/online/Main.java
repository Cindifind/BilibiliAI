package online;

import client.tool.BiliBiliTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.map.Address;
import online.map.Datacenter;
import online.map.Package;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class Main {
    //JSON处理工具
    public static final ObjectMapper JSON = new ObjectMapper();

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        int RoomId = 000;
        String cookie = "bilibliCookie";
        //使用Util.getRoomAddressJson()方法获取地址，并使用readValue方法将JSON转成Address对象
        Client client = new Client(RoomId, cookie);
    }
}
