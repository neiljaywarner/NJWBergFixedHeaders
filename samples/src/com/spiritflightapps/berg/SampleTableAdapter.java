package com.spiritflightapps.berg;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class implements the main functionalities of the TableAdapter in
 * Mutuactivos.
 * 
 * 
 * @author Brais Gab�n, modiifed by Neil Warner
 */
public abstract class SampleTableAdapter extends BaseTableAdapter {
	private final Context context;
	private final LayoutInflater inflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The current context.
	 */
	public SampleTableAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * Returns the context associated with this array adapter. The context is
	 * used to create views from the resource passed to the constructor.
	 * 
	 * @return The Context associated with this adapter.
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Quick access to the LayoutInflater instance that this Adapter retreived
	 * from its Context.
	 * 
	 * @return The shared LayoutInflater.
	 */
	public LayoutInflater getInflater() {
		return inflater;
	}






}
