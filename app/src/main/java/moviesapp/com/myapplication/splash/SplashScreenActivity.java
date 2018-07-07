package moviesapp.com.myapplication.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.WindowManager;
import android.widget.TextView;

import moviesapp.com.myapplication.view.MainActivity;
import moviesapp.com.myapplication.R;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        TextView splashText = findViewById(R.id.splash_text);
        String text = "<font color=#cc0029><b>Popular</b></font> <font color=#ffcc00>Movies</font>";
        splashText.setText(Html.fromHtml(text));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}
