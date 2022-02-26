package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Waiting wor title on the article page")
    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find article title on page",15);
    }

    @Step ("Get article title")
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()){
            return title_element.getText();
        }else if(Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        }else {
            return title_element.getText();
        }
    }

    @Step ("Swiping to footer on the article page")
    public void swipeToFooter(){
        if(Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        } else {
        this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
    }

    @Step ("Adding the article to my list")
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

    @Step ("Closing the article")
    public void closeArticle(){
        if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find x link",
                5);
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step ("Добавление статьи в существующий лист")
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

    @Step ("Переход в 'My lists'")
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

    @Step ("Проверка появления заголовка")
    public void assertTitlePresent () {
        this.assertElementPresent(
                TITLE,
                "Cannot find title");

    }

    @Step ("Клик по меню и добавление статьи")
    private void clickOptionsButtonAndAddToMyList() {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5);
    }

    @Step ("Adding the article to my saved articles")
    public void addArticlesToMySaved() {
        if(Platform.getInstance().isMW()){
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list",5);
    }

    @Step ("Removing article from saved if it has been added")
    public void removeArticleFromSavedIfItAdded(){
        if(this.isElementPresent( OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)){
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add article in my list",
                    5
            );
        }
    }

}

