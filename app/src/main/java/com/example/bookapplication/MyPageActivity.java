package com.example.bookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    DBHelper dbHelper;

    TextView tvBookmarkCount;
    TextView tvReviewCount;
    TextView tvReadingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        dbHelper = new DBHelper(this);

        tvBookmarkCount = findViewById(R.id.tvBookmarkCount);
        tvReviewCount = findViewById(R.id.tvReviewCount);
        tvReadingTime = findViewById(R.id.tvReadingTime);

        loadMyPageData();

        findViewById(R.id.menuBookmarks).setOnClickListener(v ->
                Toast.makeText(this, "북마크한 책 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.menuReviews).setOnClickListener(v ->
                Toast.makeText(this, "내가 쓴 리뷰 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.menuReadingRecord).setOnClickListener(v ->
                Toast.makeText(this, "독서 시간 기록 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.menuMusic).setOnClickListener(v ->
                Toast.makeText(this, "음악 재생 목록 화면은 다음 단계에서 연결합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.menuSetting).setOnClickListener(v ->
                Toast.makeText(this, "설정 화면은 선택 기능입니다.", Toast.LENGTH_SHORT).show()
        );

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

        findViewById(R.id.navMyPage).setOnClickListener(v ->
                Toast.makeText(this, "현재 마이페이지입니다.", Toast.LENGTH_SHORT).show()
        );
    }

    private void loadMyPageData() {
        int bookmarkCount = dbHelper.getBookmarkCount();
        int reviewCount = dbHelper.getReviewCount();

        tvBookmarkCount.setText(String.valueOf(bookmarkCount));
        tvReviewCount.setText(String.valueOf(reviewCount));

        // 독서 타이머 저장 기능 연결 전까지는 기록 개수 기준으로 임시 표시
        int readingRecordCount = dbHelper.getReadingRecordCount();
        if (readingRecordCount == 0) {
            tvReadingTime.setText("0시간");
        } else {
            tvReadingTime.setText(readingRecordCount + "회");
        }
    }
}