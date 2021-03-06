package external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnClient {

    private static final String API_KEY = "e6ad8c328c0cd50ffa10f8d1c7ca6226c8c1ada6";

    public static void main(String[] args) {

        String[] textList = {
                "Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look.", };
        List<List<String>> words = extractKeywords(textList);
        for (List<String> ws : words) {
            for (String w : ws) {
                System.out.println(w);
            }
            System.out.println();
        }
    }

    public static List<List<String>> extractKeywords(String[] text) {
        if (text == null || text.length == 0) {
            return new ArrayList<>();
        }

        // Use the API key from your account
        MonkeyLearn ml = new MonkeyLearn(API_KEY);

        // Use the keyword extractor
        ExtraParam[] extraParams = {
                new ExtraParam("max_keywords", "4"),
                new ExtraParam("transformation", "lemma"),
                new ExtraParam("transformation", "stem")
        };
        MonkeyLearnResponse response;
        try {
            response = ml.extractors.extract("ex_YCya9nrn", text, extraParams);
            JSONArray resultArray = response.arrayResult;
            return getKeywords(resultArray);
        } catch (MonkeyLearnException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static List<List<String>> getKeywords(JSONArray mlResultArray) {
        List<List<String>> topKeywords = new ArrayList<>();

        for (Object value : mlResultArray) {
            List<String> keywords = new ArrayList<>();
            JSONArray keywordsArray = (JSONArray) value;
            for (Object o : keywordsArray) {
                JSONObject keywordObject = (JSONObject) o;
                String keyword = (String) keywordObject.get("keyword");
                if (keyword.matches("(.*)</li")) {
                    keyword = keyword.substring(0, keyword.length() - 4);
                }
                keywords.add(keyword);
            }
            topKeywords.add(keywords);
        }
        return topKeywords;
    }
}
