package com.haystack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.entities.Context;
import com.haystack.entities.Location;
import com.haystack.entities.Meeting;

public class LocationMatchingTest {

	private Location l1, l2, l3, l4;

	private double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
	    float pk = (float) (180/Math.PI);

	    float a1 = lat_a / pk;
	    float a2 = lng_a / pk;
	    float b1 = lat_b / pk;
	    float b2 = lng_b / pk;

	    float t1 = (float) (Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2));
	    float t2 = (float) (Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2));
	    float t3 = (float) (Math.sin(a1)*Math.sin(b1));
	    double tt = Math.acos(t1 + t2 + t3);

	    return 6366000 * tt;
	}

	@Before
	public void setUp() {
		HaystackDBFacade hdbf = new HaystackDBFacade();

		Meeting meeting1 = hdbf.getMeeting(20);
		Meeting meeting2 = hdbf.getMeeting(21);
		Context ctx1 = meeting1.getContexts().get(0);
		Context ctx2 = meeting2.getContexts().get(0);
		l1 = ctx1.getLocation();
		l2 = ctx2.getLocation();

		Meeting meeting3 = hdbf.getMeeting(22);
		Meeting meeting4 = hdbf.getMeeting(23);
		Context ctx3 = meeting3.getContexts().get(0);
		Context ctx4 = meeting4.getContexts().get(0);
		l3 = ctx3.getLocation();
		l4 = ctx4.getLocation();
	}

	@Test
	public void distanceTest() {
		String name1 = l1.getTitle() + ", " + l1.getArea();
		String name2 = l2.getTitle() + ", " + l2.getArea();
		Float lat1 = l1.getLat();
		Float lon1 = l1.getLongd();
		Float lat2 = l2.getLat();
		Float lon2 = l2.getLongd();
		double distance = gps2m(lat1, lon1, lat2, lon2);
		System.out.println(name1 + " " + lat1 + ", " + lon1 + " and " +
						   name2 + " " + lat2 + ", " + lon2 + " are " +
						   distance + "m apart");
	}

	@Test
	public void radiusTest() {
		String name1 = l3.getTitle() + ", " + l3.getArea();
		String name2 = l4.getTitle() + ", " + l4.getArea();
		Float lat1 = l3.getLat();
		Float lon1 = l3.getLongd();
		Float lat2 = l4.getLat();
		Float lon2 = l4.getLongd();
		double distance = gps2m(lat1, lon1, lat2, lon2);
		System.out.println(name1 + " " + lat1 + ", " + lon1 + " and " +
				   		   name2 + " " + lat2 + ", " + lon2 + " are " +
				   		   distance + "m apart");
		assertFalse(distance > 500);
	}

	@Test
	public void geoCalcTest() {
		String name1 = l3.getTitle() + ", " + l3.getArea();
		String name2 = l4.getTitle() + ", " + l4.getArea();
		Float lat1 = l3.getLat();
		Float lon1 = l3.getLongd();
		Float lat2 = l4.getLat();
		Float lon2 = l4.getLongd();

		Coordinate lat = Coordinate.fromDegrees(lat1);
		Coordinate lng = Coordinate.fromDegrees(lon1);
		Point LSE = Point.at(lat, lng);

		lat = Coordinate.fromDegrees(lat2);
		lng = Coordinate.fromDegrees(lon2);
		Point vEmbankment = Point.at(lat, lng);

		double distance = EarthCalc.gcdDistance(LSE, vEmbankment);
		System.out.println(name1 + " " + lat1 + ", " + lon1 + " and " +
		   		   name2 + " " + lat2 + ", " + lon2 + " are " +
		   		   distance + "m apart");
		assertFalse(distance > 400);
		assertFalse(distance < 200);
	}

}
