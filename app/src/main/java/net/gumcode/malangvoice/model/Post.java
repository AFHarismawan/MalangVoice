package net.gumcode.malangvoice.model;

/**
 * Created by A. Fauzi Harismawan on 1/1/2016.
 */
public class Post {

    private int id;
    private String imgUrl;
    private String title;
    private String desc;
    private String time;
    private String author;
    private String category;
    private String link;

    public Post(int id, String imgUrl, String title, String desc, String time, String author, String category, String link) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.author = author;
        this.category = category;
        this.link = link;
    }

    public Post(int id, String imgUrl, String title, String desc, String author) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.desc = desc;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getLink() {
        return link;
    }
}
