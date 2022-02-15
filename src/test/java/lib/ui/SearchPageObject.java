package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject{
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_LIST_RESULT,
            SEARCH_RESULT_TITLE,
            SEARCH_BY_TITLE_AND_DESCRIPTION_TPL;

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS*/
    public static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public static String getResultSearchContainer(String title, String description){
        return String.format(SEARCH_BY_TITLE_AND_DESCRIPTION_TPL, title, description);
    }
    /* TEMPLATES METHODS*/

    public void initSearchInput(){
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    public void waitForCancelButtonToAppear (){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear (){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch (){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find type into search input", 5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring" + substring);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring" + substring, 10);
    }

    public int getAmountOfFoundArticle(){
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results");
    }

    public void assertElementHasText(String text) {
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                text,
                "Text is incorrect",
                5);
    }

    public List<WebElement> getCheckList() {
        return this.getElements(SEARCH_LIST_RESULT);
    }

    public List<WebElement> searchResultTitles() {
        return this.getElements(SEARCH_RESULT_TITLE);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchContainer(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with " + title + " and " + description);
    }
}
