package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyListsTests extends CoreTestCase {

    private static String
            name_of_folder = "Learning programming",
            login = "X0makQA",
            password = "X0makQA!";

    @Test
    @Features(value ={@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Сохранение первой статьи в список/в папку")
    @Description("Мы сохраняем статью, проверяем что она сохранени, а затем удаляем из списка/из папки")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());

            //ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    @Features(value ={@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Сохранение двух статей в список/папку")
    @Description("Мы сохраняем 2 статьи в папку/список, затем удаляем одну и проверяем что осталась другая статья")
    @Step("Starting test testSaveTwoArticleAndDeleteOne")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveTwoArticleAndDeleteOne() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Linkin Park discography");
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            SearchPageObject.clickByArticleWithSubstring("inkin Park discography");
        } else {
            SearchPageObject.clickByArticleWithSubstring("and discography");
        }
        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticleToExistingList();
            ArticlePageObject.goToTheList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        int number_of_titles = MyListsPageObject.getNumberOfTitles();
        MyListsPageObject.swipeByArticleToDelete("Java (programming language)");

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            MyListsPageObject.waitForArticleToAppearByTitle("Linkin Park discography");
        } else{
            assertEquals(MyListsPageObject.getNumberOfTitles() + 1, number_of_titles);
        }
    }
}
