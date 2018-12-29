package com.fatimamostafa.app.newsviewsv2.ui.main;

public class NavItem {
    private String title;
    private int image;
    private boolean isSelected;
    private String fragment;



    public NavItem(String title, int image, boolean isSelected,String fragment) {
        this.title = title;
        this.image = image;
        this.isSelected = isSelected;
        this.fragment = fragment;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

}
