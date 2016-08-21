
package dangelov.wd;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WCHtmlUnitDriver extends HtmlUnitDriver {
    public WCHtmlUnitDriver() {
        this(false);
    }

    public WCHtmlUnitDriver(boolean enableJavascript) {
        super(enableJavascript);
    }

    public WCHtmlUnitDriver(BrowserVersion version, boolean enableJavascript) {
        super(version, enableJavascript);
    }

    public WCHtmlUnitDriver(BrowserVersion version) {
        super(version);
    }

    public WCHtmlUnitDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public WCHtmlUnitDriver(Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(desiredCapabilities, requiredCapabilities);
    }

    public WebClient retrieveWebClient() {
        return this.getWebClient();
    }
}
