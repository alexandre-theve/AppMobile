package com.example.ime5_tp2_absences;

import java.util.ArrayList;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signature);

		Signature signature = null;
		if(getIntent().getExtras() != null &&  getIntent().getExtras().getSerializable("signature") != null)
			signature = new Signature((ArrayList<Point>) getIntent().getExtras().getSerializable("signature"));
		
		p = (Panel) findViewById(R.id.panelSignature);
		Button button = (Button) findViewById(R.id.SignatureBouton);
		button.setOnClickListener(this);
		
		p.setSignature(signature);
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
		this.setResult(RESULT_OK, (new Intent()).putExtra("signature", p.getSignature()));
		finish();
	}
}
