package com.tech.cloudnausor.ohmytennis.utils.homemenu;

import android.content.Context;

import com.tech.cloudnausor.ohmytennis.R;


public class Utils {

	//Set all the navigation icons and always to set "zero 0" for the item is a category
	public static int[] iconNavigation = new int[] {
			R.drawable.menu_dashboard,R.drawable.oh_my_coachs, R.drawable.menu_my_accounct,R.drawable.menu_events, 0,0,0,0,R.drawable.menu_reservation,
			R.drawable.menu_logout};
	
	//get title of the item navigation
	public static String getTitleItem(Context context, int posicao){		
		String[] titulos = context.getResources().getStringArray(R.array.nav_menu_items);  
		return titulos[posicao];
	}

}
