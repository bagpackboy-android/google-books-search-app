package ooo.alphaman.googlebooksearch;

public class Books {


    private static String mBookId;
    private static String mSelfLink;
    private static String mTitle;
    private static String[] mAuthor;
    private static String mPublisher;


    public Books(String bookId, String selfLink, String title, String[] author, String publisher, String smallThumbnail, String thumbnail) {
        mBookId = bookId;
        mSelfLink = selfLink;
        mTitle = title;
        mAuthor = author;
        mPublisher = publisher;
        mSmallThumbnail = smallThumbnail;
        mThumbnail = thumbnail;
    }

    public static String getmBookId() {
        return mBookId;
    }

    public static String getmSelfLink() {
        return mSelfLink;
    }

    public static String getmTitle() {
        return mTitle;
    }

    public static String[] getmAuthor() {
        return mAuthor;
    }

    public static String getmPublisher() {
        return mPublisher;
    }

    public static String getmSmallThumbnail() {
        return mSmallThumbnail;
    }

    public static String getmThumbnail() {
        return mThumbnail;
    }

    private static String mSmallThumbnail;
    private static String mThumbnail;


}
