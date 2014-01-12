package com.example.ime5_tp2_absences;

import java.util.ArrayList;

import model.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter {
	private static class ViewHolder 
	{
		public TextView nom;
		public CheckBox presence;
	}
	
	private LayoutInflater mInflater;
	private ArrayList<Data> vList;
	
	public DataAdapter(ArrayList<Data> vList, LayoutInflater inflater) {
		super();
		this.vList = vList;
		mInflater = inflater;
	}

	@Override
	public int getCount() {
		return vList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// Si la vue n'est pas recycl�e
		if(convertView == null) 
		{
			// On r�cup�re le layout
			convertView  = mInflater.inflate(R.layout.adapter_data, null);
			            
			holder = new ViewHolder();
			// On place les widgets de notre layout dans le holder
			holder.nom = (TextView) convertView.findViewById(R.id.prenomTextView);
			holder.presence = (CheckBox) convertView.findViewById(R.id.presenceCheckBox);
			
			// puis on ins�re le holder en tant que tag dans le layout
			convertView.setTag(holder);
		} 
		else
		{
			// Si on recycle la vue, on r�cup�re son holder en tag
			holder = (ViewHolder)convertView.getTag();
		}
		    
		// Dans tous les cas, on r�cup�re le user concern�
		Data data = (Data)getItem(position);
		
		// Si cet �l�ment existe vraiment
		if(data != null) {
			// On place dans le holder les informations du user
			holder.nom.setText(data.getEleve().getPrenom() + " " + data.getEleve().getNom());
			holder.presence.setChecked(data.getBoolPresence());
		}
		return convertView;
	}

	@Override
	public Data getItem(int position) {
		return vList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}
}
