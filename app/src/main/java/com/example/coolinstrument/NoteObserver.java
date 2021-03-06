package com.example.coolinstrument;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONException;
import org.json.JSONObject;

public class NoteObserver implements Observer {
    private Piano piano;

    public NoteObserver(Piano piano) {
        this.piano = piano;
    }

    @Override
    public void update(Observable client, Object msg) {
        if (Global.gameID != null && Global.pID != null) {
            try {
                JSONObject info = new JSONObject((String) msg);
                if (((String) msg).contains("collection")
                        && info.getString("collection").equals("datas")
                        && ((String) msg).contains("noteNumber")) {
                    JSONObject fields = info.getJSONObject("fields");
                    String gameID = fields.getString("gameId");
                    String pID = fields.getString("connectionId");
                    String time = fields.getString("time");
                    time = time.substring(0, time.length() - 9);
                    if (Global.gameID.equals(gameID) && !Global.pID.equals(pID)) {
                        int note = fields.getInt("noteNumber");
                        piano.playSound(note, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
