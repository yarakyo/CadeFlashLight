package yarakyo.flashlight;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoadCamera extends Activity {
	
	Button LED_BUTTON;
	Button closeButton;
	boolean LEDon = false;
	boolean started = false;
	Camera cam;
	TextView display;
	TextView status;
	Parameters paramsOn; 
	Parameters paramsOff;
	
	
	private void ResetCamera() {
		if(started == false) cam = Camera.open();
		cam.setParameters(paramsOff);
		cam.startPreview();
		cam.autoFocus(new AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {
			}
		});
		cam.release();
	}

	
	private void SetCameraParms()
	{
		//On State
		cam = Camera.open();
		paramsOn = cam.getParameters();
		paramsOn.setFlashMode(Parameters.FLASH_MODE_TORCH);
		paramsOn.setFocusMode(Parameters.FOCUS_MODE_INFINITY);
		
		//Off State
		paramsOff = cam.getParameters();
		paramsOff.setFlashMode(Parameters.FLASH_MODE_OFF);
		paramsOff.setFocusMode(Parameters.FOCUS_MODE_INFINITY);
		cam.release();
	}
	
    private void SetUpViewAndListeners()
    {
    	status = (TextView) findViewById(R.id.textViewStatus);
        LED_BUTTON = (Button) findViewById(R.id.LEDButton);
        display = (TextView) findViewById(R.id.textView);	
       
        LED_BUTTON.setOnClickListener(new View.OnClickListener() {     	
			@Override
			public void onClick(View v) {
				LEDSwitch();
			}
		});
        
                     
        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				status.setText("Attempting to Close");
				ResetCamera();
				finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
    }
    
    private void LEDSwitch()
    {    	
		if(LEDon == false)
		{
			try{
				if(started == true) cam.release();
				cam = Camera.open();
				cam.setParameters(paramsOn);
				cam.startPreview();
				status.setText("LED SHOULD BE ON");
				LED_BUTTON.setText("Turn Light Off");
				started = true;
				LEDon = true;
			}catch(RuntimeException e){
				status.setText("Camera Class");	status.setText("Error" + e);
			}
		}else
		{
			try{
				if(started == true) cam.release();
				cam = Camera.open();
				cam.setParameters(paramsOff);
				cam.startPreview();
				status.setText("LED SHOULD BE OFF");
				LED_BUTTON.setText("Turn Light On");
				started = true;
				LEDon = false;
			}catch(RuntimeException e){
				status.setText("Camera Class");	status.setText("Error" + e);
			}
		}
		
		cam.autoFocus(new AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {
			}
		});
	
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_camera);
		SetCameraParms();
		ResetCamera();
		SetUpViewAndListeners();
	}


	@Override
	protected void onStop() {
		super.onStop();

		if (cam != null) {
			cam.release();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (cam != null) {
			cam.release();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_load_camera, menu);
		return true;
	}
}
