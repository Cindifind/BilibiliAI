package online.kiroBot;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

import static online.kiroBot.DeepSeekKiroChat.apiJson;

public class GPTKiroChat {
    private static final Logger logger = Logger.getLogger(GPTKiroChat.class.getName());

    public static String gpt35Api(JSONArray messages,String character) {
        JSONObject ChatGPT = apiJson.getJSONObject("GPT");
        String API_KEY = ChatGPT.getString("API_KEY");
        String BASE_URL = ChatGPT.getString("URL");
        String systemMessage = Same.getKiroCharacter(character);
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
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new JSONArray(mutableMessages));
        HttpResponse<String> response = Unirest.post(BASE_URL + "/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(requestBody.toString())
                .asString();
        JSONObject object = new JSONObject(response.getBody());
        logger.info("GPT");
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
                .put("content", gpt35Api(messages,character));
        messages.put(responseObject);
    }
}
