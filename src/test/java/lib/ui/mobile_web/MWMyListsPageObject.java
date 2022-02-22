package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPK = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(),'{TITLE}')]";
        //REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(),'{TITLE}')]/../../[contains(@class, 'watched')]";
        REMOVE_FROM_SAVED_BUTTON = "css:.mw-ui-icon-wikimedia-unStar-progressive";
        TITLE_WITH_WATCHSTAR = "css:.page-summary.with-watchstar";
    }

    public MWMyListsPageObject (RemoteWebDriver driver){
        super(driver);
    }

    @Override
    public void waitForArticleToDisappearByTitle(String article_title) {
        this.waitForElementNotPresent(
                ARTICLE_BY_TITLE_TPK.replace("{TITLE}", article_title),
                "Saved article still present with title " + article_title,
                15);
    }
}
