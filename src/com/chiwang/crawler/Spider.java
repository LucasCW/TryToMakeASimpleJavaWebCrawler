package com.chiwang.crawler;

import java.util.*;

public class Spider {
	// Fields
	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	
	public void search(String url, String searchWord) {
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			if(this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			leg.crawl(currentUrl);
			boolean success = leg.searchForWord(searchWord);
			if(success) {
				System.out.println(String.format("**Success** Word %s found at %s",
						searchWord, currentUrl));
				break;
			}
			this.pagesToVisit.addAll(leg.getLinks());
			System.out.println(String.format("**Done** Visited %s web page(s)",
					this.pagesVisited.size()));
		}
	}
	
	
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
			if(this.pagesVisited.contains(nextUrl)) {
				System.out.println("duplicated page found");
			}
		} while(this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
}
