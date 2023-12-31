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

import com.atrasoft.newsreader.news.FeedChannel;
import com.atrasoft.newsreader.news.FeedChannelRepository;
import com.atrasoft.newsreader.news.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Atrament <atrament666@gmail.com>
 */
@Controller
public class ChannelController {

    @Autowired
    private FeedChannelRepository feedChannelRepository;
    @Autowired
    private RssService rssService;

    @RequestMapping("/admin/channel/detail")
    public String getDetails(@RequestParam Integer id, Model model) {
        model.addAttribute("channel", feedChannelRepository.getFeedChannelById(id));
        return "NewFeedChannel";
    }

    @RequestMapping(value = "/admin/channel/save", method = RequestMethod.GET)
    public String addNew(Model model) {
        model.addAttribute("channel", new FeedChannel());
        return "NewFeedChannel";
    }

    @RequestMapping(value = "/admin/channel/save", method = RequestMethod.POST)
    public String save(@ModelAttribute FeedChannel channel, Model model) {
        model.addAttribute("channel", channel);
        feedChannelRepository.save(channel);
        rssService.update();
        return "redirect:/admin/list";
    }

    @RequestMapping("/admin/channel/delete")
    public String delete(@RequestParam Integer id, Model model) {
        FeedChannel channel = feedChannelRepository.getFeedChannelById(id);
        feedChannelRepository.delete(channel);
        rssService.update();
        return "redirect:/admin/list";
    }
}
