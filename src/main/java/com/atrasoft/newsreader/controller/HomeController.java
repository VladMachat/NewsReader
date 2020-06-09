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
package com.atrasoft.newsreader.controller;

import com.atrasoft.newsreader.news.RssService;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/", "/home", "/rss/clanky"})
public class HomeController {

    private final RssService rssService;

    @Autowired
    public HomeController(RssService rssService) {
        this.rssService = rssService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Map<String, Object> model) {
        // List<Entry> entries = rssRepo.findAll();
        Logger.getLogger(this.toString()).info("home visited");
        model.put("entries", rssService.getEntries());
        model.put("lastUpdate", rssService.getLastUpdateString());
        model.put("minutesAgo", rssService.getMinutesAgo());
        model.put("updatePeriod", rssService.getUpdatePeriod());
        return "home";
    }

}
