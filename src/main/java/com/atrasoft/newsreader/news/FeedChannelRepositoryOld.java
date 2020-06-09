/* 
 * The MIT License
 *
 * Copyright 2020 Atrament.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.atrasoft.newsreader.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Atrament
 */

public class FeedChannelRepositoryOld implements Serializable {

    private List<FeedChannel> feeds = new ArrayList<>();

    public FeedChannelRepositoryOld() {
        
            feeds.add(new FeedChannel("Root.cz - Články", "https://www.root.cz/rss/clanky/"));
            feeds.add(new FeedChannel("Root.cz - Zprávičky", "https://www.root.cz/rss/zpravicky/"));
            feeds.add(new FeedChannel("Technet.cz", "https://servis.idnes.cz/rss.aspx?c=technet"));
            feeds.add(new FeedChannel("Lupa.cz", "https://www.lupa.cz/rss/clanky/"));
            feeds.add(new FeedChannel("Svět hardware", "https://www.svethardware.cz/export.jsp?format=rss2"));
            feeds.add(new FeedChannel("Zdroják.cz", "https://www.zdrojak.cz/feed"));
            feeds.add(new FeedChannel("Databázeknih.cz", "https://www.databazeknih.cz/rss/news.xml"));
            feeds.add(new FeedChannel("Itnetwork.cz", "https://www.itnetwork.cz/script/system/rss"));
            feeds.add(new FeedChannel("Science Daily", "https://rss.sciencedaily.com/all.xml"));
        
    }

    public List<FeedChannel> findAll() {
        return feeds;
    }

    public void setFeeds(List<FeedChannel> feeds) {
        this.feeds = feeds;
    }

    public List<FeedChannel> getActiveFeeds() {
        return feeds.stream().filter(x -> x.isActive()).collect(Collectors.toList());
    }

}
