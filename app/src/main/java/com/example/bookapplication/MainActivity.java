package com.example.bookapplication;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView chipAll, chipNovel, chipEssay, chipSelf, chipPsychology, chipHuman;
    TextView tvListTitle;

    int purple = Color.rgb(95, 53, 200);
    int grayText = Color.rgb(100, 100, 100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipAll = findViewById(R.id.chipAll);
        chipNovel = findViewById(R.id.chipNovel);
        chipEssay = findViewById(R.id.chipEssay);
        chipSelf = findViewById(R.id.chipSelf);
        chipPsychology = findViewById(R.id.chipPsychology);
        chipHuman = findViewById(R.id.chipHuman);
        tvListTitle = findViewById(R.id.tvListTitle);

        LinearLayout cardBook1 = findViewById(R.id.cardBook1);
        LinearLayout cardBook2 = findViewById(R.id.cardBook2);
        LinearLayout cardBook3 = findViewById(R.id.cardBook3);

        LinearLayout rowBook1 = findViewById(R.id.rowBook1);
        LinearLayout rowBook2 = findViewById(R.id.rowBook2);
        LinearLayout rowBook3 = findViewById(R.id.rowBook3);

        cardBook1.setOnClickListener(v -> openBookDetail(1));
        cardBook2.setOnClickListener(v -> openBookDetail(2));
        cardBook3.setOnClickListener(v -> openBookDetail(3));

        rowBook1.setOnClickListener(v -> openBookDetail(1));
        rowBook2.setOnClickListener(v -> openBookDetail(4));
        rowBook3.setOnClickListener(v -> openBookDetail(2));

        chipAll.setOnClickListener(v -> selectChip("전체"));
        chipNovel.setOnClickListener(v -> selectChip("소설"));
        chipEssay.setOnClickListener(v -> selectChip("에세이"));
        chipSelf.setOnClickListener(v -> selectChip("자기계발"));
        chipPsychology.setOnClickListener(v -> selectChip("심리"));
        chipHuman.setOnClickListener(v -> selectChip("인문"));

        findViewById(R.id.navFocus).setOnClickListener(v ->
                Toast.makeText(this, "집중모드는 다음 단계에서 제작합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navReview).setOnClickListener(v ->
                Toast.makeText(this, "리뷰 화면은 다음 단계에서 제작합니다.", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navMyPage).setOnClickListener(v -> {
            Intent intent = new Intent(this, MyPageActivity.class);
            startActivity(intent);
        });
    }

    private void selectChip(String selected) {
        resetChips();

        if (selected.equals("전체")) {
            chipAll.setBackgroundResource(R.drawable.bg_chip_selected);
            chipAll.setTextColor(Color.WHITE);
            tvListTitle.setText("최근 본 책");
        } else if (selected.equals("소설")) {
            chipNovel.setBackgroundResource(R.drawable.bg_chip_selected);
            chipNovel.setTextColor(Color.WHITE);
            tvListTitle.setText("소설");
        } else if (selected.equals("에세이")) {
            chipEssay.setBackgroundResource(R.drawable.bg_chip_selected);
            chipEssay.setTextColor(Color.WHITE);
            tvListTitle.setText("에세이");
        } else if (selected.equals("자기계발")) {
            chipSelf.setBackgroundResource(R.drawable.bg_chip_selected);
            chipSelf.setTextColor(Color.WHITE);
            tvListTitle.setText("자기계발");
        } else if (selected.equals("심리")) {
            chipPsychology.setBackgroundResource(R.drawable.bg_chip_selected);
            chipPsychology.setTextColor(Color.WHITE);
            tvListTitle.setText("심리");
        } else if (selected.equals("인문")) {
            chipHuman.setBackgroundResource(R.drawable.bg_chip_selected);
            chipHuman.setTextColor(Color.WHITE);
            tvListTitle.setText("인문");
        }

        Toast.makeText(this, selected + " 선택", Toast.LENGTH_SHORT).show();
    }

    private void resetChips() {
        chipAll.setBackgroundResource(R.drawable.bg_chip);
        chipNovel.setBackgroundResource(R.drawable.bg_chip);
        chipEssay.setBackgroundResource(R.drawable.bg_chip);
        chipSelf.setBackgroundResource(R.drawable.bg_chip);
        chipPsychology.setBackgroundResource(R.drawable.bg_chip);
        chipHuman.setBackgroundResource(R.drawable.bg_chip);

        chipAll.setTextColor(purple);
        chipNovel.setTextColor(purple);
        chipEssay.setTextColor(purple);
        chipSelf.setTextColor(purple);
        chipPsychology.setTextColor(purple);
        chipHuman.setTextColor(purple);
    }

    private void openBookDetail(int bookId) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }
}