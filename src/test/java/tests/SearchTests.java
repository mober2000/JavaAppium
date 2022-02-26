package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

public class SearchTests extends CoreTestCase {

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Тестирование поиска")
    @Description("Мы проверяем результаты поиска после ввода названия статьи '{search_line}' в поле")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Отмена поиска")
    @Description("Отменяем поиск и проверяем отсутствие кнопки отмены поиска")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Проверка что результаты поиска не пусты")
    @Description("Мы проверяем что в результатах поиска несколько статей")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfNotEmptySearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticle();

        assertTrue("We found too few results!",
                amount_of_search_results > 0);
    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Проверка пустого результата поиска")
    @Description("Мы првоеряем что при запросе '{search_line}' нет ни одного результата поиска")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "345345234525423544";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Проверка текста в поле")
    @Description("Првоеряем наличия текста у элемента")
    @Step("Starting test testCheckExpectedText")
    @Severity(value = SeverityLevel.MINOR)
    public void testCheckExpectedText(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.assertElementHasText("Search Wikipedia");
    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Поиск и отмена поиска")
    @Description("Проверяем что результат отобразился и закрываем поиск")
    @Step("Starting test testCancelSearchAndCheckList")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearchAndCheckList(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Harry Potter");

        assertNotNull("Список пустой", SearchPageObject.getCheckList());

        SearchPageObject.clickCancelSearch();

        assertNull("Список не пустой", SearchPageObject.getCheckList());

    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Проверяем что все результаты поиска содержат '{searchCriteria}'")
    @Description("Проверяем что все результаты поиска содержат '{searchCriteria}'")
    @Step("Starting test testCheckTextInSearchResults")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckTextInSearchResults(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchCriteria = "Harry Potter";
        SearchPageObject.typeSearchLine(searchCriteria);
        List<WebElement> searchResultTitles = SearchPageObject.searchResultTitles();
        assertNotNull("Список пустой", searchResultTitles);
        searchResultTitles.forEach(title -> assertTrue(title.getText().toLowerCase().contains(searchCriteria.toLowerCase())));
    }

    @Test
    @Features(value ={@Feature(value="Search")})
    @DisplayName("Проверка отображения заголовка и описания")
    @Description("Мы должны удостовериться что результаты поиска содержат необходимый заголовок и описание")
    @Step("Starting test testSearchResultByTitleAndDescription")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearchResultByTitleAndDescription(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.waitForElementByTitleAndDescription("Appium", "Automation for Apps");
    }
}
