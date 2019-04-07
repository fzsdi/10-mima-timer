package ir.ifaeze.selfstudy;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    TextView mTextView;
    SeekBar mSeekBar;
    RelativeLayout mRelativeLayout;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secLeft) {
        int minutes = secLeft / 60;
        int seconds = secLeft - minutes * 60;

        String secString = Integer.toString(seconds);

        if (seconds <= 9) {
            secString = "0" + seconds;
        }

        mTextView.setText(minutes + ":" + secString);
    }

    public void resetTimer() {
        mTextView.setText("0:30");
        mSeekBar.setProgress(30);
        mButton.setText("FIRE!");
        countDownTimer.cancel();
        mSeekBar.setVisibility(mRelativeLayout.VISIBLE);
        counterIsActive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = findViewById(R.id.Button);
        mTextView = findViewById(R.id.textView);
        mSeekBar = findViewById(R.id.seekBar);
        mRelativeLayout = findViewById(R.id.relativeLayout);

        mSeekBar.setMax(600);
        mSeekBar.setProgress(30);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        View.OnClickListener fireListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!counterIsActive) {
                    mSeekBar.setVisibility(mRelativeLayout.GONE);
                    counterIsActive = true;
                    mButton.setText("STIFLE");
                    countDownTimer = new CountDownTimer(mSeekBar.getProgress() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            mTextView.setText("0:00");
                            mSeekBar.setVisibility(mRelativeLayout.VISIBLE);
                            mTextView.setText("0:00");
                            mButton.setText("FIRE!");
                        }
                    }.start();
                } else if (counterIsActive) {
                    resetTimer();
                }
            }
        };

        mButton.setOnClickListener(fireListener);
    }
}
