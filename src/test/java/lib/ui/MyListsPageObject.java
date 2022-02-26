package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPK,
            REMOVE_FROM_SAVED_BUTTON,
            TITLE_WITH_WATCHSTAR;

    @Step("Получения названия папки")
    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    @Step ("Получение названия сохраненных статей'")
    private static String getSavedArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPK.replace("{TITLE}", article_title);
    }

    @Step ("Удаление статьи из сохраненных")
    private String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject (RemoteWebDriver driver){
        super(driver);
    }

    @Step ("Открытие папки по названию '{name_of_folder}'")
    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
    this.waitForElementAndClick(
            folder_name_xpath,
            "Cannot find folder by name" + name_of_folder,
            5);
    }

    @Step ("Ожидания появления статьи по названию: '{article_title}'")
    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15);
    }

    @Step ("Проверка отсутствия статьи по названию: '{article_title}'")
    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15);
    }

    @Step ("Удаление статьи '{article_title}' из папки/списка ")
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

    @Step ("Получение количества сохраненных статей в списке")
    public int getNumberOfTitles() {
        return this.getElements(TITLE_WITH_WATCHSTAR).size();
    }
}
