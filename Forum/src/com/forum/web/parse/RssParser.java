package com.forum.web.parse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.forum.web.rss.Enclosure;
import com.forum.web.rss.Image;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;
import com.forum.web.rss.SkipDays;
import com.forum.web.rss.TextInput;

public class RssParser extends Parser {
	public static final int RSS_STR_MAX_LEN = 1000;
	public static final int RSS_LANG_MAX_LEN = 20;
	public static final int RSS_NAME_MAX_LEN = 50;

		
	// XML input factory for streamReaders
	private XMLInputFactory factory = XMLInputFactory.newInstance();

	// Creates a new Rss reader that can aggregate several RssFeeds.
	public RssParser() {}
	
	// Method for getting the data from a URL
	@SuppressWarnings("unchecked")
	public Stream parseLink(InputStream input) {
		if (input == null) {
			return null;
		}
	
		// the Channel that will be returned
		RssChannel channel = null;
		
		// represents the items that will be included in this channel
		Set<RssItem> channelItems = new HashSet<RssItem>();
		
		// a stack to represent the dom
		Stack<HashMap<String, Object>> dom = new Stack<HashMap<String, Object>>();
		
		// a wrapper object for when popping things off the stack
		HashMap<String, Object> top = null;

		try {
			
			// File file = new File("testRss.xml"); 
			// InputStream stream = new FileInputStream(file);
			
			// System.out.println("reading file testRss.xml");

			// set up XML stream reader from the URL
			XMLStreamReader reader = factory.createXMLStreamReader(input);		

			while (reader.hasNext()) {
				reader.next();
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					
					// get the xml tag name 
					String currentElement = reader.getLocalName();
					switch (currentElement) {
					
					case "rss":
						break;

					case "channel":
						dom.push(new HashMap<String, Object>());
						break;
						
					case "item":
						dom.push(new HashMap<String, Object>());
						break;
					
					case "image":
						dom.push(new HashMap<String, Object>());
						break;

					case "textInput":
						dom.push(new HashMap<String, Object>());
						break;
					
					case "enclosure":
						dom.push(new HashMap<String, Object>());
						break;
						
					case "skipHours":
						dom.push(new HashMap<String, Object>());
						break;
						
					case "skipDays":
						dom.push(new HashMap<String, Object>());
						break;

						
					// used by channel and almost all it's possible sub-elements
					case "title":
						top = dom.pop();
						top.put("title", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
					
					// used by channel and almost all it's possible sub-elements
					case "link":
						top = dom.pop();
						top.put("link", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// used by channel and almost all it's possible sub-elements
					case "description":
						top = dom.pop();
						top.put("description", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// unique to a channel
					case "language":
						top = dom.pop();
						top.put("language", validateLength(reader.getElementText(), RSS_LANG_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "copyright":
						top = dom.pop();
						top.put("copyright", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "managingEditor":
						top = dom.pop();
						top.put("managingEditor", validateLength(reader.getElementText(), RSS_NAME_MAX_LEN));
						dom.push(top);
						top = null;
						break;
					
					// unique to a channel
					case "webMaster":
						top = dom.pop();
						top.put("webMaster", validateLength(reader.getElementText(), RSS_NAME_MAX_LEN));
						dom.push(top);
						top = null;
						break;
					
					// used by channel, item
					case "pubDate":
						top = dom.pop();
						top.put("pubDate", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "lastBuildDate":
						top = dom.pop();
						top.put("lastBuildDate", reader.getElementText());
						dom.push(top);
						top = null;
						break;
						
					// used by channel, item
					case "category":
						top = dom.pop();
						top.put("category", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// unique to a channel
					case "generator":
						top = dom.pop();
						top.put("generator", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "cloud":
						top = dom.pop();
						top.put("cloud", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
					
					// unique to channel
					case "docs":
						top = dom.pop();
						top.put("docs", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "ttl":
						top = dom.pop();
						top.put("ttl", Integer.valueOf(reader.getElementText()));
						dom.push(top);
						top = null;
						break;

					// unique to the item sub-element
					case "author":
						top = dom.pop();
						top.put("author", validateLength(reader.getElementText(), RSS_NAME_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to the item sub-element
					case "comments":
						top = dom.pop();
						top.put("comments", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// unique to the item sub-element
					case "guid":
						top = dom.pop();
						top.put("guid", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// unique to the item sub-element
					case "source":
						top = dom.pop();
						top.put("source", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					// unique to the enclosure sub-element
					case "length":
						top = dom.pop();
						top.put("length", Integer.valueOf(reader.getElementText()));
						dom.push(top);
						top = null;
						break;	
					
					// unique to the enclosure sub-element
					case "type":
						top = dom.pop();
						top.put("type", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;

					// unique to enclosure and image
					case "url":
						top = dom.pop();
						top.put("url", validateLength(reader.getElementText(), RSS_STR_MAX_LEN));
						dom.push(top);
						top = null;
						break;
						
					/* USELESS WASTE OF TIME
					case "hour":
						top = dom.pop();
						if (!top.containsKey("skipHours")) {
							top.put("skipHours", new ArrayList<Integer>());
						}
						if (top.get("skipHours") instanceof ArrayList<?>) {
							((ArrayList<Integer>) top.get("skipHours")).add(Integer.parseInt(reader.getElementText()));
						}
						dom.push(top);
						top = null;
						break;
					*/
						
					case "day":
						top = dom.pop();
						if (!top.containsKey("skipDays")) {
							top.put("skipDays", new ArrayList<String>());
						}
						if (top.get("skipDays") instanceof ArrayList<?>) {
							((ArrayList<String>) top.get("skipDays")).add(reader.getElementText());
						}
						dom.push(top);
						top = null;
						break;

					}

					// the item/channel is finished, we need to use what we've
					// gathered and make an
					// object out of it
				} else if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {

					// get what type of element is ending
					String endElement = reader.getLocalName();
					
					// create an object depending on what it is
					switch(endElement) {
					
					case "rss":
						break;
						
					// the channel is closed, create an RssChannel out of its sub-elements
					case "channel":
						top = dom.pop();
						
						// if the channel has the minimum required elements, get to work!
						if (top.containsKey("title")
								&& top.containsKey("link")
								&& top.containsKey("description")) {
							
							// created builder from core required elements
							channel = new RssChannel(
									(String) top.get("title"), 
									(String) top.get("link"), 
									(String) top.get("description"));
							
							// add optional elements as they exist
							if (top.containsKey("pubDate")) {
								channel.setPubDate(parseDateRss((String) top.get("pubDate")));
							}
							
							if (top.containsKey("lastBuildDate")) {
								channel.setLastBuildDate(parseDateRss((String) top.get("lastBuildDate")));
							}
							
							/* USELESS WASTE OF TIME
							if (top.containsKey("skipHours")) {
								channel.setSkipHours((ArrayList<Integer>) top.get("skipHours"));
							}
							
							if (top.containsKey("skipDays")) {
								channel.setSkipDays(new SkipDays((ArrayList<String>) top.get("skipDays")));
							} */
							
							if (top.containsKey("language")) {
								channel.setLanguage((String) top.get("language"));
							}

							if (top.containsKey("category")) {
								channel.setCategory((String) top.get("category"));
							}
														
							if (top.containsKey("cloud")) {
								channel.setCloud((String) top.get("cloud"));
							}
							
							if (top.containsKey("copyright")) {
								channel.setCopyright((String) top.get("copyright"));
							}
							
							if (top.containsKey("docs")) {
								channel.setDocs((String) top.get("docs"));
							}

							if (top.containsKey("generator")) {
								channel.setGenerator((String) top.get("generator"));
							}
							
							if (top.containsKey("image")) {
								channel.setImage((Image) top.get("Image"));
							}
							
							if (top.containsKey("managingEditor")) {
								channel.setManagingEditor((String) top.get("managingEditor"));
							}

							if (top.containsKey("rating")) {
								channel.setRating((String) top.get("rating"));
							}
							
							if (top.containsKey("textInput")) {
								TextInput ti = (TextInput) top.get("textInput");
								channel.addTextInput(ti);
							}

							if (top.containsKey("ttl")) {
								channel.setTtl((Integer) top.get("ttl"));
							}

							if (top.containsKey("webMaster")) {
								channel.setWebMaster((String) top.get("webMaster"));
							}

							channel.addItems(channelItems);

						}
						top = null;
						break;
						
					// an item has closed, create an RssItem out of it's sub-elements
					case "item":
						top = dom.pop();
						RssItem item;
						
						// check if a required field is missing
						if (top.containsKey("title") || top.containsKey("description")) {
							item = new RssItem(
									(String) top.get("title"), 
									(String) top.get("link"), 
									(String) top.get("description"),
									(String) top.get("guid"));
							
							// add optional fields if they exist
							if (top.containsKey("author")) {
								item.setAuthor((String) top.get("author"));
							}
							
							if (top.containsKey("category")) {
								item.setCategory((String) top.get("category"));
							}

							if (top.containsKey("comments")) {
								item.setComments((String) top.get("comments"));
							}
							
//							if (top.containsKey("enclosure")) {
//								item.setEnclosure((Enclosure) top.get("enclosure"));
//							}
														
							if (top.containsKey("pubDate")) {
								item.setPubDate(parseDateRss((String) top.get("pubDate")));
							}
							
							if (top.containsKey("source")) {
								item.setSource((String) top.get("source"));
							}
							
							channelItems.add(item);

						} else {
							System.out.println("bad item");
						}
						top = null;
						break;
						
					// create an image object from previous data and put it into
					// its supertag's hashmap
					case "image":
						top = dom.pop();
						Image image;
						
						// if the required fields exist...
						if (top.containsKey("title") && top.containsKey("url") && top.containsKey("link")) {
							image = new Image((String) top.get("title"), 
									(String) top.get("link"), 
									(String) top.get("url"));
							if (top.containsKey("description")) {
								image.setDescription((String) top.get("description"));
							}
							
							if (top.containsKey("height")) {
								image.setHeight(Integer.parseInt((String) top.get("height")));
							}

							if (top.containsKey("width")) {
								image.setWidth(Integer.parseInt((String) top.get("width")));
							}
														
							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							top.put("image", image);
							dom.push(top);
							top = null;
						}
						break;
						
					// a textInput has closed. Create a TextInput object and add
					// it to the dom in its super's Object hashmap
					case "textInput":
						top = dom.pop();
						TextInput textInput;
						
						if (top.containsKey("title") 
								&& top.containsKey("link") 
								&& top.containsKey("description") 
								&& top.containsKey("name")) {
							
							// and finally build the Image object
							textInput = new TextInput(
									(String) top.get("title"),
									(String) top.get("link"),
									(String) top.get("description"),
									(String) top.get("name"));
							
							
							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							top.put("textInput", textInput);
							dom.push(top);
							top = null;
						}
						break;
	
					// and enclosure has closed. create an object and add it to
					// its super element's hashmap
					case "enclosure":
						top = dom.pop();
						Enclosure enclosure;
						
						if (top.containsKey("length")
								&& top.containsKey("type")
								&& top.containsKey("url")) {
							enclosure = new Enclosure((Integer) top.get("length"),
									(String) top.get("type"),
									(String) top.get("url"));
							
							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							top.put("enclosure", enclosure);
							dom.push(top);
							top = null;
						}
						break;
						
					case "skipHours":
						break;
					
					/* USELESS WASTE OF TIME
					case "skipDays":
						break;
					*/
					}
				}

			}

		} catch (XMLStreamException e) {
			System.out.print("Error when parsing xml: ");
			System.out.println(e);
			System.exit(1);

		} 
		
		// print out diagnostics for debugging
//		if (channel != null) {
//			System.out.println(channel.toString());
//			System.out.println("Items in channel:");
//			for (RssItem item: channel.getItems()) {
//				System.out.println(item.toString());
//			}
//			System.out.println("channel's item count: " + channel.getItems().size());
//			System.out.println("parsing complete.");
//
//		} else {
//			System.out.println("parsing failed. Channel returned is a null object");
//		}
//		
		return (Stream) channel;
	}
	
}
