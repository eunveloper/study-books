package com.study.books.cleancode.function.obj;

public class PageData {

    public WikiPage getWikiPage() {
        return new WikiPage();
    }

    public String getHtml() {
        return null;
    }

    public boolean hasAttribute(String test) {
        return true;
    }

    public void setContent(String toString) {
    }
}
