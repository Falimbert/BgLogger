package com.BgLogger.activity.graphs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrawGraph extends ActionBarActivity {

	private ArrayList<String> dates;
	private ArrayList<Double> readings;
	private String title;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
	private Calendar c = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		draw();
		if(title.equals("Fasting Blood Sugar")){
			title(getString(R.string.fasting_blood_sugar_report));
		}
		else if(title.equals("Random Blood Sugar")){
			title(getString(R.string.random_blood_sugar_report));
		}
		else{
			title(getString(R.string.hba1c_report));
		}
	}

	@SuppressWarnings("unchecked")
	public void draw() {
		ArrayList<String> dates;
		ArrayList<Double> readings;

		// assign the data passed to the intent
		dates = getIntent().getStringArrayListExtra("dates");
		readings = (ArrayList<Double>) getIntent().getSerializableExtra(
				"readings");
		title = getIntent().getStringExtra("test");

		// create a new time series for the graph
		TimeSeries series = new TimeSeries(title + " Results");

		// populate the series with data retrieved from the database
		for (int i = dates.size() - 1; i >= 0; i--) {
			// parse the date object to calendar date time
			try {
				c.setTime(sdf.parse(dates.get(i)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// add the current point to the time series
			series.add(c.getTime(), readings.get(i));

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series); // initialize the set of data to be drawn

		// Now create the renderer to control how the chart looks like
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(4);
		renderer.setColor(Color.WHITE);

		// add point markers
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setPointStrokeWidth(3);
		renderer.setDisplayChartValues(true);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		// avoid black border
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent
																		// margins
		// Enable Pan on two axis
		mRenderer.setPanEnabled(true, true);
		mRenderer.setShowGrid(true); // show the grid
		mRenderer.setXTitle("Day");
		if (title.equals("HBA1C"))
			mRenderer.setYTitle("%");
		else
			mRenderer.setYTitle("mg/dl");
		mRenderer.setZoomButtonsVisible(true);

		GraphicalView chartView = ChartFactory.getTimeChartView(DrawGraph.this,
				dataset, mRenderer, "yyyy/MM/dd hh:mm aa");// combines the dataset with
													// the renderer and returns
													// a view
		
		
		LinearLayout chart_container = (LinearLayout) findViewById(R.id.Chart_layout);

		//set the created chart view to the linear layout of the layout created for the graph
		chart_container.addView(chartView, 0);

	}

}
