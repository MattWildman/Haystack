package com.haystack;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.haystack.dataAccess.ConnectionJDBCTemplate;
import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.MeetingJDBCTemplate;
import com.haystack.entities.Connection;
import com.haystack.entities.Meeting;

public class ConnectionMatchingTest extends ConnectionJDBCTemplate {

	List<Connection> candidates;
	Meeting target, target2;
	Connection c;
	MeetingJDBCTemplate mDAO;
	HaystackDBFacade hdbf;
	
	@Before
	public void setUp() {
		hdbf = new HaystackDBFacade();
		candidates = new ArrayList<Connection>();
		mDAO = new MeetingJDBCTemplate();
		target = hdbf.getMeeting(24); // "London meeting test"
		target2 = hdbf.getMeeting(22); // "distance test 1"
		c = this.getByMeetingId(target.getId());
	}
	
	@Test
	public void getCandidates() {
		Integer tId = target.getId();
		Integer userId = hdbf.getUserId(tId);
		Float lat = target.getContexts().get(0).getLocation().getLat();
		Float lon = target.getContexts().get(0).getLocation().getLongd();
		Integer rad = target.getContexts().get(0).getLocation().getRad();
		Date earliest = target.getContexts().get(0).getEarliest();
		Date latest = target.getContexts().get(0).getLatest();
		String SQL = "select * " +
				"from connections c " +
				"where c.conType = 'meeting' " +
				"and c.status = 'unresolved' " +
				"and c.userId <> ? " +
				"and exists " +
				   "(select * " +
				    "from contexts cx " +
					"where cx.earliest < ? " +
					"and cx.latest > ? " +
					"and cx.conId = c.id " +
					"and exists " +
					   "(select * " +
						"from locations l " +
						"where l.ctxId = cx.id " +
						"and ? + l.rad >= " +
							"(6371 * acos( cos( radians(?) ) * cos( radians( l.lat ) ) " +
							"* cos( radians( l.longd ) - radians(?) ) + " +
							"sin( radians(?) ) * sin( radians( l.lat ) ) ) ) * 1000" +
						"))";
		List <Connection> results = jdbcTemplateObject.query(SQL, new Object[]{userId,latest,earliest,rad,lat,lon,lat}, 
															 this.getRowMapper());
		Meeting result1 = (Meeting) mDAO.getByOwnerId("conId", results.get(0).getId()).get(0);
		result1 = hdbf.getMeeting(result1.getId());
		Meeting result2 = (Meeting) mDAO.getByOwnerId("conId", results.get(1).getId()).get(0);
		result2 = hdbf.getMeeting(result2.getId());
		System.out.println(result1.getTitle() + " and " + result2.getTitle());
		assertEquals("Wrong meeting retrieved", "distance test 1", result1.getTitle());
	}

}
