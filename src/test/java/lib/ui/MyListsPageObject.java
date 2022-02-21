package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPK,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPK.replace("{TITLE}", article_title);
    }

    private String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject (RemoteWebDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
    this.waitForElementAndClick(
            folder_name_xpath,
            "Cannot find folder by name" + name_of_folder,
            5);
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15);
    }

    public void swipeByArticleToDelete(String article_title){

        if(Platform.getInstance().isMW()){
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    5
            );
        } else {
            String article_xpath = getFolderXpathByName(article_title);
            this.waitForArticleToAppearByTitle(article_title);
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article");
        }

        if(Platform.getInstance().isIOS()){
            String article_xpath = getFolderXpathByName(article_title);
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }
        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }
}
