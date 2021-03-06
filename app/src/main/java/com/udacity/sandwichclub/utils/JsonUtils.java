package com.udacity.sandwichclub.utils;
import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONException;
import java.util.List;

public class JsonUtils {

    /**

     *
     * @param json the JSON string to be parsed.
     * @return the JSON string in a Sandwich object or null if parsing the specified JSON string fails.
     */
    public static Sandwich parseSandwichJson(String json) {
        try
        {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.getJSONObject("name");

            String mainName = nameObject.getString("mainName");

            List<String> alsoKnownAs = JsonUtils.fromJsonArrayToList(nameObject, "alsoKnownAs");

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = jsonObject.getString("description");

            String image = jsonObject.getString("image");

            List<String> ingredients = JsonUtils.fromJsonArrayToList(jsonObject, "ingredients");

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (JSONException e)
        {
            // Could not parse JSON String
            return null;
        }
    }

    /**
     
     * @param jsonObject the JSON object containing the JSON array to be converted.
     * @param jsonArrayName the name of the JSON array in the specified JSONObject.
     * @return the JSON array specified by the JSON array name as a List.
     * @throws JSONException if there is a parse error.
     */
    private static List<String> fromJsonArrayToList(JSONObject jsonObject, String jsonArrayName) throws JSONException
    {
        List<String> list = new ArrayList<String>();
        JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayName);
        for(int i = 0; i < jsonArray.length(); ++i)
        {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
