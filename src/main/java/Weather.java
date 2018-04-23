import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=OPEN_WEATHER_TOKEN");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.next();
        }

        JSONObject jsonObject = new JSONObject(result);
        model.setName(jsonObject.getString("name"));

        JSONObject mainObject = jsonObject.getJSONObject("main");
        model.setTemp(mainObject.getDouble("temp"));
        model.setHumidity(mainObject.getDouble("humidity"));

        JSONArray array = jsonObject.getJSONArray("weather");
        for (int i = 0; i < array.length(); i++) {
            JSONObject temp = array.getJSONObject(i);
            model.setIcon((String) temp.get("icon"));
            model.setMain((String) temp.get("main"));
        }

        return "name=" + model.getName() + '\n' +
                "temp=" + model.getTemp() + "C\n" +
                "humidity=" + model.getHumidity() + "%\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png\n" +
                "main=" + model.getMain() + ".";
    }
}
