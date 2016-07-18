package org.example.events;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EventsData events;
    private ListView mListView;
    private static String[] FROM = {Constants._ID, Constants.TIME, Constants.TITLE,};
    private static String ORDER_BY = Constants.TIME + " DESC";
    private static int[] TO = {R.id.rowid, R.id.time, R.id.title};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(android.R.id.list);
        mListView.setEmptyView(findViewById(android.R.id.empty));
        events = new EventsData(this);
        try {
            addEvent("Hello, Android");
            Cursor cursor = getEvents();
            showEvents(cursor);
        }
        finally {
            events.close();
        }
    }

    private void addEvent(String string) {
        // Eventsデータソースに新しい行を挿入
        // 削除、更新も同様の方法で実行できる
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.TIME, System.currentTimeMillis());
        values.put(Constants.TITLE, string);
        db.insertOrThrow(Constants.TABLE_NAME, null, values);
    }

    private Cursor getEvents() {
        // 管理されたクエリを実行する。Activityは接続切断の他、必要な場合は再クエリを処理する
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showEvents(Cursor cursor) {
        // データの紐付を行う
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.item, cursor, FROM, TO, 0);
        mListView.setAdapter(adapter);
        /*// カーソルのすべての内容を大きな文字列に詰め込む
        StringBuilder builder = new StringBuilder("Saved events:\n");
        while(cursor.moveToNext()) {
            // インデックスの取得にはgetColumnIndexOrThrow()も使える
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String title = cursor.getString(2);
            builder.append(id).append(": ");
            builder.append(time).append(": ");
            builder.append(title).append("\n");
        }
        // 画面に文字列を表示する
        TextView text = (TextView)findViewById(R.id.text);
        text.setText(builder);*/
    }
}
