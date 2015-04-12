package com.forum.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forum.web.parse.Stream;
import com.forum.web.parse.WebCrawler;
import com.forum.web.service.StreamService;

@Controller
public class HomeController {

	private StreamService streamService;
	
	@Autowired
	public void setFeedService(StreamService streamService) {
		this.streamService = streamService;
	}

	@RequestMapping("/")
	public String showHome(Model model) {
		
		// List<RssItem> items = feedService.getItems(); 
		// model.addAttribute("items", items);
		return "home";
	}
	
	@RequestMapping("fetchrss")
	public String fetchRss() {
		
		RssController instance = RssController.getInstance();
		

		return "fetchrss";
	}
}
