package com.forum.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forum.web.parse.Stream;
import com.forum.web.parse.WebCrawler;
import com.forum.web.service.StreamService;

@Controller
public class AdminController {

	
	private StreamService streamService;
	
	@Autowired
	public void setFeedService(StreamService streamService) {
		this.streamService = streamService;
	}

	@RequestMapping("admin")
	public String showAdmin() {
		return "admin";
	}
	
	@RequestMapping("fetchrss")
	public String fetchRss() {		
		return "fetchrss";
	}
	
	@RequestMapping(value="dofetch", method=RequestMethod.POST)
	public String doFetch(Model model, @RequestParam("url") String url, @RequestParam("type") String type) {
		WebCrawler wc = null;
		List<String> links = new ArrayList<String>();
		if (type.equals("rss")) {
			links.add(url);
			wc = new WebCrawler(links, null);
		} else {
			links.add(url);
			wc = new WebCrawler(null, links);
		}
		List<Stream> streams = wc.parseLinks();
		streamService.createStreams(streams);
		return "rssfetched";
	}

}
