package com.dylantjohnson.articlelist;

import android.util.Log;
import androidx.core.util.Consumer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * A utility class for fetching the Article RSS feed.
 */
class ArticleService {
    private static final String TAG = ArticleService.class.getCanonicalName();

    private URL mUrl;

    /**
     * Create an ArticleService instance.
     *
     * @param url the URL to use for fetching the RSS feed
     * @throws MalformedURLException if the URL is no good
     */
    ArticleService(String url) throws MalformedURLException {
        mUrl = new URL(url);
    }

    /**
     * Asynchronously fetch the RSS feed.
     * <p>
     * This will fetch the feed in a background thread, so be sure that the callback is aware to
     * avoid UI thread exceptions and such.
     *
     * @param callback the code to run after fetching the RSS feed
     */
    void get(Consumer<List<Article>> callback) {
        new Thread(() -> run(callback)).start();
    }

    private void run(Consumer<List<Article>> callback) {
        try {
            List<Article> articles = new ArrayList<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(mUrl.openConnection().getInputStream(), "UTF-8");
            int type = parser.getEventType();
            boolean inItem = false;
            URL imageUrl = null;
            String title = null;
            String description = null;
            URL link = null;
            while (type != XmlPullParser.END_DOCUMENT) {
                if (type == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("item")) {
                        inItem = true;
                    }
                    else if (inItem) {
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("title")) {
                            title = parser.nextText();
                        } else if (name.equalsIgnoreCase("description")) {
                            description = parser.nextText();
                        } else if (name.equalsIgnoreCase("media:content")) {
                            imageUrl = new URL(
                                    parser.getAttributeValue(null, "url"));
                        } else if (name.equalsIgnoreCase("link")) {
                            link = new URL(parser.nextText() + "?displayMobileNavigation=0");
                        }
                    }
                } else if (type == XmlPullParser.END_TAG
                        && parser.getName().equalsIgnoreCase("item")) {
                    inItem = false;
                    articles.add(new Article(imageUrl, title, description, link));
                }
                type = parser.next();
            }
            callback.accept(articles);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
