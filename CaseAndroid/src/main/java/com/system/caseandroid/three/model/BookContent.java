package com.system.caseandroid.three.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/9/30.
 */

public class BookContent
{
    public static List<Book> ITEMS = new ArrayList<Book>();
    public static class Book{
        public int id;
        public String title;
        public String desc;
        public Book(int id,String title,String desc){
            this.id = id;
            this.title = title;
            this.desc = desc;
        }
        @Override
        public String toString()
        {
            return title;
        }
    }

    public static Map<Integer,Book> ITEM_MAP = new HashMap<Integer, Book>();
    static {
        addItem(new Book(1,"疯狂Java讲义","一本全面、深入的Java学习图书"));
        addItem(new Book(2,"疯狂Android讲义","一本全面、深入的Java学习图书"));
        addItem(new Book(2,"疯狂JavaEE讲义","一本全面、深入的Java学习图书"));
    }
    private static void addItem(Book book){
        ITEMS.add(book);
        ITEM_MAP.put(book.id,book);
    }

}
