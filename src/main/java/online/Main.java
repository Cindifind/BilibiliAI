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
        int RoomId = 7058884;
        String url = BiliBiliTool.buildDanmuInfoUrl(RoomId);
        String cookie = "_uuid=6A55A153-32B3-9C10C-EA8A-10F210FE82101051027159infoc; buvid3=C974659F-B44A-1DCF-1338-49F553E698D828333infoc; b_nut=1744986128; LIVE_BUVID=AUTO9317449861723981; header_theme_version=CLOSE; enable_web_push=DISABLE; enable_feed_channel=ENABLE; home_feed_column=5; browser_resolution=1659-856; buvid4=C3131B70-136E-964B-4BCA-043379C654EA02256-024060817-FkQzfkCfnDsTzG0Nw2V3hQ%3D%3D; fingerprint=adc6a753f2836252b03a31cee5268821; buvid_fp_plain=undefined; rpdid=|(u))l)Jm|RR0J'u~Rl)Jk~|l; DedeUserID=437366177; DedeUserID__ckMd5=7e72316470ed821c; buvid_fp=adc6a753f2836252b03a31cee5268821; hit-dyn-v2=1; b_lsid=3D108AD7E_197D5D23CC0; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NTE4OTgyMTUsImlhdCI6MTc1MTYzODk1NSwicGx0IjotMX0.ADyrf-XiKBCjZvHZpaYUDIkbraRi4hSi8fPEO7NN-Uw; bili_ticket_expires=1751898155; SESSDATA=59cf3548%2C1767191015%2C1106d%2A72CjCdVWZVG-sKlOSrepMbwIJCsSMEWAOvNpNISYFRaAD9vBUPkf0HuG6k3c75a3gSUKASVldTaVBkVWNjUHdiX2RBTWtaS3J5cjRheEtEZ1JCWG1TN29fZTdQQmVwa1RyeXFpemtmbW9fa1dJYVJtV0g0TXJPN1NPTmk3aGk1SnNtaEtyYzZGNUl3IIEC; bili_jct=e4746711061b1c90e5b381501fa28ac6; sid=4tyss5l0; CURRENT_FNVAL=2000; bp_t_offset_437366177=1085770459733032960; GIFT_BLOCK_COOKIE=GIFT_BLOCK_COOKIE; LIVE_ROOM_ADMIN_POP_TIP=1; theme_style=dark; timeMachine=0; PVID=4";
        //使用Util.getRoomAddressJson()方法获取地址，并使用readValue方法将JSON转成Address对象
        Address address = JSON.readValue(Util.getRoomAddressJson(url, RoomId, cookie), Address.class);
        //获取地址主要信息
        int port = address.getData().getHostList().get(0).getWssPort();
        String host = address.getData().getHostList().get(0).getHost();
        String token = address.getData().getToken();
        Map<String, String> map = Datacenter.getCookieMap(cookie);
        //连接并发送心跳包
        URI uri = new URI("wss://" + host + ":" + port + "/sub");
        Package pkg = new Package(
                Integer.parseInt(map.get("DedeUserID")),
                RoomId,
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
