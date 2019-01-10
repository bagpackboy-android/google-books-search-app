package ooo.alphaman.googlebooksearch;

public class Books {

    private static String mAuthor;
    private static String mTitle;
    private static String mLanguage;
    private static String mPublisher;
    private static String mUrl;
    private static String mThumbnail;

    private Books(String author, String title, String language, String publisher, String url, String thumbnail){
        mAuthor = author;
        mTitle = title;
        mLanguage = language;
        mPublisher = publisher;
        mUrl = url;
        mThumbnail = thumbnail;
    }

    public static String getmAuthor() {
        return mAuthor;
    }

    public static String getmTitle() {
        return mTitle;
    }

    public static String getmLanguage() {
        return mLanguage;
    }

    public static String getmPublisher() {
        return mPublisher;
    }

    public static String getmUrl() {
        return mUrl;
    }

    public static String getmThumbnail() {
        return mThumbnail;
    }





}
