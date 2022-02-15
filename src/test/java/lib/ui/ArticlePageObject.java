package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_NY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find article title on page",15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()){
            return title_element.getText();
        } else {
            return title_element.getAttribute("name");
        }
    }

    public void swipeToFooter(){
        if(Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
    }

    public void addArticleToMyList(String name_of_folder){

        clickOptionsButtonAndAddToMyList();

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5);

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to see name of article folder",
                5);


        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input to see name of article folder",
                5);

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press Ok button",
                5);
    }

    public void closeArticle(){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find x link",
                5);
    }

    public void addArticleToExistingList() {
        clickOptionsButtonAndAddToMyList();

        this.waitForElementAndClick(
                "id:org.wikipedia:id/item_title",
                "Cannot find list for saving",
                5);

        this.waitForElementAndClick(
                "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
                "Cannot close article, cannot find x link",
                5);

    }

        public void goToTheList (String name_of_folder){
            this.waitForElementAndClick(
                "xpath://android.widget.FrameLayout[@content-desc='My lists']",
                "Cannot find navigation button to My List",
                5);

        this.waitForElementAndClick(
                "xpath://*[@text= '"+ name_of_folder + "']",
                "Cannot find created folder",
                5);
    }

    public void assertTitlePresent () {
        this.assertElementPresent(
                TITLE,
                "Cannot find title");

    }

    private void clickOptionsButtonAndAddToMyList() {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_NY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5);
    }

    public void addArticlesToMySaved() {
        this.waitForElementAndClick(OPTIONS_ADD_TO_NY_LIST_BUTTON, "Cannot find option to add article to reading list",5);
    }
}

