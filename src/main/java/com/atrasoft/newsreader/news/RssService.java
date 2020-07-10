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

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atrament
 */
@Service
public class RssService {

    private final FeedChannelRepository feedChannelRepository;
    private List<Entry> entries;
    private LocalDateTime lastUpdate;
    private final Logger log = LoggerFactory.getLogger(this.toString());

    @Value("${daysBack}")
    private int daysBack;

    @Value("${locale}")
    private String locale;

    @Value("${refresh}")
    private String updatePeriod;

    @Autowired
    public RssService(FeedChannelRepository feedChannelRepository) {
        this.entries = new ArrayList<>();
        this.feedChannelRepository = feedChannelRepository;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Scheduled(fixedDelayString = "${refresh}")
    public final void update() {
        log.info("Updating entries");
        List<Entry> newEntries = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, daysBack);
        Date limit = calendar.getTime();

        feedChannelRepository.getFeedsByActive(true).stream().forEach(feedChannel -> {
            try {
                SyndFeed feed = input.build(new XmlReader(new URL(feedChannel.getUrl())));
                log.info("Entries: {0}", feed.getEntries().size());
                feed.getEntries().stream().filter(sei -> (sei.getPublishedDate().after(limit))).forEach(sei
                        -> newEntries.add(new Entry(feedChannel.getName(), sei))
                );

            } catch (IOException | IllegalArgumentException | FeedException ex) {
                log.error(ex.getMessage());
            }
        });
        Collections.sort(newEntries);
        this.entries = newEntries;
        lastUpdate = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateString() {
        return lastUpdate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT).withLocale(Locale.forLanguageTag(locale)));
    }

    public long getMinutesFromLastUpdate() {
        return ChronoUnit.MINUTES.between(lastUpdate, LocalDateTime.now());
    }

    public long getUpdatePeriod() {
        return Long.parseLong(updatePeriod) / 60000;

    }

}
