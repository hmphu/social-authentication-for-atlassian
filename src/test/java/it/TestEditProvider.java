package it;

import com.atlassian.jira.pageobjects.BaseJiraWebTest;
import com.atlassian.jira.pageobjects.config.LoginAs;
import com.atlassian.pageobjects.elements.query.Poller;
import it.pageobjects.AddProviderPage;
import it.pageobjects.ConfigurationPage;
import it.pageobjects.EditProviderPage;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static com.atlassian.pageobjects.elements.query.Poller.waitUntilFalse;
import static com.atlassian.pageobjects.elements.query.Poller.waitUntilTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class TestEditProvider extends BaseJiraWebTest {

    @Before
    public void setUp() {
        jira.backdoor().restoreBlankInstance();
        jira.backdoor().project().addProject("Test", "TST", "admin");
    }

    @Test
    public void editOpenIdProvider()
    {
        final String name = "Testing";
        final String endpointUrl = "http://asdkasjdkald.pl";

        AddProviderPage addPage = jira.visit(AddProviderPage.class);

        addPage.setName(name);
        addPage.setEndpointUrl(endpointUrl);
        addPage.setExtensionNamespace("ext1");

        ConfigurationPage configurationPage = addPage.save();

        EditProviderPage editPage = configurationPage.editProvider("Testing");
        Poller.waitUntil(editPage.getName(), (Matcher<String>) equalTo(name));
        Poller.waitUntil(editPage.getEndpointUrl(), (Matcher<String>) equalTo(endpointUrl));

        editPage.setName("");
        editPage.setEndpointUrl("");
        editPage.setExtensionNamespace("");
        editPage.save();

        assertThat(editPage.getFormErrors(), hasEntry("name", "Name must not be empty."));
        assertThat(editPage.getFormErrors(), hasEntry("endpointUrl", "Provider URL must not be empty."));
        assertThat(editPage.getFormErrors(), hasEntry("extensionNamespace", "Alias must not be empty."));
    }
}