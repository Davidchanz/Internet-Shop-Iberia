package com.InternetShopIberia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchBarController {
    @GetMapping("/event-count")
    public String getEventCount(@RequestParam("searchInput") String searchInput, ModelMap map) {
        // TODO: retrieve the new value here so you can add it to model map
        map.addAttribute("numDeviceEventsWithAlarm", searchInput);

        System.out.println(searchInput);
        // change "myview" to the name of your view
        return "headerBar :: #eventCount";
    }
}
