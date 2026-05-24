package com.example.bookapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

        setBookData();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnBookmark).setOnClickListener(v -> {
            dbHelper.insertBookmark(book.id);
            Toast.makeText(this, book.title + " 북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnTopBookmark).setOnClickListener(v -> {
            dbHelper.insertBookmark(book.id);
            Toast.makeText(this, book.title + " 북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
        });


        findViewById(R.id.videoBox).setOnClickListener(v ->
                Toast.makeText(this, book.title + " 관련 영상 재생", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.btnReviewWrite).setOnClickListener(v ->
                Toast.makeText(this, book.title + " 리뷰 작성 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );
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
}