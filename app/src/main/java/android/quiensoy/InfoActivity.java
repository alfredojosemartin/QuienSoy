package android.quiensoy;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends Activity /*implements OnClickListener*/ {
	
	@SuppressWarnings("unused")
	TextView donacion, donacion1, donacion3, donacion5, donacion10, donacion30;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		setFullFrame();
			
		initFontsAspect();
			
		findViewById(R.id.btnContinuar).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}
		});

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
	
	private void initFontsAspect(){
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/boys.ttf");
		((Button) findViewById(R.id.btnContinuar)).setTypeface(typeFace);	
	}

	/*
	private void initVariables(){
		donacion = (TextView)findViewById(R.id.donacion);
		donacion1 = (TextView)findViewById(R.id.donacion1);
		donacion3 = (TextView)findViewById(R.id.donacion3);
		donacion5 = (TextView)findViewById(R.id.donacion5);
		donacion10 = (TextView)findViewById(R.id.donacion10);
		donacion30 = (TextView)findViewById(R.id.donacion30);
	}
	
	private void initListeners(){
		donacion.setOnClickListener(this);
		donacion1.setOnClickListener(this);
		donacion3.setOnClickListener(this);
		donacion5.setOnClickListener(this);
		donacion10.setOnClickListener(this);
		donacion30.setOnClickListener(this);
	}
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setFullFrame();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();		
	}

	/*
	@Override
	public void onClick(View v) {
		Intent browserIntent;
			switch (v.getId()) {
			case R.id.donacion:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_number=donacionQuienSoy&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));				
				startActivity(browserIntent);
				break;
			case R.id.donacion1:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_name=Donaciones%20Quien%20Soy%20%2d%201%20%e2%82%ac&item_number=donacion1QuienSoy&amount=1%2e00&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
				startActivity(browserIntent);
				break;
			case R.id.donacion3:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_name=Donaciones%20Quien%20Soy%20%2d%203%20%e2%82%ac&item_number=donacion3QuienSoy&amount=3%2e00&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
				startActivity(browserIntent);
				break;
			case R.id.donacion5:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_name=Donaciones%20Quien%20Soy%20%2d%205%20%e2%82%ac&item_number=donacion5QuienSoy&amount=5%2e00&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
				startActivity(browserIntent);
				break;
			case R.id.donacion10:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_name=Donaciones%20Quien%20Soy%20%2d%2010%20%e2%82%ac&item_number=donacion10QuienSoy&amount=10%2e00&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
				startActivity(browserIntent);
				break;
			case R.id.donacion30:
				browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ZYLHUXS5NSZCE&lc=ES&item_name=Donaciones%20Quien%20Soy%20%2d%2030%20%e2%82%ac&item_number=donacion30QuienSoy&amount=30%2e00&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));				
				startActivity(browserIntent);
				break;
			default:
				break;
			}	
	}*/
}
