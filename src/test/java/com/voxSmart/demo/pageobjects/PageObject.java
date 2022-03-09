package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.fail;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class PageObject {

    public static final int ONE_SECOND = 1;
    public static final int FIVE_SECONDS = 5;
    public static final int TEN_SECONDS = 10;
    public static final int THIRTY_SECONDS = 30;
    public static final int SIXTY_SECONDS = 60;
    public static final Duration SIXTY_SECONDS_DURATION = Duration.ofSeconds(60);
    public static final Duration ONE_SECOND_DURATION = Duration.ofSeconds(1);
    public static final Duration FIFTEEN_SECOND_DURATION = Duration.ofSeconds(15);
    public static final Duration THIRTY_SECOND_DURATION = Duration.ofSeconds(30);
    public static final String ELEMENT_IS_CLICKABLE = "elementIsClickable";
    public static final String ELEMENT_IS_VISIBLE = "elementIsVisible";
    public static final String CLASS_IS_ACTIVE = "is-active";
    public static final String ACCORDIAN_PANEL_OPEN_CLASS = "mdc-accordion__panel--active";
    public static final String ACCORDIAN_PANEL_OPEN_COMPLETED = "mdc-accordion__panel--completed";
    public static final String ACCORDIAN_PANEL_CLOSE_CLASS = "mdc-accordion__panel";

    public static final String OVERLAY_XPATH = "//body/div[@class='overlay']";
    private static WebDriverWait jsWait;

    private CucumberWebDriver webDriver;

    public PageObject(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static void sleep(Integer seconds) { //Should only be used for debugging
        long secondsLong = (long) seconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public <T> void waitForPageToLoad(Class<T> clz, final WebDriver webDriver) {
        for (final Field field : clz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FindBy.class)) {
                if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).id())) {
                    waitForElement(
                            webDriver,
                            By.id(field.getAnnotation(FindBy.class).id()),
                            ELEMENT_IS_VISIBLE);
                } else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).name())) {
                    waitForElement(
                            webDriver,
                            By.name(field.getAnnotation(FindBy.class).name()),
                            ELEMENT_IS_VISIBLE);
                } else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).className())) {
                    waitForElement(
                            webDriver,
                            By.className(field.getAnnotation(FindBy.class).className()),
                            ELEMENT_IS_VISIBLE);
                } else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).xpath())) {
                    waitForElement(
                            webDriver,
                            By.xpath(field.getAnnotation(FindBy.class).xpath()),
                            ELEMENT_IS_VISIBLE);
                }
            }
        }
    }

    public <T> WebElement waitForElement(
            final WebDriver webDriver, final By locator, String condition) {

        new FluentWait<WebDriver>(webDriver)
                .withTimeout(SIXTY_SECONDS_DURATION)
                .pollingEvery(ONE_SECOND_DURATION)
                .ignoring(NoSuchElementException.class)
                .until(expectedCondition(condition, locator));

        return webDriver.findElement(locator);
    }

    public PageObject pause(int timeInSeconds) {
        try {
            SECONDS.sleep(timeInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void waitTillDisappear(By by) {
        new FluentWait<>(webDriver.findElement(by))
                .withTimeout(Duration.ofSeconds(TEN_SECONDS))
                .pollingEvery(Duration.ofMillis(100))
                .until((Function<WebElement, Boolean>) element -> !element.isDisplayed());
    }

    public void waitTillToBeDisplayed(By by, int seconds) {
        new FluentWait<>(webDriver.findElement(by))
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(100))
                .until((Function<WebElement, Boolean>) element -> element.isDisplayed());
    }

    public String getPanelOpenClass() {
        return ACCORDIAN_PANEL_OPEN_CLASS;
    }

    public String getPanelCloseClass() {
        return ACCORDIAN_PANEL_CLOSE_CLASS;
    }

    public String getPanelCompletedClass() {
        return ACCORDIAN_PANEL_OPEN_COMPLETED;
    }

    public ExpectedCondition<WebElement> expectedCondition(String condition, By locator) {

        switch (condition) {
            case ELEMENT_IS_VISIBLE:
                return ExpectedConditions.visibilityOfElementLocated(locator);
            case ELEMENT_IS_CLICKABLE:
                return ExpectedConditions.elementToBeClickable(locator);
            default:
                throw new IllegalArgumentException("invalid condition");
        }
    }

    public boolean isElementPresent(WebDriver webDriver, By by) {
        webDriver.manage().timeouts().implicitlyWait(0, SECONDS);
        try {
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            webDriver.manage().timeouts().implicitlyWait(30, SECONDS);
        }
    }

    public void waitForClassChange(WebElement webElement, String classValue, Integer waitSecond) {
        new WebDriverWait(webDriver, waitSecond)
                .until(ExpectedConditions.attributeToBe(webElement, "class", classValue));
    }

    public void waitForClassChange(WebElement webElement, String classValue) {
        waitForClassChange(webElement, classValue, FIVE_SECONDS);
    }

    public void waitForAttributeChange(
            WebElement webElement, String attribute, String attributeValue, Integer waitSecond) {
        new WebDriverWait(webDriver, waitSecond)
                .until(ExpectedConditions.attributeToBe(webElement, attribute, attributeValue));
    }

    public void waitForAttributeChangeWithPolling(
            WebElement webElement, String attribute, String attributeValue, Integer waitSecond) {
        new FluentWait<>(webElement)
                .withTimeout(Duration.ofSeconds(waitSecond))
                .pollingEvery(Duration.ofMillis(500))
                .until(
                        (Function<WebElement, Boolean>)
                                element ->
                                        element.getAttribute(attribute).contains(attributeValue));
    }

    public void waitForAttributeChangeWithPolling(
            WebElement webElement, String attribute, String attributeValue) {
        new FluentWait<>(webElement)
                .withTimeout(Duration.ofSeconds(FIVE_SECONDS))
                .pollingEvery(Duration.ofMillis(500))
                .until(
                        (Function<WebElement, Boolean>)
                                element ->
                                        element.getAttribute(attribute).contains(attributeValue));
    }

    public void waitForAttributeNotToContainWithPolling(
            WebElement webElement, String attribute, String attributeValue) {
        new FluentWait<>(webElement)
                .withTimeout(Duration.ofSeconds(FIVE_SECONDS))
                .pollingEvery(Duration.ofMillis(500))
                .until(
                        (Function<WebElement, Boolean>)
                                element ->
                                        !element.getAttribute(attribute).contains(attributeValue));
    }

    public void waitForSelectButtonClassToLoad(WebElement webElement) {
        this.getWebDriver().manage().timeouts().implicitlyWait(TEN_SECONDS, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(this.getWebDriver(), TEN_SECONDS);
        wait.until(
                element ->
                        webElement
                                .getAttribute("class")
                                .equals("c-btn c-btn--select c-select__btn is-"));
    }

    public String getParentClass(WebElement webElement) {
        WebElement parentElement = webElement.findElement(By.xpath(".."));
        return parentElement.getAttribute("class");
    }

    public WebElement getParent(WebElement webElement) {
        WebElement parentElement = webElement.findElement(By.xpath(".."));
        return parentElement;
    }

    public WebElement getChild(WebElement webElement, Integer nthChild) {
        WebElement childElement = null;
        try {
            childElement = webElement.findElements(By.xpath("./child::*")).get(nthChild);
        } catch (Exception e) {
            fail(
                    "Unable to find child element for element with xpath "
                            + getElementXPath(webElement));
        }
        return childElement;
    }

    public String getFirstChildClass(WebElement webElement) {
        return getChild(webElement, 0).getAttribute("class");
    }

    public boolean isParentClassActive(WebElement webElement) {
        return getParentClass(webElement).equalsIgnoreCase(CLASS_IS_ACTIVE);
    }

    public boolean isClassActive(WebElement webElement) {
        return webElement.getAttribute("class").contains(CLASS_IS_ACTIVE);
    }

    public void waitForClassToBeActive(WebElement element,Integer timeOut,Integer poll) {
        await().atMost(Duration.ofSeconds(timeOut))
                .pollInterval(Duration.ofSeconds(poll))
                .until(() -> isClassActive(element));
    }

    public boolean elementHasClass(WebElement element, String active) {
        try {
            return element.getAttribute("class").contains(active);
        } catch (StaleElementReferenceException e) {
            return StaleElementHandleByID(element);
        }
    }

    public Boolean StaleElementHandleByID(WebElement element) {
        int count = 0;
        boolean isPresent = false;
        while (count < 10 && !isPresent) {
            try {
                isPresent = element.isDisplayed();
            } catch (StaleElementReferenceException e) {
                e.toString();
                System.out.println(
                        "Trying to recover from a stale element : "
                                + count
                                + " --- "
                                + isPresent
                                + "---"
                                + e.getMessage());
                count = count + 1;
            }
        }
        return isPresent;
    }

    public WebElement staleElementWorkAround(WebElement element) throws InterruptedException {
        int count = 0;
        boolean isPresent = false;
        while (count < 10 && !isPresent) {
            try {
                isPresent = element.isDisplayed();
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                webDriver.findElement(By.xpath(getElementXPath(element)));
                TimeUnit.MILLISECONDS.sleep(100);
                e.toString();
                System.out.println(
                        "Trying to recover from a stale element : "
                                + count
                                + " --- "
                                + isPresent
                                + "---"
                                + e.getMessage());
                count = count + 1;
            }
        }
        return element;
    }

    public void staleElementClick(WebElement element) {
        int count = 0;
        boolean isPresent = false;
        while (count < 10 && !isPresent) {
            try {
                isPresent = element.findElement(By.xpath(getElementXPath(element))).isDisplayed();
                jsClick(element);
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                e.toString();
                System.out.println(
                        "Trying to recover from a stale element : "
                                + count
                                + " --- "
                                + isPresent
                                + "---"
                                + e.getMessage());
                count = count + 1;
            }
        }
    }

    public void waitForElementToAppear(WebElement element, Integer waitSecond) {
        resetImplicitWait();
        new WebDriverWait(webDriver, waitSecond).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element, Integer waitSecond) {
        resetImplicitWait();
        new WebDriverWait(webDriver, waitSecond)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForEitherElementToAppear(
            WebElement element1, WebElement element2, Integer waitSecond) {
        resetImplicitWait();
        WebDriverWait wait = new WebDriverWait(webDriver, waitSecond);
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(element1),
                        ExpectedConditions.visibilityOf(element2)));
    }

    public void waitForAnyElementToAppear(
            WebElement element1, WebElement element2, WebElement element3, Integer waitSecond) {
        resetImplicitWait();
        WebDriverWait wait = new WebDriverWait(webDriver, waitSecond);
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(element1),
                        ExpectedConditions.visibilityOf(element2),
                        ExpectedConditions.visibilityOf(element3)));
    }

    public void waitForButtonToBeClickableAndClick(WebElement element) {
        new WebDriverWait(webDriver, THIRTY_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(element));
        jsClick(element);
    }

    public void waitForButtonToBeClickable(WebElement element) {
        new WebDriverWait(webDriver, THIRTY_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void printCurrentFrame() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        System.out.println("Current Frame is: " + currentFrame);
    }

    public String getCurrentFrame() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String currentFrame =
                (String) jsExecutor.executeScript("return self.name + document.title");
        return currentFrame;
    }

    public void printAttributes(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String attributes =
                (String) jsExecutor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
        log.info(attributes);
    }

    public String getCurrentURL() {
        return webDriver.getCurrentUrl();
    }

    public void jsSendKeys(WebElement element, String text) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].value='" + text + "';", element);
    }

    public void switchToDefaultFrame() {
        webDriver.switchTo().defaultContent();
    }

    public void switchToFirstIFrame() {
        webDriver.switchTo().frame(0);
    }

    public void switchToFrame(String frameId) {
        webDriver.switchTo().frame(frameId);
    }

    public void switchToFrame(WebElement elementOnFrame) {
        webDriver.switchTo().frame(elementOnFrame);
        log.debug("Switched to frame " + getCurrentFrame());
    }

    public void waitForElement(final WebElement element) {
        Wait wait =
                new FluentWait(webDriver)
                        .withTimeout(THIRTY_SECOND_DURATION)
                        .pollingEvery(ONE_SECOND_DURATION)
                        .ignoring(
                                NoSuchElementException.class, StaleElementReferenceException.class);
        wait.until(
                new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return element.findElement(By.xpath(".")) != null;
                    }
                });
    }

    //    public List<WebElement> getAllButtons() {
    //        return webDriver.findElements(By.tagName("button"));
    //    }
    //
    //    public WebElement getFirstButtonsWithText(String text) {
    //        WebElement webElement = null;
    //        for (WebElement element : this.getAllButtons()) {
    //            if (element.getText().equalsIgnoreCase(text)) {
    //                webElement = element;
    //            }
    //        }
    //        return webElement;
    //    }

    public WebElement waitAndReturnElement(WebElement element) {
        try {
            waitForElement(element);
            return element;
        } catch (Exception e) {
            fail("Element not found");
            return null;
        }
    }

    public void waitForElementWithText(final WebElement element, String text) {
        Wait wait =
                new FluentWait(webDriver)
                        .withTimeout(FIFTEEN_SECOND_DURATION)
                        .pollingEvery(ONE_SECOND_DURATION)
                        .ignoring(NoSuchElementException.class);
        wait.until(
                new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return element.getAttribute("innerText").equalsIgnoreCase(text);
                    }
                });
    }

    public void
            waitIfNotFailed() { // Application specific need to move somewhere else as it should not
        // be in page object
        if (!isOneErrorPage()) {
            Wait wait =
                    new FluentWait(webDriver)
                            .withTimeout(FIFTEEN_SECOND_DURATION)
                            .pollingEvery(ONE_SECOND_DURATION)
                            .ignoring(NoSuchElementException.class);
            wait.until(
                    new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return !(driver.getCurrentUrl().contains("ineligible")
                                    || driver.getCurrentUrl().contains("error"));
                        }
                    });
        } else {
            fail("On error page so no point waiting");
        }
    }

    public void waitForElement(final WebElement element, Integer timeoutSeconds) {
        Wait wait =
                new FluentWait(webDriver)
                        .withTimeout(Duration.ofSeconds(Long.valueOf(timeoutSeconds)))
                        .pollingEvery(ONE_SECOND_DURATION)
                        .ignoring(NoSuchElementException.class);
        wait.until(
                new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return element.findElement(By.xpath(".")) != null;
                    }
                });
    }

    public void waitForInvisibility(WebElement webElement, int maxSeconds) {
        Long startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < maxSeconds * 1000
                    && webElement.isDisplayed()) {}
            System.out.println("Waiting...");
        } catch (StaleElementReferenceException e) {
            System.out.println("In stale");
            return;
        } catch (NoSuchElementException e) {
            System.out.println("In No such element");
            return;
        }
    }

    public void waitUntilElementDisplayed(final WebElement webElement) {
        WebDriver driver = webDriver;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, SIXTY_SECONDS);
        ExpectedCondition elementIsDisplayed =
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver arg0) {
                        try {
                            webElement.isDisplayed();
                            return true;
                        } catch (NoSuchElementException e) {
                            return false;
                        } catch (StaleElementReferenceException f) {
                            return false;
                        }
                    }
                };
        wait.until(elementIsDisplayed);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    public void waitUntilElementEnabled(final WebElement webElement) {
        WebDriver driver = webDriver;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, SIXTY_SECONDS);
        ExpectedCondition elementIsDisplayed =
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver arg0) {
                        try {
                            webElement.isEnabled();
                            System.out.println("Element is enabled, returning");
                            return true;
                        } catch (NoSuchElementException e) {
                            return false;
                        } catch (StaleElementReferenceException f) {
                            return false;
                        }
                    }
                };
        wait.until(elementIsDisplayed);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public List<WebElement> getAlliFrames() {
        return webDriver.findElements(By.xpath("//iframe"));
    }

    public void waitForAjaxToFinish() {
        int timeout = 0;

        while (timeout < 80) {
            boolean ajaxFinished =
                    (boolean)
                            ((JavascriptExecutor) webDriver)
                                    .executeScript("return !!jQuery && jQuery.active == 0");
            if (ajaxFinished) return;
            timeout++;
            try {
                sleep(500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        throw new AssertionError("Ajax haven't finished its job in 40 sec");
    }

    public void waitForAjaxToFinishNew() {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        if ((Boolean) executor.executeScript("return window.jQuery != undefined")) {
            while (!(Boolean) executor.executeScript("return jQuery.active == 0")
                    && !(Boolean)
                            executor.executeAsyncScript("return document.readyState")
                                    .equals("complete")) {
                sleep(1000);
            }
        }
        return;
    }

    public void scrollDown() {
        printCurrentFrame();
        //        JavascriptExecutor jse = (JavascriptExecutor) RuntimeState.getWebDriver();
        //        jse.executeScript("scroll(0,250);");
        //        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    public void resetImplicitWait() {
        webDriver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
    }

    public WebElement getElementWithText(String elementText) {
        return webDriver.findElement(By.xpath("//*[contains(text(),\"" + elementText + "\")]"));
    }

    public WebElement getElementWithXpath(String xpath) {
        return webDriver.findElement(By.xpath(xpath));
    }

    public WebElement getAnchorWithText(String anchorText) {
        //        return webDriver.findElement(By.xpath("//a[text(),\"" + anchorText + "\"]"));
        return webDriver.findElement(By.xpath("//a[contains(text(),\"" + anchorText + "\")]"));
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public void hardCodeWait() {
        sleep(2000);
    }

    public void moveToElementAndClick(WebElement element) {
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(element);
        actions.click();
        try {
            actions.perform();
        } catch (org.openqa.selenium.WebDriverException e) {
            element.click(); // Browserstack doesnt like perform
        }
    }

    public void moveToElement(WebElement element) {
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(element);
        try {
            actions.perform();
        } catch (org.openqa.selenium.WebDriverException e) {
            //            element.click(); // Browserstack doesnt like perform
        }
    }

    public void jsClick(WebElement element) {
        jsScrollToElement(element);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "var evt = document.createEvent('MouseEvents');"
                        + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                        + "arguments[0].dispatchEvent(evt);",
                element);
    }

    public void jsScrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsClear(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].value=''", element);
    }

    public void jsScrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,800)");
    }

    public void clearAndSendKeys(WebElement element,String text) {
        Actions actions = new Actions(webDriver);
        Action actionBuilder =
                actions.moveToElement(element)
                        .keyDown(element, Keys.CONTROL)
                        .sendKeys(element, "a")
                        .keyUp(element, Keys.CONTROL)
                        .sendKeys(element, text)
                        .sendKeys(element, Keys.TAB)
                        .build();
        actionBuilder.perform();
    }

    public void jsClearAndSendKeys(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].value='" + text + "'", element);
    }

    public void setAttribute(WebElement element, String attName, String attValue) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].setAttribute(arguments[1], arguments[2]);",
                element,
                attName,
                attValue);
    }

    public void clearWithBackspace(WebElement input) {
        while (input.getAttribute("value").length() > 0) {
            input.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void ctrlAdelete(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public String getElementXPath(WebElement element) {
        return (String)
                ((JavascriptExecutor) webDriver)
                        .executeScript(
                                "getXPath=function(node)"
                                        + "{"
                                        + "if (node.id !== '')"
                                        + "{"
                                        + "return '//' + node.tagName.toLowerCase() + '[@id=\"' + node.id + '\"]'"
                                        + "}"
                                        + "if (node === document.body)"
                                        + "{"
                                        + "return node.tagName.toLowerCase()"
                                        + "}"
                                        + "var nodeCount = 0;"
                                        + "var childNodes = node.parentNode.childNodes;"
                                        + "for (var i=0; i<childNodes.length; i++)"
                                        + "{"
                                        + "var currentNode = childNodes[i];"
                                        + "if (currentNode === node)"
                                        + "{"
                                        + "return getXPath(node.parentNode) + '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) + ']'"
                                        + "}"
                                        + "if (currentNode.nodeType === 1 && "
                                        + "currentNode.tagName.toLowerCase() === node.tagName.toLowerCase())"
                                        + "{"
                                        + "nodeCount++"
                                        + "}"
                                        + "}"
                                        + "};"
                                        + "return getXPath(arguments[0]);",
                                element);
    }

    private boolean isOneErrorPage() {
        // Only applicable for SkyIE app
        if (webDriver.getCurrentUrl().endsWith("error")) {
            return true;
        }
        return false;
    }

    public Cookie getBrowserCookieNamed(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }
}
