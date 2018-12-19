package db.functions.appointment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.*;

import db.pojo.eventPOJO.EventDB;

/**
 * LoadAppointment
 */
public class LoadAppointment {

    public ArrayList<EventDB> load(){

        ArrayList<EventDB> events = new ArrayList<>();

        try {

            URL url = new URL("http://localhost/cyberlife/calendar/API/?user=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            con.disconnect();

            JSONObject jsonResponse = new JSONObject(buffer.toString());
            JSONArray data = jsonResponse.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                events.add(createEvent(data.getJSONObject(i)));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
		}

        return events;
    }

    private EventDB createEvent(JSONObject obj) throws JSONException {

        EventDB event = new EventDB();
        System.out.println("---------------------");
        System.out.println(obj.getInt("cod_evento"));
        System.out.println(obj.getString("titulo"));
        System.out.println(obj.getLong("data_inicio"));
        System.out.println(obj.getLong("data_fim"));
        System.out.println(obj.getBoolean("dia_todo"));
        System.out.println(obj.getString("local_evento"));
        System.out.println(obj.getString("descricao"));
        System.out.println(obj.getBoolean("concluido"));
        System.out.println(obj.getBoolean("excluido"));
        System.out.println(obj.getInt("tipo_repeticao"));
        System.out.println(obj.getInt("tipo_fim_repeticao"));
        System.out.println(obj.getInt("fk_usuario"));
        return event;
    }
}