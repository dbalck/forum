package com.forum.web.parse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {

	private List<String> atomLinks;
	private List<String> rssLinks;
	private RssParser rssParser = new RssParser();
	private AtomParser atomParser = new AtomParser();

	public WebCrawler(List<String> rssLinks, List<String> atomLinks) {
		this.rssLinks = rssLinks;
		this.atomLinks = atomLinks;

	}

	private URL getUrl(String url) throws MalformedURLException {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			System.out.println(e);
			throw new MalformedURLException();
		}
	}

	private InputStream getInput(URL link) {
		try {
			return link.openStream();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(4);
			return null;
		}
	}

	public List<Stream> parseLinks() {
		List<Stream> streams = new ArrayList<Stream>();

		try {
			for (String link : rssLinks) {
				URL url = getUrl(link);
				InputStream input = getInput(url);
				streams.add(rssParser.parseLink(input));
			}
			
			for (String link : atomLinks) {
				URL url = getUrl(link);
				InputStream input = getInput(url);
				streams.add(atomParser.parseLink(input));
			}


		} catch (MalformedURLException e) {
			System.out.println("error trying to read that url");
			System.out.println(e);
			System.exit(1);

		} 
		return streams;
	}
}
