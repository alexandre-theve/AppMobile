package com.example.ime5_tp2_absences;

import java.util.ArrayList;

import model.Data;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter implements OnClickListener, OnLongClickListener {
	public interface DataAdapterSelected {
	    public void onClickData(Data item);
	    public void onLongClickData(Data item);
	}

	/*private static class ViewHolder 
	{
		public TextView nom;
		public CheckBox presence;
	}*/
	
	private LayoutInflater mInflater;
	private ArrayList<Data> vList;
	private Context context;
	
	public DataAdapter(Context context, ArrayList<Data> vList, LayoutInflater inflater) {
		this.vList = vList;
		this.mInflater = inflater;
		this.context = context;
	}
	
	//Contient la liste des listeners
	private ArrayList<DataAdapterSelected> mListListener = new ArrayList<DataAdapterSelected>();
	/**
	 * Pour ajouter un listener sur notre adapter
	 */
	public void setOnDataAdapterSelected(DataAdapterSelected aListener) {
	    mListListener.add(aListener);
	}
	
	private void sendClickListener(Data item) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onClickData(item);
	    }
	}
	
	private void sendLongClickListener(Data item) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onLongClickData(item);
	    }
	}

	@Override
	public int getCount() {
		return vList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//ViewHolder holder = null;
		// Si la vue n'est pas recyclée
		if(convertView == null) 
		{
			// On récupère le layout
			convertView  = mInflater.inflate(R.layout.adapter_data, null);
			convertView.setOnClickListener(this); 
			convertView.setOnLongClickListener(this);
			
			
			//holder = new ViewHolder();
			
			
			// puis on insère le holder en tant que tag dans le layout
			//convertView.setTag(holder);
		} 
		    
		// On place les widgets de notre layout dans le holder
		TextView nom = (TextView) convertView.findViewById(R.id.prenomTextView);
		CheckBox presence = (CheckBox) convertView.findViewById(R.id.presenceCheckBox);
		presence.setEnabled(false);
					
		// Dans tous les cas, on récupère le user concerné
		Data data = getItem(position);
		
		convertView.setTag(data);
		
		// Si cet élément existe vraiment
		if(data != null) {
			// On place dans le holder les informations du user
			nom.setText(data.getEleve().getPrenom() + " " + data.getEleve().getNom() + (data.getBoolRetard() ? " - " + context.getString(R.string.seance_retard) : ""));
			presence.setChecked(data.getBoolPresence());
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

	@Override
	public void onClick(View v) {
		Data data = (Data)v.getTag();
		
		//On prévient les listeners qu'il y a eu un clic sur l'item
		sendClickListener(data);
	}

	@Override
	public boolean onLongClick(View v) {
		Data data = (Data)v.getTag();
		
		//On prévient les listeners qu'il y a eu un clic sur l'item
		sendLongClickListener(data);
		
		return true;
	}
}
