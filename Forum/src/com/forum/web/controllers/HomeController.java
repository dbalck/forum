package com.forum.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forum.web.parse.Article;
import com.forum.web.parse.Stream;
import com.forum.web.service.StreamService;

@Controller
public class HomeController {

	private StreamService streamService;
	
	@Autowired
	public void setFeedService(StreamService streamService) {
		this.streamService = streamService;
	}

	@Transactional
	@RequestMapping("/")
	public String showHome(Model model) {
		Set<Stream> streams = streamService.getAllStreams();
		Set<Article> articles = streamService.getAllArticles();
				
		model.addAttribute("streams", streams);
		model.addAttribute("articles", articles);
		return "home";
	}
	

}
