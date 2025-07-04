package online.map;

import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Package {
    int uid;
    int roomid;
    int protover;
    String buvid;
    String platform;
    int type;
    String key;

    public Package(int uid, int roomid, int protover, String buvid, String platform, int type, String key) {
        this.uid = uid;
        this.roomid = roomid;
        this.protover = protover;
        this.buvid = buvid;
        this.platform = platform;
        this.type = type;
        this.key = key;
    }

    public Package() {
    }

    /**
     * 创建一个数据包
     *
     * @param operation 操作符
     * @param data      数据
     * @return 返回一个包
     */
    public static byte[] createDataPackage(int operation, byte[] data) {
        int length = data.length + 16;
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.putInt(length);
        buffer.putShort((short) 16);
        buffer.putShort((short) 1);
        buffer.putInt(operation);
        buffer.putInt(1);
        buffer.put(data);
        return buffer.array();
    }
}
