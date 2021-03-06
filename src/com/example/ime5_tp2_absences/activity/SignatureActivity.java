package com.example.ime5_tp2_absences.activity;

import java.util.ArrayList;

import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.R.id;
import com.example.ime5_tp2_absences.R.layout;
import com.example.ime5_tp2_absences.R.menu;

import model.Data;
import model.Signature;
import GesturePanel.Panel;
import GesturePanel.Point;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SignatureActivity extends Activity implements OnClickListener {
	private Panel p;
	private Data data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signature);

		p = (Panel) findViewById(R.id.panelSignature);
		Button button = (Button) findViewById(R.id.SignatureBouton);
		button.setOnClickListener(this);
		
		if(getIntent().getSerializableExtra("data") != null) {
			//signature = new Signature((ArrayList<Point>) getIntent().getExtras().getSerializable("signature"));
			data = (Data) getIntent().getSerializableExtra("data");
			
			p.setSignature(data.getSignature());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_signature, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.signature_clear:
			p.clear();
			break;
		case R.id.signature_retard:
			item.setChecked(!item.isChecked());
			data.setBoolRetard(item.isChecked());
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setContentView(R.layout.activity_signature);
		Signature signature = p.getSignature();
		p = (Panel) findViewById(R.id.panelSignature);
		p.setSignature(signature);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		data.setSignature(p.getSignature());
		this.setResult(RESULT_OK, (new Intent()).putExtra("data", data));
		finish();
	}
}
