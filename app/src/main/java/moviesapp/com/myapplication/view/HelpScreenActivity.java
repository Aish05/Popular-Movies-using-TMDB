package moviesapp.com.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.view.utility.Util;

public class HelpScreenActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.help_screen);
        Button btnOk =  findViewById(R.id.btn_ok);
        mContext = this;
        Util.saveBooleanDataInPref(mContext,"FIRST_LAUNCH_SHOWN",true);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpScreenActivity.this.finish();


            }
        });

    }
}
