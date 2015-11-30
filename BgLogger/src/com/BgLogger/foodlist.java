package com.BgLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.BgLogger.R;

public class foodlist extends ActionBarActivity {

	public static String[] foodnames = { "BAKED GOODS", "Apple Muffin",
			"Apple Muffin No Sugar", "Banana Cake", "Banana Cake No Sugar",
			"Sponge cake", "Vanille Cake/Frosting",

			"BEVERAGES", "Apple Juice", "Coca Cola", "Cranberry Juice",
			"Fanta Orange Soda", "Gatorade", "Orange Juice",
			"Tomato Juice, canned",

			"BREADS", "Bagel, White", "Baguette, White", "Barley Bread",
			"Corn Tortilla", "Hamburger Bun", "Kaiser Roll",
			"Pumpernickel Bread", "Wheat Tortilla", "White Bread",
			"Whole Wheat Bread",

			"BREAKFAST CEREALS", "All-Bran", "Coco Pops", "Cornflakes",
			"Cream of Wheat", "Grapenuts", "Muesli", "Oatmeal", "Raisin Bran",

			"COOKIES AND CRACKERS", "Graham Crackers", "Rice cakes",
			"Shortbread", "Soda crackers",

			"DAIRY PRODUCTS", "Ice Cream", "Milk, Whole", "Milk, Skim",
			"Yogurt with Fruit",

			"FRUITS", "Apple", "Banana", "Dates, Dried", "Grapefruit",
			"Grapes", "Orange", "Peach", "Raisins", "Watermelon",

			"PASTA", "Fettucini", "Macaroni and Cheese", "Spaghetti, White",
			"Spaghetti, Wheat",

			"SNACKS", "Corn Chips", "Fruit Roll-Ups", "M & M's, Peanut",
			"Popcorn", "Potato Chips", "Snickers Bar",

			"VEGETABLES", "Carrots", "Peas", "Potato, Russet, Baked",
			"Sweet Potato"

	};

	public static String[] glyindex = { "", "48", "44", "47", "55", "46", "42",

	"", "44", "63", "68", "68", "78", "50", "38",

	"", "72", "95", "34", "52", "61", "73", "56", "30", "73", "71",

	"", "55", "77", "93", "66", "75", "66", "55", "61",

	"", "74", "82", "64", "74",

	"", "57", "41", "32", "33",

	"", "39", "62", "42", "25", "59", "40", "42", "64", "72",

	"", "32", "64", "46", "42",

	"", "42", "99", "33", "55", "51", "51",

	"", "35", "51", "111", "70"

	};

	public static String[] servingsize = { "", "60 grams", "60 grams",
			"60 grams", "60 grams", "63 grams", "111 grams",

			"", "250 mL", "250 mL", "250 mL", "250 mL", "250 mL", "250 mL",
			"250 mL",

			"", "70 grams", "30 grams", "30 grams", "50 grams", "30 grams",
			"30 grams", "30 grams", "50 grams", "30 grams", "30 grams",

			"", "30 grams", "30 grams", "30 grams", "250 grams", "30 grams",
			"30 grams", "250 grams", "30 grams",

			"", "25 grams", "25 grams", "25 grams", "25 grams",

			"", "50 grams", "250 mL", "250 mL", "200 grams",

			"", "120 grams", "120 grams", "60 grams", "120 grams", "120 grams",
			"120 grams", "120 grams", "60 grams", "120 grams",

			"", "180 grams", "180 grams", "180 grams", "180 grams",

			"", "50 grams", "30 grams", "30 grams", "20 grams", "50 grams",
			"60 grams",

			"", "80 grams", "80 grams", "150 grams", "150 grams"

	};

	public static String[] glyload = { "", "13", "9", "14", "12", "17", "24",

	"", "30", "16", "24", "23", "12", "12", "4",

	"", "25", "15", "7", "12", "9", "12", "7", "8", "10", "9",

	"", "12", "20", "23", "17", "16", "16", "13", "12",

	"", "14", "17", "10", "12",

	"", "6", "5", "4", "11",

	"", "6", "16", "18", "3", "11", "4", "5", "28", "4",

	"", "15", "32", "22", "17",

	"", "11", "24", "6", "6", "12", "18",

	"", "2", "4", "33", "22"

	};

	Button backbutton;
	ListView lv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glylist);
		title(getString(R.string.glycemic_load_list));

		Button backbutton = (Button) findViewById(R.id.back);
		backbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});

		ArrayList<Map<String, String>> list = buildData();
		String[] from = { "food", "gload" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, from, to);
		ListView glylist = (ListView)findViewById(R.id.glylist);
		glylist.setAdapter(adapter);
	}

	private ArrayList<Map<String, String>> buildData() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < foodnames.length; i++) {
			list.add(putData(foodnames[i], glyload[i]));
		}

		return list;
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		String message = "";
		message = message + foodnames[position] + "\n" + "Glycemic Index  "
				+ glyindex[position] + "\n" + "Glycemic Load  "
				+ glyload[position] + "\n" + "Serving Size  "
				+ servingsize[position];

		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
				.show();
	}

	private HashMap<String, String> putData(String food, String gload) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("food", food);
		item.put("gload", gload);
		return item;
	}
}