package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

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

    public SearchPageObject(RemoteWebDriver driver){
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

    @Step("initializing the search field")
    public void initSearchInput(){
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear (){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear (){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelSearch (){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find type into search input", 5);
    }

    @Step("Waiting for Search Result")
    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Getting amount of articles")
    public int getAmountOfFoundArticle(){
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty result label")
    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results");
    }

    @Step ("Проверка наличия текста: '{text}'")
    public void assertElementHasText(String text) {
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                text,
                "Text is incorrect",
                5);
    }

    @Step ("Получение количества результатов")
    public List<WebElement> getCheckList() {
        return this.getElements(SEARCH_LIST_RESULT);
    }

    @Step ("Получение названия статей в списке")
    public List<WebElement> searchResultTitles() {
        return this.getElements(SEARCH_RESULT_TITLE);
    }

    @Step ("Проверка наличия заголовка: '{title}' и наличия описания: '{description}'")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchContainer(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with " + title + " and " + description);
    }
}
