package com.example.cointoss;

import android.app.Activity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity {
    private final Random random = new Random();
    private TextView coin, result, stats;
    private Button tossButton, resetButton;
    private int heads = 0, tails = 0;
    private boolean tossing = false;

    private int dp(float v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    private TextView label(String text, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        return t;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(16, 23, 42));
        getWindow().setNavigationBarColor(Color.rgb(16, 23, 42));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(24), dp(24), dp(24), dp(20));
        root.setBackgroundColor(Color.rgb(16, 23, 42));

        TextView title = label("COIN TOSS", 28, Color.WHITE, true);
        root.addView(title, new LinearLayout.LayoutParams(-1, dp(52)));

        TextView subtitle = label("Leave it to chance.", 15, Color.rgb(176, 188, 211), false);
        root.addView(subtitle, new LinearLayout.LayoutParams(-1, dp(30)));

        View spacer = new View(this);
        root.addView(spacer, new LinearLayout.LayoutParams(1, dp(20)));

        coin = label("H", 54, Color.rgb(16, 23, 42), true);
        coin.setBackground(rounded(Color.rgb(247, 201, 72), 100));
        LinearLayout.LayoutParams coinParams = new LinearLayout.LayoutParams(dp(190), dp(190));
        coinParams.gravity = Gravity.CENTER;
        root.addView(coin, coinParams);

        result = label("Ready to flip?", 24, Color.WHITE, true);
        LinearLayout.LayoutParams resultParams = new LinearLayout.LayoutParams(-1, dp(62));
        resultParams.topMargin = dp(14);
        root.addView(result, resultParams);

        tossButton = new Button(this);
        tossButton.setText("FLIP COIN");
        tossButton.setTextSize(17);
        tossButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tossButton.setTextColor(Color.rgb(16, 23, 42));
        tossButton.setBackground(rounded(Color.rgb(247, 201, 72), 18));
        LinearLayout.LayoutParams tossParams = new LinearLayout.LayoutParams(-1, dp(58));
        tossParams.topMargin = dp(12);
        root.addView(tossButton, tossParams);

        stats = label("HEADS   0       •       TAILS   0", 16, Color.rgb(218, 226, 242), true);
        LinearLayout.LayoutParams statsParams = new LinearLayout.LayoutParams(-1, dp(58));
        statsParams.topMargin = dp(18);
        root.addView(stats, statsParams);

        resetButton = new Button(this);
        resetButton.setText("RESET STATS");
        resetButton.setTextColor(Color.WHITE);
        resetButton.setTextSize(14);
        resetButton.setBackground(rounded(Color.rgb(37, 48, 72), 16));
        LinearLayout.LayoutParams resetParams = new LinearLayout.LayoutParams(-1, dp(50));
        resetParams.topMargin = dp(4);
        root.addView(resetButton, resetParams);

        TextView footer = label("A fair 50/50 coin toss", 13, Color.rgb(133, 149, 179), false);
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(-1, dp(42));
        footerParams.topMargin = dp(8);
        root.addView(footer, footerParams);

        tossButton.setOnClickListener(v -> toss());
        resetButton.setOnClickListener(v -> {
            heads = 0; tails = 0;
            updateStats();
            result.setText("Ready to flip?");
        });

        setContentView(root);
    }

    private void toss() {
        if (tossing) return;
        tossing = true;
        tossButton.setEnabled(false);
        result.setText("Flipping...");
        ObjectAnimator spin = ObjectAnimator.ofFloat(coin, View.ROTATION_Y, 0f, 1440f);
        spin.setDuration(900);
        spin.setInterpolator(new DecelerateInterpolator());
        spin.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                boolean isHeads = random.nextBoolean();
                if (isHeads) heads++; else tails++;
                coin.setRotationY(0f);
                coin.setText(isHeads ? "H" : "T");
                coin.setBackground(rounded(isHeads ? Color.rgb(247, 201, 72) : Color.rgb(111, 174, 255), 100));
                result.setText(isHeads ? "HEADS!" : "TAILS!");
                updateStats();
                tossButton.setEnabled(true);
                tossing = false;
            }
        });
        spin.start();
    }

    private void updateStats() {
        stats.setText("HEADS   " + heads + "       •       TAILS   " + tails);
    }
}
