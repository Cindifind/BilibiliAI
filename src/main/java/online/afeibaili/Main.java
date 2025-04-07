package online.afeibaili;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.afeibaili.map.Address;
import online.afeibaili.map.Datacenter;
import online.afeibaili.map.Package;

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
        String url = "https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id=" + RoomId + "&type=0";
        String cookie = "buvid3=1EEE960C-C574-FEB9-0AC3-3B764D2F701D02256infoc; b_nut=1717866702; buvid4=C3131B70-136E-964B-4BCA-043379C654EA02256-024060817-FkQzfkCfnDsTzG0Nw2V3hQ%3D%3D; LIVE_BUVID=AUTO2917178667032455; _uuid=B1045B1022-3E51-1B83-C10F10-10323C47F862924879infoc; enable_web_push=DISABLE; header_theme_version=CLOSE; rpdid=|(k|)~)uk~0J'u~u)Rlk)~Y; buvid_fp_plain=undefined; hit-dyn-v2=1; CURRENT_BLACKGAP=0; CURRENT_QUALITY=120; fingerprint=adc6a753f2836252b03a31cee5268821; DedeUserID=437366177; DedeUserID__ckMd5=7e72316470ed821c; buvid_fp=adc6a753f2836252b03a31cee5268821; enable_feed_channel=ENABLE; home_feed_column=5; browser_resolution=1659-856; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1739632501,1739635126,1739982111,1741707017; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NDM2ODE1NjQsImlhdCI6MTc0MzQyMjMwNCwicGx0IjotMX0.iEXF8zKj70PWlegSHb1f1-2HoO2t9NiIPUsiXY30TDc; bili_ticket_expires=1743681504; CURRENT_FNVAL=4048; bp_t_offset_437366177=1050513575711342592; b_lsid=10AF78B3D_195F6A0B5CC; SESSDATA=5a9812e2%2C1759151483%2Cc6999%2A42CjBtoSMDqfEJKpFkQOZtiZZGPFnspwJs-jU8NN6LM5VNpNFzTGbjppns4WSMkrmoPr0SVlJQcl93SnlLQnVkeTBfU2JuWUdTMmtkT1AtSWpHdzR3RmdNa01NOXUwRGduZVZ5TW9mTmUzUU5KcFJ2YVBqTmY4R20zU1RwOWZKT2dUTWh5VGNlWVV3IIEC; bili_jct=bcd075ccd8278b2930a149a11a6f50db; sid=7i1q1gi0; PVID=1";
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
