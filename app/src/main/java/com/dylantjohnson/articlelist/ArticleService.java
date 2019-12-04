package com.dylantjohnson.articlelist;

import android.util.Log;
import androidx.core.util.Consumer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

class ArticleService {
    private static final String TAG = ArticleService.class.getCanonicalName();

    private URL mUrl;

    ArticleService(String url) throws MalformedURLException {
        mUrl = new URL(url);
    }

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
            String content = null;
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
                        } else if (name.equalsIgnoreCase("content:encoded")) {
                            content = parser.nextText();
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
                    articles.add(new Article(imageUrl, title, description, content, link));
                }
                type = parser.next();
            }
            callback.accept(articles);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
