package android.quiensoy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private ArrayList<String> activeCollection = new ArrayList<>();
	private String[] words = new String[Constants.WORDS_LENGTH];
	SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setFullFrame();
		setListeners();
		loadPreferences();
	}

	private void setListeners(){
		findViewById(R.id.btnAnimales).setOnClickListener(this);
		findViewById(R.id.btnCiudades).setOnClickListener(this);
		findViewById(R.id.btnPaises).setOnClickListener(this);
		findViewById(R.id.btnPersonajesDisney).setOnClickListener(this);
		findViewById(R.id.btnPeliculasDisney).setOnClickListener(this);
		findViewById(R.id.btnPeliculas).setOnClickListener(this);
		findViewById(R.id.btnSuperheroes).setOnClickListener(this);
		findViewById(R.id.btnEspecialNavidad).setOnClickListener(this);
		findViewById(R.id.btnSettings).setOnClickListener(this);
		findViewById(R.id.btnInfo).setOnClickListener(this);

		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/boys.ttf");
		((Button) findViewById(R.id.btnAnimales)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnCiudades)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnPaises)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnPersonajesDisney)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnPeliculasDisney)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnPeliculas)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnSuperheroes)).setTypeface(typeFace);
		((Button) findViewById(R.id.btnEspecialNavidad)).setTypeface(typeFace);
		((TextView) findViewById(R.id.wordMain)).setTypeface(typeFace);
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

	private void loadPreferences() {
		prefs = getSharedPreferences(Constants.MY_PREFERENCES,
				Context.MODE_PRIVATE);
		Constants.INITIAL_TIME = prefs.getInt("InitialTime", 30);
		Constants.VOLUME = prefs.getFloat("Volume", 1.0f);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setFullFrame();
	}

	private void initWords(int rawId) {
		activeCollection.clear();
		String[] aux =  new WordsFileReader(this).getWords(rawId);
		activeCollection.addAll(Arrays.asList(aux));
	}

	private void generateWordsArray(ArrayList<String> list) {
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < 20; i++) {
			int index = (r.nextInt(list.size()));
			words[i] = list.get(index);
			list.remove(index);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSettings:
			launchSettings();
			return;
		case R.id.btnInfo:
			launchInfo();
			return;
		case R.id.btnAnimales:
			initWords(R.raw.animales);
			break;
		case R.id.btnCiudades:
			initWords(R.raw.ciudades);
			break;
		case R.id.btnPaises:
			initWords(R.raw.paises);
			break;
		case R.id.btnPersonajesDisney:
			initWords(R.raw.personajes_disney);
			break;
		case R.id.btnPeliculasDisney:
			initWords(R.raw.peliculas_disney);
			break;
		case R.id.btnPeliculas:
			initWords(R.raw.peliculas);
			break;
		case R.id.btnSuperheroes:
			initWords(R.raw.superheroes);
			break;
		case R.id.btnEspecialNavidad:
			initWords(R.raw.xmas);
			break;
		default:
			break;

		}

		generateWordsArray(activeCollection);
		// Launch the game
		Intent i = new Intent(getApplicationContext(), GameActivity.class);
		i.putExtra("words", words);
		i.putExtra("initialTime", Constants.INITIAL_TIME);
		startActivity(i);

	}

	private void launchSettings() {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
	}

	private void launchInfo() {
		Intent i = new Intent(getApplicationContext(), InfoActivity.class);
		startActivity(i);
	}
}
