package android.quiensoy;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsActivity extends Activity implements OnItemLongClickListener {
	public String[] words;
	public boolean[] status;
	public int index;

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<ResultItem> listItems = new ArrayList<>();
	// DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	ResultsAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			index = bundle.getInt("index");
			words = bundle.getStringArray("words");
			status = bundle.getBooleanArray("status");
		}

		setFullFrame();

		// Change the font type
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/boys.ttf");
		((TextView) findViewById(R.id.ResultsTextString)).setTypeface(typeFace);
		((TextView) findViewById(R.id.ResultsText)).setTypeface(typeFace);
		
		((Button) findViewById(R.id.btnContinuar)).setTypeface(typeFace);		
		findViewById(R.id.btnContinuar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});

		// Sets the data behind this ListView
		ListView lista = ((ListView) findViewById(R.id.ResultsList));
		adapter = new ResultsAdapter(this, listItems);
		lista.setAdapter(adapter);
		lista.setOnItemLongClickListener(this);

		updateCorrectAnswers();

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

	@Override
	protected void onResume() {
		super.onResume();
		setFullFrame();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void updateCorrectAnswers() {
		int aux = 0;
		ResultItem item;
		for (int i = 0; i < index; i++) {
			item = new ResultItem();
			item.setText(words[i]);
			if (status[i]) {
				aux++;
				item.setIsCorrect(true);
			} else {
				item.setIsCorrect(false);
			}
			listItems.add(item);

		}
		((TextView) findViewById(R.id.ResultsText)).setText(String.format(Locale.ENGLISH,"%d", aux));
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Intent browserIntent = new Intent("android.intent.action.VIEW",
				Uri.parse("http://es.wikipedia.org/wiki/" + words[position]));
		startActivity(browserIntent);
		return true;
	}

}
