package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPK = "xpath://XCUIElementTypeLink[@name='{TITLE}']";
    }

    public iOSMyListsPageObject(AppiumDriver driver) { super(driver); }
}
