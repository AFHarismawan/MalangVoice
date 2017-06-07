package net.gumcode.malangvoice.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by A. Fauzi Harismawan on 1/6/2016.
 */
public class Utilities {

    public static String getStringTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        String show = "";
        try {
            Date pubDate = dateFormat.parse(time);
            long milis = System.currentTimeMillis() - pubDate.getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(milis);

            if (minutes == 0) {
                show = TimeUnit.MILLISECONDS.toSeconds(milis) + " detik yang lalu.";
            } else if (minutes > 0 && minutes < 60) {
                show = minutes + " menit yang lalu.";
            } else if (minutes >= 60 && minutes < 1440) {
                show = TimeUnit.MILLISECONDS.toHours(milis) + " jam yang lalu.";
            } else if (minutes >= 1440) {
                show = TimeUnit.MILLISECONDS.toDays(milis) + " hari yang lalu.";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return show;
    }
}
