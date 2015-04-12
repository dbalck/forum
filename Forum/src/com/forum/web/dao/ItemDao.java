package com.forum.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;

@Component("itemDao")
public class ItemDao {
	
	public ItemDao() {
		System.out.println("successfully loaded itemDao");
	}
	
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	// gets all the RssItems in the database, regardless of channel
	public List<RssItem> getAllItems() {
		return jdbc.query("select * from items", new RowMapper<RssItem>() {
			public RssItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				// build required fields
				return null;
			}
		});
	}
	
	// creates a row in the items table of the database based on an RssItem object
	public void create(RssItem item, int channelId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", item.getDescription());
		params.addValue("link", item.getLink());
		params.addValue("description", item.getDescription());
		
		params.addValue("channel_id", channelId);
		params.addValue("pubdate", item.getPubDate());
		params.addValue("category", item.getCategory());
		params.addValue("guid", item.getGuid());
		params.addValue("source", item.getSource());
		// params.addValue("enclosure", item.getEnclosure());
		params.addValue("author", item.getAuthor());
		params.addValue("comments", item.getComments());
		
		jdbc.update("insert into items (title, link, description, channel_id, pubdate, category, guid, source, enclosure, author, comments)"
				+ " values (:title, :link, :description, :channel_id, :pubdate, :category, :guid, :source, :enclosure, :author, :comments)", params);

	}
		
	public boolean exists(RssItem item) {
		return false;
	}
	
	public boolean exists(RssChannel channel) {
		return false;
	}
	



}
