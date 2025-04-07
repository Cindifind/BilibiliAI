import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Test {
    public static byte[] getCertification(String json) {
        // 将JSON转换为UTF-8字节数组
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);

        // 创建一个总长度为jsonBytes.length + 16的字节数组
        ByteBuffer buffer = ByteBuffer.allocate(jsonBytes.length + 16);

        // 填充数据包头部
        buffer.putInt(jsonBytes.length + 16);  // 数据包总长度
        buffer.putShort((short) 16);           // 头部长度
        buffer.putShort((short) 1);            // 协议版本
        buffer.putInt(7);                      // 类型, 7为加入房间认证
        buffer.putInt(1);                      // 填充值

        // 填充json数据
        buffer.put(jsonBytes);

        return buffer.array();
    }

    public static void main(String[] args) {
        String json = "{\"username\":\"user123\", \"password\":\"pass123\"}";
        byte[] certificationData = getCertification(json);

        // 输出生成的数据包
        for (byte b : certificationData) {
            System.out.print(String.format("%02X ", b));
        }
    }
}
