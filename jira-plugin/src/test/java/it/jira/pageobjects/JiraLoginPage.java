package it.jira.pageobjects;

import com.atlassian.jira.pageobjects.pages.AbstractJiraPage;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;

public class JiraLoginPage extends AbstractJiraPage {

    @ElementBy(id = "openid-login")
    PageElement openIdLoginPanel;

    public TimedCondition isOpenIdButtonVisible() {
        return openIdLoginPanel.timed().isVisible();
    }

    public void startAuthenticationDanceFor(@Nonnull String name) {
        elementFinder.find(By.linkText(name)).click();
    }

    @Override
    public TimedCondition isAt() {
        return isOpenIdButtonVisible();
    }

    @Override
    public String getUrl() {
        return "/login.jsp";
    }
}
