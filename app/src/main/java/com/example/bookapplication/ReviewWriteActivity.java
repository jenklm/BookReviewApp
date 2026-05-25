package com.example.bookapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewWriteActivity extends AppCompatActivity {

    Book book;
    DBHelper dbHelper;

    int selectedRating = 4;

    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvRatingNumber;
    EditText etReviewContent;

    TextView star1, star2, star3, star4, star5;

    int purple = Color.rgb(95, 53, 200);
    int gray = Color.rgb(187, 187, 187);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        dbHelper = new DBHelper(this);

        int bookId = getIntent().getIntExtra("book_id", 1);
        book = BookData.getBookById(bookId);

        tvBookTitle = findViewById(R.id.tvBookTitle);
        tvBookAuthor = findViewById(R.id.tvBookAuthor);
        tvRatingNumber = findViewById(R.id.tvRatingNumber);
        etReviewContent = findViewById(R.id.etReviewContent);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        tvBookTitle.setText(book.title);
        tvBookAuthor.setText(book.author);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        star1.setOnClickListener(v -> setRating(1));
        star2.setOnClickListener(v -> setRating(2));
        star3.setOnClickListener(v -> setRating(3));
        star4.setOnClickListener(v -> setRating(4));
        star5.setOnClickListener(v -> setRating(5));

        findViewById(R.id.btnSaveReview).setOnClickListener(v -> saveReview());

        updateStars();
    }

    private void setRating(int rating) {
        selectedRating = rating;
        updateStars();
    }

    private void updateStars() {
        TextView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < selectedRating) {
                stars[i].setText("★");
                stars[i].setTextColor(purple);
            } else {
                stars[i].setText("☆");
                stars[i].setTextColor(gray);
            }
        }

        tvRatingNumber.setText(selectedRating + ".0");
    }

    private void saveReview() {
        String content = etReviewContent.getText().toString().trim();

        if (content.length() == 0) {
            Toast.makeText(this, "감상문을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.insertReview(book.id, selectedRating, content);

        String message;

        if (selectedRating >= 4) {
            message = "좋았던 책으로 리뷰가 저장되었습니다.";
        } else if (selectedRating == 3) {
            message = "무난한 독서 기록으로 저장되었습니다.";
        } else {
            message = "아쉬웠던 점이 기록되었습니다.";
        }

        new AlertDialog.Builder(this)
                .setTitle("리뷰 저장 완료")
                .setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> finish())
                .show();
    }
}