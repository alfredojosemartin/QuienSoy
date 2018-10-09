package android.quiensoy;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private View fondolayoutBackground;
	private TextView wordText, timeText;
	private long lastUpdate;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float[] orientationVals = new float[3];
	private CountDownTimer timer;
	private int status = 0;
	private int game_status = 0; // Constans.GAME_STATUS_XXXX
	private int soundtick, soundOK, soundFail, soundGameOver, soundCountDown;
	SoundPool sp;

	private String words[];
	private boolean wordsStatus[];
	private int wordsIndex;
	private int initialTime;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);

		setFullFrame();

		setContentView(R.layout.activity_game);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			words = bundle.getStringArray("words");
			initialTime = bundle.getInt("initialTime");
		}


		// cargamos los sonidos
		loadSounds();

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Log.d("Sensor", "Obtenemos el servicio del sensor");

		// Inicializamos los valores del juego
		initValues();		
	}

	@SuppressWarnings("deprecation")
	private void loadSounds() {
		sp = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundCountDown = sp.load(this, R.raw.countdown, 1);
		soundFail = sp.load(this, R.raw.fail, 1);
		soundGameOver = sp.load(this, R.raw.gameover, 1);
		soundOK = sp.load(this, R.raw.success, 1);
		soundtick = sp.load(this, R.raw.tick, 1);
	}

	private void initValues() {
		lastUpdate = System.currentTimeMillis();
		wordsStatus = new boolean[words.length];
		wordsIndex = 0;

		fondolayoutBackground = findViewById(R.id.mainLayout);
		fondolayoutBackground.setBackgroundColor(Color.BLUE);

		wordText = (TextView) findViewById(R.id.word);
		wordText.setText(R.string.enlafrente);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/boys.ttf");
		wordText.setTypeface(typeFace);

		timeText = (TextView) findViewById(R.id.time);

		game_status = Constants.GAME_STATUS_BEFORE;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			mGravity = event.values;
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			mGeomagnetic = event.values;

		if (mGravity != null && mGeomagnetic != null) {

			long actualTime = System.currentTimeMillis();

			if (!(actualTime - lastUpdate < Constants.GAME_DELAY)) {
				lastUpdate = actualTime;
				getRotationChange();
			}
		}

	}

	private void getRotationChange() {
		float R[] = new float[9];
		float I[] = new float[9];
		SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
		SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X,
				SensorManager.AXIS_Z, R);
		SensorManager.getOrientation(R, orientationVals);

		// Convert the result from radians to degrees
		float degrees = (float) Math.toDegrees(orientationVals[1]);

		if (game_status == Constants.GAME_STATUS_DURING) {
			if (degrees < -Constants.GRADE) {
				if (status != -1) {
					status = -1;
					fondolayoutBackground.setBackgroundColor(0xFF007F0E);
					wordText.setText(android.quiensoy.R.string.acierto);
					wordsStatus[wordsIndex] = true;
					wordsIndex++;
					sp.play(soundOK, Constants.VOLUME, Constants.VOLUME, 0, 0,
							1);
					lastUpdate += Constants.GAME_DELAY_AFTER_RESPONSE;
				}
			} else if (degrees > Constants.GRADE) {
				if (status != 1) {
					status = 1;
					fondolayoutBackground.setBackgroundColor(Color.RED);
					wordText.setText(android.quiensoy.R.string.error);
					wordsStatus[wordsIndex] = false;
					wordsIndex++;
					sp.play(soundFail, Constants.VOLUME, Constants.VOLUME, 0,
							0, 1);
					lastUpdate += Constants.GAME_DELAY_AFTER_RESPONSE;
				}
			} else {
				if (status != 0) {
					status = 0;
					fondolayoutBackground.setBackgroundColor(Color.BLUE);
					if (wordsIndex >= words.length)
						wordsIndex = 0;
					wordText.setText(words[wordsIndex].toUpperCase(Locale.US));
				}
			}
		} else if (game_status == Constants.GAME_STATUS_BEFORE) {
			if ((degrees > -Constants.GRADE) && (degrees < Constants.GRADE)) {
				showPreviousTimer(Constants.GAME_COUNTDOWN_START);
			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * Manage the click event
	 * 
	 * @param view Object clicked
	 */
	public void clickLayout(View view) {
		if (game_status == Constants.GAME_STATUS_AFTER) {
			launchResultActivity();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Sensor mySensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, mySensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(this, mySensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		Log.d("SensorManager", "Registramos los sensores");

		setFullFrame();
	}

	@Override
	protected void onPause() {
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onDestroy() {
		sensorManager.unregisterListener(this);
		if (timer != null) {
			timer.cancel();
		}
		super.onDestroy();
	}

	private void launchResultActivity() {
		Intent i = new Intent(this, ResultsActivity.class);
		i.putExtra("words", words);
		i.putExtra("status", wordsStatus);
		i.putExtra("index", wordsIndex);
		startActivity(i);
		finish();
	}

	private void launchGame() {
		wordText.setText(words[0]);

		game_status = Constants.GAME_STATUS_DURING;
		showTimer(initialTime * Constants.MILLIS_PER_SECOND);
	}

	private void showTimer(int countdownMillis) {
		if (timer != null) {
			timer.cancel();
		}
		timer = new CountDownTimer(countdownMillis, Constants.MILLIS_PER_SECOND) {
			@Override
			public void onTick(long millisUntilFinished) {
				timeText.setText(String.format(Locale.ENGLISH, "%d", millisUntilFinished / Constants.MILLIS_PER_SECOND));
				if ((millisUntilFinished / Constants.MILLIS_PER_SECOND) < 6) {
					sp.play(soundtick, Constants.VOLUME, Constants.VOLUME, 0,
							0, 1);
				}
			}

			@Override
			public void onFinish() {
				game_status = Constants.GAME_STATUS_AFTER;
				wordText.setText(R.string.verResultados);
				timeText.setText("");
				sp.play(soundGameOver, Constants.VOLUME, Constants.VOLUME, 0,
						0, 1);
			}
		};

		timer.start();
	}

	private void showPreviousTimer(int countdownMillis) {
		if (timer != null) {
			return;
		}
		sp.play(soundCountDown, Constants.VOLUME, Constants.VOLUME, 0, 0, 1);
		timer = new CountDownTimer(countdownMillis, Constants.MILLIS_PER_SECOND) {
			@Override
			public void onTick(long millisUntilFinished) {
				wordText.setText(String.format(Locale.ENGLISH,"%d", millisUntilFinished / Constants.MILLIS_PER_SECOND));
			}

			@Override
			public void onFinish() {
				// Launch the game
				launchGame();
			}
		};

		timer.start();
	}

	@SuppressLint("InlinedApi")
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
}
