package net.siji.imageSliderViewPager;

public class PagesLessException extends Exception {
    @Override
    public String getMessage() {
        return "Pages must equal or larger than 2";
    }
}
