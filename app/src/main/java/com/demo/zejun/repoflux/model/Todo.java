package com.demo.zejun.repoflux.model;

public class Todo implements Cloneable, Comparable<Todo> {

    long id;
    boolean complete;
    String text;

    public Todo(long id, String text) {
        this(id, text, false);
    }

    public Todo(long id, String text, boolean complete) {
        this.id = id;
        this.text = text;
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    @Override
    public Todo clone() throws CloneNotSupportedException {
        return new Todo(id, text, complete);
    }

    @Override
    public int compareTo(Todo another) {
        if (id == another.id) {
            return 0;
        } else if (id < another.getId()) {
            return -1;
        } else {
            return 1;
        }
    }
}
