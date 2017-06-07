package net.gumcode.malangvoice.utilities;

import android.text.Html;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.gumcode.malangvoice.config.Constants;
import net.gumcode.malangvoice.model.Page;
import net.gumcode.malangvoice.model.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A. Fauzi Harismawan on 1/1/2016.
 */
public class JSONParser {

    public static List<Post> parsePostList(InputStream inputStream) {
        Log.d(Constants.LOG_TAG, "GET POST COMPLETE");
        List<Post> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(inputStream);
            for (JsonNode obj : rootNode) {
                int id = -1;
                String imgUrl = "/error";
                String title = "error";
                String content = "error";
                String date = "error";
                String author = "error";
                String category;
                String link = "error";

                if (obj.get("ID") != null) {
                    id = obj.get("ID").asInt();
                }

                if (obj.get("featured_image").get("source") != null) {
                    imgUrl = obj.get("featured_image").get("source").asText();
                }

                if (obj.get("title") != null) {
                    title = Html.fromHtml(obj.get("title").asText()).toString();
                }

                if (obj.get("content") != null) {
                    content = obj.get("content").asText();
//                    content = Jsoup.parse(obj.get("content").asText()).html();
                }

                if (obj.get("date") != null) {
                    date = obj.get("date").asText();
                }

                if (obj.get("author") != null) {
                    author = obj.get("author").get("name").asText();
                }

                if (obj.get("link") != null) {
                    link = obj.get("link").asText();
                }

                if (obj.get("terms").get("category") != null) {
                    category = obj.get("terms").get("category").get(0).get("name").asText();
                } else {
                    category = "Other";
                }
                list.add(new Post(id, imgUrl, title, content, date, author, category, link));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(Constants.LOG_TAG, "PARSE POST COMPLETE");
        return list;
    }

    public static List<Post> parseAdsList(InputStream inputStream) {
        Log.d(Constants.LOG_TAG, "GET ADS COMPLETE");
        List<Post> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(inputStream);
            for (JsonNode obj : rootNode) {
                int id = -1;
                String imgUrl = "/error";
                String title = "error";
                String content = "error";
                String link = "error";

                if (obj.get("id") != null) {
                    id = obj.get("id").asInt();
                }

                if (obj.get("title") != null) {
                    title = obj.get("title").asText();
                }

                if (obj.get("description") != null) {
                    content = obj.get("description").asText();
                }

                if (obj.get("img_url") != null) {
                    imgUrl = obj.get("img_url").asText();
                }

                if (obj.get("link") != null) {
                    link = obj.get("link").asText();
                }
                list.add(new Post(id, imgUrl, title, content, link));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(Constants.LOG_TAG, "PARSE POST COMPLETE");
        return list;
    }

    public static Post parsePost(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            int id = -1;
            String imgUrl = "/error";
            String title = "error";
            String content = "error";
            String date = "error";
            String author = "error";
            String category;
            String link = "error";

            JsonNode rootNode = mapper.readTree(inputStream);
            if (rootNode.get("ID") != null) {
                id = rootNode.get("ID").asInt();
            }

            if (rootNode.get("featured_image").get("source") != null) {
                imgUrl = rootNode.get("featured_image").get("source").asText();
            }

            if (rootNode.get("title") != null) {
                title = rootNode.get("title").asText();
            }

            if (rootNode.get("content") != null) {
                content = rootNode.get("content").asText();
//                Document temp = Jsoup.parse(rootNode.get("content").asText());
//                temp.select("div").last().remove();
//                content = temp.body().html();
            }

            if (rootNode.get("date") != null) {
                date = rootNode.get("date").asText();
            }

            if (rootNode.get("author") != null) {
                author = rootNode.get("author").get("name").asText();
            }

            if (rootNode.get("link") != null) {
                link = rootNode.get("link").asText();
            }

            if (rootNode.get("terms").get("category") != null) {
                category = rootNode.get("terms").get("category").get(0).get("name").asText();
            } else {
                category = "Other";
            }
            return new Post(id, imgUrl, title, content, date, author, category, link);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Page parsePage(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            int id = -1;
            String title = "error";
            String content = "error";

            JsonNode rootNode = mapper.readTree(inputStream);
            if (rootNode.get("ID") != null) {
                id = rootNode.get("ID").asInt();
            }

            if (rootNode.get("title") != null) {
                title = rootNode.get("title").asText();
            }

            if (rootNode.get("content") != null) {
                Document temp = Jsoup.parse(rootNode.get("content").asText());
                temp.select("div").last().remove();
                content = temp.body().html();
            }
            return new Page(id, title, content);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
