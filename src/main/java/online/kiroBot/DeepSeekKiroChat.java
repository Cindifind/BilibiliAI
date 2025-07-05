package online.kiroBot;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Path;
import java.util.logging.Logger;



public class DeepSeekKiroChat {
    private static final Logger logger = Logger.getLogger(DeepSeekKiroChat.class.getName());
    public static Path path = Path.of(System.getProperty("user.dir") + "\\LLMKEY\\"+"api.json");
    public static JSONObject apiJson = new JSONObject(Same.loadApi(Path.of(System.getProperty("user.dir") + "\\LLMKEY\\api.json")));
    private static int max_tokens = 1024;

    public static String DeepSeekApi(JSONArray messages, String character) {
        String systemMessage = Same.getKiroCharacter(character);
        JSONObject deepSeek = apiJson.getJSONObject("DeepSeek");
        String API_KEY = deepSeek.getString("API_KEY");
        String BASE_URL = deepSeek.getString("URL");
        //加入character
        if (systemMessage == null) {
            return null;
        }
        JSONObject messageJson = new JSONObject();
        messageJson.put("role", "system");
        messageJson.put("content", systemMessage);
        // 将不可变列表转换为可变列表
        JSONArray mutableMessages = new JSONArray(messages);
        mutableMessages.put(messageJson);
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "deepseek-ai/DeepSeek-V3");
        requestBody.put("max_tokens",max_tokens);
        requestBody.put("messages", new JSONArray(mutableMessages));
        HttpResponse<String> response = Unirest.post(BASE_URL + "/v1/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(requestBody.toString())
                .asString();
        JSONObject object = new JSONObject(response.getBody());
        logger.info("DeepSeek");
        logger.info(object.toString());
        return object
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
    public static void messages(JSONArray messages, String character) {
        JSONObject responseObject = new JSONObject()
                .put("role", "assistant")
                .put("content", DeepSeekApi(messages,character));
        messages.put(responseObject);
    }

    public static void main(String[] args) {
        JSONObject deepSeek = apiJson.getJSONObject("DeepSeek");
        System.out.println(deepSeek);
    }
}
