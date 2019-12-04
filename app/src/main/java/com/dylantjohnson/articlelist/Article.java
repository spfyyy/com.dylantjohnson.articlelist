package com.dylantjohnson.articlelist;

import java.io.Serializable;
import java.net.URL;

/**
 * A data class for representing articles retrieved from the RSS feed.
 */
class Article implements Serializable {
    private URL mImageUrl;
    private String mTitle;
    private String mDescription;
    private URL mLink;

    /**
     * Construct a new article.
     *
     * @param imageUrl the URL for the article's picture
     * @param title the title of the article
     * @param description the summary of the article
     * @param link the URL of the article's landing page
     */
    Article(URL imageUrl, String title, String description, URL link) {
        mImageUrl = imageUrl;
        mTitle = title;
        mDescription = description;
        mLink = link;
    }

    /**
     * Retrieve the image URL.
     *
     * @return the URL of the article picture
     */
    URL getImageUrl() {
        return mImageUrl;
    }

    /**
     * Retrieve the title.
     *
     * @return the title of the article
     */
    String getTitle() {
        return mTitle;
    }

    /**
     * Retrieve the description.
     *
     * @return the summary of the article
     */
    String getDescription() {
        return mDescription;
    }

    /**
     * Retrieve the article URL
     *
     * @return the URL of the article
     */
    URL getLink() {
        return mLink;
    }
}
