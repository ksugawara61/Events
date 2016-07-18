package org.example.events;

import android.provider.BaseColumns;

/**
 * Created by katsuya on 16/07/18.
 */
public interface Constants extends BaseColumns {
    public static final String TABLE_NAME = "events";
    // Eventsデータベースの列
    public static final String TIME = "time";
    public static final String TITLE = "title";
}
