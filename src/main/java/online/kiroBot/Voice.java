package online.kiroBot;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static online.kiroBot.DeepSeekKiroChat.apiJson;


public class Voice {
    private static final String API_KEY = "sk-mkgdltfgwnpetbuvrpnszqepevcxpusoghylastsmnrxkqvy";
    private static final String BASE_URL = "https://api.siliconflow.cn/v1";
    private static final String Elysia = "speech:Elysia:sbdn8crxf9:mststxysjyefjprcoino";
    private static final String Furina = "speech:Furina:sbdn8crxf9:bxcinedegttdfrzvizzb";
    public static File generateVoiceFile(String text, String character) throws IOException {
//        JSONObject deepSeek = apiJson.getJSONObject("DeepSeek");
//        String API_KEY = deepSeek.getString("API_KEY");
//        String BASE_URL = deepSeek.getString("URL");
//        String Furina = deepSeek .getString("Furina");
//        String Elysia = deepSeek .getString("Elysia");
        String model = character.equals("Elysia")?Elysia:Furina;
        // 创建临时文件路径
        Path speechPath = Paths.get("1.mp3");
        File outputFile = speechPath.toFile();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "FunAudioLLM/CosyVoice2-0.5B");
        jsonObject.put("voice", model);
        jsonObject.put("input", text);
        jsonObject.put("response_format", "mp3");
        // 构建请求体
        String jsonInput = jsonObject.toString();

        // 创建HTTP连接
        HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL + "/audio/speech").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        // 发送请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 处理响应
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream is = connection.getInputStream();
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            return outputFile;
        } else {
            throw new IOException("Request failed with HTTP code: " + connection.getResponseCode());
        }
    }

    // 使用示例
    public static void main(String[] args) {
        try {
            File result = generateVoiceFile("哼，月月这个懒虫，整天就知道偷懒！你还有什么脸活在世上啊？快去干活！不过要是你敢哭的话，我就饶不了你！", "Elysia");
            System.out.println("语音文件已生成至：" + result.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}