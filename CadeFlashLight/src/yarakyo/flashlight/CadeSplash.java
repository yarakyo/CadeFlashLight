package yarakyo.flashlight;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CadeSplash extends Activity {
	
	public void SplashScreen()
	{
		Thread timer = new Thread(){
			public void run(){
			try{
				SystemClock.sleep(500);		
			}finally{
				Intent CameraIntent = new Intent(CadeSplash.this,LoadCamera.class);
				startActivity(CameraIntent);
			}
			}
		};
		timer.start();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cade_splash);
        SplashScreen();
    }
    
	@Override
	protected void onStart() {
		super.onStart();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
