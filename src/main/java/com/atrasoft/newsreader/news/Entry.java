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

import com.rometools.rome.feed.synd.SyndEntry;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import org.jsoup.Jsoup;

/**
 *
 * @author Atrament
 */
public class Entry implements Comparable<Entry> {

    private String title;
    private String source;
    private String description;
    private String author;
    private LocalDateTime publishedDate;
    private String details;
    private String link;
    private String thumbnail;

    Entry(String source, SyndEntry sei) {
        this.title = filterOutHtml(sei.getTitle());
        this.author = filterOutHtml(sei.getAuthor());
        this.description = filterOutHtml(sei.getDescription().getValue());
        if (sei.getPublishedDate() == null) {
            this.publishedDate = LocalDateTime.now();
        } else {
            this.publishedDate = LocalDateTime.ofInstant(sei.getPublishedDate().toInstant(), ZoneId.systemDefault());
        }

        this.link = sei.getLink();
        this.source = source;
        if ((sei.getAuthor() == null) || (sei.getAuthor().isEmpty())) {
            this.details = source;
        } else {
            this.details = source + " : " + sei.getAuthor();
        }

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getDate() {
        return publishedDate;
    }

    public void setDate(LocalDateTime date) {
        this.publishedDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int compareTo(Entry o) {
        return o.publishedDate.compareTo(this.publishedDate);
    }

    private String filterOutHtml(String text) {
        return Jsoup.parse(text).text();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPublishedDateString() {
        //TODO get the locale from properties
        return publishedDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT).withLocale(Locale.forLanguageTag("cs-CZ")));
    }

}
