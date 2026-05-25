package com.example.bookapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookmarkListActivity extends AppCompatActivity {

    DBHelper dbHelper;
    LinearLayout bookmarkListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);

        dbHelper = new DBHelper(this);
        bookmarkListContainer = findViewById(R.id.bookmarkListContainer);
        dbHelper.removeDuplicateBookmarks();
        loadBookmarks();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navFocus).setOnClickListener(v ->
                Toast.makeText(this, "집중모드는 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navReview).setOnClickListener(v ->
                Toast.makeText(this, "리뷰 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navMyPage).setOnClickListener(v -> {
            Intent intent = new Intent(this, MyPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void loadBookmarks() {
        bookmarkListContainer.removeAllViews();

        Cursor cursor = dbHelper.getBookmarks();

        if (cursor.getCount() == 0) {
            TextView empty = new TextView(this);
            empty.setText("아직 북마크한 책이 없습니다.\n책 상세보기에서 북마크를 추가해보세요.");
            empty.setTextColor(Color.GRAY);
            empty.setTextSize(16);
            empty.setGravity(Gravity.CENTER);
            empty.setLineSpacing(dp(4), 1);
            bookmarkListContainer.addView(empty, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(260)
            ));
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {
            int bookId = cursor.getInt(cursor.getColumnIndexOrThrow("book_id"));
            Book book = BookData.getBookById(bookId);
            bookmarkListContainer.addView(createBookmarkRow(book));
        }

        cursor.close();
    }

    private LinearLayout createBookmarkRow(Book book) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(10), dp(10), dp(10), dp(10));
        row.setBackgroundResource(R.drawable.bg_book_card);

        ImageView cover = new ImageView(this);
        cover.setImageResource(R.mipmap.ic_launcher);
        cover.setBackgroundColor(Color.rgb(216, 216, 216));
        cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        row.addView(cover, new LinearLayout.LayoutParams(dp(72), dp(88)));

        LinearLayout infoBox = new LinearLayout(this);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        infoBox.setPadding(dp(14), 0, 0, 0);
        row.addView(infoBox, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView title = new TextView(this);
        title.setText(book.title);
        title.setTextColor(Color.rgb(17, 17, 17));
        title.setTextSize(17);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        infoBox.addView(title);

        TextView meta = new TextView(this);
        meta.setText(book.author + "  |  " + book.category);
        meta.setTextColor(Color.GRAY);
        meta.setTextSize(13);
        meta.setPadding(0, dp(5), 0, 0);
        infoBox.addView(meta);

        TextView rating = new TextView(this);
        rating.setText("★ " + book.rating);
        rating.setTextColor(Color.rgb(245, 180, 0));
        rating.setTextSize(13);
        rating.setPadding(0, dp(5), 0, 0);
        infoBox.addView(rating);

        TextView arrow = new TextView(this);
        arrow.setText("›");
        arrow.setTextColor(Color.rgb(17, 17, 17));
        arrow.setTextSize(32);
        arrow.setGravity(Gravity.CENTER);
        row.addView(arrow, new LinearLayout.LayoutParams(dp(30), LinearLayout.LayoutParams.MATCH_PARENT));

        row.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("book_id", book.id);
            startActivity(intent);
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(10));
        row.setLayoutParams(params);

        return row;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}