package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject{
    public NavigationUI (AppiumDriver driver){
        super(driver);
    }

    protected static String
            MY_LISTS_LINK;

    public void clickMyLists(){
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My List",
                5);
    }
}
