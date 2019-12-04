package com.dylantjohnson.articlelist;

import java.io.Serializable;
import java.net.URL;

class Article implements Serializable {
    private URL mImageUrl;
    private String mTitle;
    private String mDescription;
    private String mContent;
    private URL mLink;

    Article(URL imageUrl, String title, String description, String content, URL link) {
        mImageUrl = imageUrl;
        mTitle = title;
        mDescription = description;
        mContent = content;
        mLink = link;
    }

    URL getImageUrl() {
        return mImageUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mDescription;
    }

    String getContent() {
        return mContent;
    }

    URL getLink() {
        return mLink;
    }
}
