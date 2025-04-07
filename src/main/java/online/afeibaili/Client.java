package online.afeibaili;

import online.afeibaili.map.Address;
import online.afeibaili.map.Datacenter;
import online.afeibaili.map.Package;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static online.afeibaili.Main.JSON;

public class Client {
    public Client(int roomId, String cookie) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id=" + roomId + "&type=0";
       //使用Util.getRoomAddressJson()方法获取地址，并使用readValue方法将JSON转成Address对象
        Address address = JSON.readValue(Util.getRoomAddressJson(url, roomId, cookie), Address.class);
        //获取地址主要信息
        int port = address.getData().getHostList().get(0).getWssPort();
        String host = address.getData().getHostList().get(0).getHost();
        String token = address.getData().getToken();
        Map<String, String> map = Datacenter.getCookieMap(cookie);
        //连接并发送心跳包
        URI uri = new URI("wss://" + host + ":" + port + "/sub");
        Package pkg = new Package(
                Integer.parseInt(map.get("DedeUserID")),
                roomId,
                2,
                "buvid3="+map.get("buvid3"),
                "web",
                2,
                token);
        //创建鉴权包
        byte[] dataPackage = Package.createDataPackage(7, JSON.writeValueAsString(pkg).getBytes(StandardCharsets.UTF_8));
        //创建客户端
        WebSocketClient client = new WebSocketClient(uri, dataPackage);
        client.connect();
    }
}
