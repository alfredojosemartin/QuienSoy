package android.quiensoy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.Locale;

public class SettingsActivity extends Activity {
	
	SeekBar volumenBar, timeBar;
	TextView volumenText, timeText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		setFullFrame();
		
		initVariables();
		initSeekBarsListeners();
		initFontsAspect();
		initValues();
			
		findViewById(R.id.btnSettingsContinuar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});

	}

	private void initVariables(){
		volumenBar = ((SeekBar) findViewById(R.id.seekVolume));
		timeBar = ((SeekBar) findViewById(R.id.seekTime));
		timeText = ((TextView)findViewById(R.id.settingsTextTime));
		volumenText = ((TextView)findViewById(R.id.settingsVolume));
	}
	
	private void setFullFrame() {
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	private void initSeekBarsListeners() {

		volumenBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						volumenText.setText(String.format(Locale.ENGLISH, "%d%%", progress));
						Constants.VOLUME = (float)progress/100;
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {}
				});
		
		timeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar,
					int progress, boolean fromUser) {
				timeText.setText(String.format(Locale.ENGLISH,"%ds", (progress + 1) * 5));
				Constants.INITIAL_TIME = (progress+1)*5;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	}
	
	private void initValues(){
		volumenBar.setProgress((int) (Constants.VOLUME * 100));
		volumenText.setText(String.format(Locale.ENGLISH,"%d%%", (int) (Constants.VOLUME * 100)));
		timeBar.setProgress((Constants.INITIAL_TIME / 5)-1);
		timeText.setText(String.format(Locale.ENGLISH,"%ds", Constants.INITIAL_TIME));
	}
	
	private void initFontsAspect(){
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/boys.ttf");
		((TextView) findViewById(R.id.settings_volumeString))
				.setTypeface(typeFace);
		((TextView) findViewById(R.id.settings_timeString))
				.setTypeface(typeFace);
		((Button) findViewById(R.id.btnSettingsContinuar)).setTypeface(typeFace);	
	}

	@Override
	protected void onResume() {
		super.onResume();
		setFullFrame();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		savePrefrences();
	}

	private void savePrefrences(){
		SharedPreferences prefs =
			     getSharedPreferences(Constants.MY_PREFERENCES,Context.MODE_PRIVATE);
			 
			SharedPreferences.Editor editor = prefs.edit();
			editor.putFloat("Volume", Constants.VOLUME);
			editor.putInt("InitialTime", Constants.INITIAL_TIME);
			editor.apply();
	}
}
