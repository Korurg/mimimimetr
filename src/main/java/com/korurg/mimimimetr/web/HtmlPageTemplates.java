package com.korurg.mimimimetr.web;

public enum HtmlPageTemplates {
    INDEX("index"),
    TOP("top"),
    VOTE("vote"),
    UPLOAD("upload");

    private final String name;

    HtmlPageTemplates(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String redirect() {
        return "redirect:/" + name;
    }
}
