package com.example.bookapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    int bookId;
    Book book;
    DBHelper dbHelper;

    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookMeta;
    TextView tvBookRating;
    TextView tvBookDesc;
    TextView tvBookTags;
    TextView tvBookInfo;

    ImageView btnTopBookmark;
    boolean isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        dbHelper = new DBHelper(this);

        bookId = getIntent().getIntExtra("book_id", 1);
        book = BookData.getBookById(bookId);

        tvBookTitle = findViewById(R.id.tvBookTitle);
        tvBookAuthor = findViewById(R.id.tvBookAuthor);
        tvBookMeta = findViewById(R.id.tvBookMeta);
        tvBookRating = findViewById(R.id.tvBookRating);
        tvBookDesc = findViewById(R.id.tvBookDesc);
        tvBookTags = findViewById(R.id.tvBookTags);
        tvBookInfo = findViewById(R.id.tvBookInfo);

        btnTopBookmark = findViewById(R.id.btnTopBookmark);

        isBookmarked = dbHelper.isBookmarked(book.id);
        updateBookmarkIcon();

        btnTopBookmark.setOnClickListener(v -> toggleBookmark());

        findViewById(R.id.btnBookmark).setOnClickListener(v -> toggleBookmark());

        setBookData();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());


        findViewById(R.id.videoBox).setOnClickListener(v ->
                Toast.makeText(this, book.title + " 관련 영상 재생", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.btnReviewWrite).setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewWriteActivity.class);
            intent.putExtra("book_id", book.id);
            startActivity(intent);
        });
    }

    private void setBookData() {
        tvBookTitle.setText(book.title);
        tvBookAuthor.setText(book.author);
        tvBookMeta.setText(book.category + "  |  " + book.publisher + "  |  " + book.date);
        tvBookRating.setText("★  " + book.rating + "  (" + book.count + ")");
        tvBookDesc.setText(book.description);
        tvBookTags.setText(book.tags);
        tvBookInfo.setText(book.info);
    }

    private void toggleBookmark() {
        if (isBookmarked) {
            dbHelper.deleteBookmark(book.id);
            isBookmarked = false;
            Toast.makeText(this, book.title + " 북마크가 취소되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertBookmark(book.id);
            isBookmarked = true;
            Toast.makeText(this, book.title + " 북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
        }

        updateBookmarkIcon();
    }

    private void updateBookmarkIcon() {
        if (isBookmarked) {
            btnTopBookmark.setImageResource(R.drawable.bookmark);
        } else {
            btnTopBookmark.setImageResource(R.drawable.bookmark_outline);
        }
    }
}