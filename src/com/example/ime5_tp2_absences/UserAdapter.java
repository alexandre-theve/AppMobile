package com.example.ime5_tp2_absences;

import java.util.ArrayList;

import model.Users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
	private static class ViewHolder 
	{
		public TextView nom;
		public CheckBox presence;
	}
	
	private LayoutInflater mInflater;
	private ArrayList<Users> vList;
	
	public UserAdapter(ArrayList<Users> vList, LayoutInflater inflater) {
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
			convertView  = mInflater.inflate(R.layout.adapter_user, null);
			            
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
		Users user = (Users)getItem(position);
		
		// Si cet �l�ment existe vraiment
		if(user != null) {
			// On place dans le holder les informations du user
			holder.nom.setText(user.getPrenom() + " " + user.getNom());
		}
		return convertView;
	}

	@Override
	public Users getItem(int position) {
		return vList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}
}
