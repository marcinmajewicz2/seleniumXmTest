import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class XmTests {

    private WebDriver driver;

    @BeforeMethod
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://xm.com/");
        //driver.navigate().to("https://www.xm.com/research/economicCalendar");

    }

    @Test
    public void xmMaxTest() {
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1024, 768));
        //driver.manage().window().setSize(new Dimension(800, 600));

        //Open Home page and check title
        waitForComplete();
        String pageTitle = driver.getTitle();
        Assert.assertTrue(driver.getTitle().equals("Forex & CFD Trading on Stocks, Indices, Oil, Gold by XM™"), "Wrong page title: " + driver.getTitle());
        //accept cookie
        WebElement acceptAllCookieButton = driver.findElement(By.cssSelector("button.js-acceptDefaultCookie.gtm-acceptDefaultCookieFirstVisit"));
        acceptAllCookieButton.click();
        // Click the <Research and Education>
        WebElement navResearchLink = driver.findElement(By.cssSelector("#main-nav > li.main_nav_research > a"));
        navResearchLink.click();
        WebElement navResearchSelected = driver.findElement(By.cssSelector("#main-nav > li.main_nav_research.selected"));
        Assert.assertTrue(navResearchSelected.isDisplayed(), "RESEARCH & EDUCATION: " + navResearchSelected.isDisplayed());
        // Click <Economic Calendar> driver.findElement(By.partialLinkText("Economic Calendar"));
        WebElement economicCalendarLink = driver.findElement(By.partialLinkText("Economic Calendar"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", economicCalendarLink);
        economicCalendarLink.click();
        //waitForComplete();

        // check text of #research-app h2
        WebElement economicCalendarH2 = driver.findElement(By.cssSelector("#research-app h2"));
        Assert.assertTrue(economicCalendarH2.getText().equals("Economic Calendar"), "Economic Calendar: " + economicCalendarH2.getText());
        //<Today> on Slider and check that the date is correct. div.mat-slider-thumb-container > div.mat-slider-focus-ring
        //switch to iFrameResizer0
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#iFrameResizer0")));

        WebElement timeFrame = driver.findElement(By.cssSelector("span.tc-timeframe > span > div"));
       // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
        WebElement dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
        WebElement dateTitle = driver.findElement(By.cssSelector("span.tc-economic-calendar-item-header-left-title"));
        WebElement slider = driver.findElement(By.cssSelector("mat-slider[role='slider']"));

        int width = slider.getSize().getWidth();
        //System.out.println("with: "+width);
        Actions move = new Actions(driver);
        //Action action = (Action) actions.dragAndDropBy(dateSlider, 30, 0).build();
        //move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build().perform();
        Action action = (Action) move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build();
        action.perform();
       // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
        // verifying if the WebElement is stale
            dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");

            //DATE

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMMM dd", new Locale("en", "PL"));
        Calendar cal = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(cal.getTime());
        //check that the date is correct
        Assert.assertTrue(dateTitle.getText().equals(todayDate), "Today date: " + dateTitle.getText() + " compare " + todayDate);
        //move to Tomorrow
        action.perform();
       // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
            dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");
        cal.add(Calendar.DATE, 1);
        String tomorrowDate = simpleDateFormat.format(cal.getTime());
        //check that the date is correct
        Assert.assertTrue(dateTitle.getText().equals(tomorrowDate), "Tomorrow date: " + dateTitle.getText() + " compare " + tomorrowDate);
        //move to this week
        action.perform();
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateTitle);
        //move to next week
        action.perform();
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateTitle);
        dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");
        //set calendar to Monday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //move to next Monday (add 7days)
        cal.add(Calendar.DATE, 7);
        String nextWeekDate = simpleDateFormat.format(cal.getTime());
        Assert.assertTrue(dateTitle.getText().equals(nextWeekDate), "nextWeekDate: " + dateTitle.getText() + " compare " + tomorrowDate);

        //Click <Educational Videos> link
        driver.switchTo().defaultContent();
        navResearchLink = waitForStaleElement("#main-nav > li.main_nav_research > a");
        navResearchLink.click();
        WebElement educationalVideosLink = driver.findElement(By.partialLinkText("Educational Videos"));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", educationalVideosLink);
        educationalVideosLink.click();
        //waitForComplete();
        // Click the Lesson 1.1
        WebElement introToTheMarketsButton = driver.findElement(By.xpath("//button[text()='Intro to the Markets']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", introToTheMarketsButton);
        WebElement lessons1_1Link = driver.findElement(By.partialLinkText("Lesson 1.1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lessons1_1Link);
        // switchToIframe and click play
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("div.videowrapper > iframe")));
        WebElement playerBigPlayButton = driver.findElement(By.cssSelector("div.player-big-play-button"));
        playerBigPlayButton.click();
        //video should play for a minimum of 5 seconds
        checkPlayerProgressTime(5);

    }

    @Test
    public void xm1014Test() {
        //driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(1024, 768));
        //driver.manage().window().setSize(new Dimension(800, 600));

        //Open Home page and check title
       /* waitForComplete();
        String pageTitle = driver.getTitle();
        Assert.assertTrue(driver.getTitle().equals("Forex & CFD Trading on Stocks, Indices, Oil, Gold by XM™"), "Wrong page title: " + driver.getTitle());
        //accept cookie
        WebElement acceptAllCookieButton = driver.findElement(By.cssSelector("button.js-acceptDefaultCookie.gtm-acceptDefaultCookieFirstVisit"));
        acceptAllCookieButton.click();
        // Click the <Research and Education>
        WebElement navResearchLink = driver.findElement(By.cssSelector("#main-nav > li.main_nav_research > a"));
        navResearchLink.click();
        WebElement navResearchSelected = driver.findElement(By.cssSelector("#main-nav > li.main_nav_research.selected"));
        Assert.assertTrue(navResearchSelected.isDisplayed(), "RESEARCH & EDUCATION: " + navResearchSelected.isDisplayed());
        // Click <Economic Calendar> driver.findElement(By.partialLinkText("Economic Calendar"));
        WebElement economicCalendarLink = driver.findElement(By.partialLinkText("Economic Calendar"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", economicCalendarLink);
        economicCalendarLink.click();
        //waitForComplete();
*/
        //SLIDER
        waitForComplete();
        WebElement acceptAllCookieButton = driver.findElement(By.cssSelector("button.js-acceptDefaultCookie.gtm-acceptDefaultCookieFirstVisit"));
        acceptAllCookieButton.click();



        // check text of #research-app h2
        WebElement economicCalendarH2 = driver.findElement(By.cssSelector("#research-app h2"));
        Assert.assertTrue(economicCalendarH2.getText().equals("Economic Calendar"), "Economic Calendar: " + economicCalendarH2.getText());
        //<Today> on Slider and check that the date is correct. div.mat-slider-thumb-container > div.mat-slider-focus-ring
        //switch to iFrameResizer0
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#iFrameResizer0")));

        WebElement timeFrame = driver.findElement(By.cssSelector("span.tc-timeframe > span > div"));
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
        WebElement dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
        WebElement dateTitle = driver.findElement(By.cssSelector("span.tc-economic-calendar-item-header-left-title"));
        WebElement slider = driver.findElement(By.cssSelector("mat-slider[role='slider']"));

        int width = slider.getSize().getWidth();
        //System.out.println("with: "+width);
        Actions move = new Actions(driver);
        //Action action = (Action) actions.dragAndDropBy(dateSlider, 30, 0).build();
        //move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build().perform();
        //Action action = (Action) move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build();
        //Action action = (Action) move.clickAndHold(dateSlider).moveByOffset(30, 0).release().build();

        scrollAndMoveSlider("Today");
        sleep();
        //Action action = (Action) move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build();
        //action.perform();
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
        // verifying if the WebElement is stale
        dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");

        //DATE

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMMM dd", new Locale("en", "PL"));
        Calendar cal = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(cal.getTime());
        //check that the date is correct
        Assert.assertTrue(dateTitle.getText().equals(todayDate), "Today date: " + dateTitle.getText() + " compare " + todayDate);
        //move to Tomorrow
        //action.perform();

        scrollAndMoveSlider("Tomorrow");
        sleep();

        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeFrame);
        dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");
        cal.add(Calendar.DATE, 1);
        String tomorrowDate = simpleDateFormat.format(cal.getTime());
        //check that the date is correct
        Assert.assertTrue(dateTitle.getText().equals(tomorrowDate), "Tomorrow date: " + dateTitle.getText() + " compare " + tomorrowDate);
        //move to this week
        //action.perform();
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateTitle);
        //move to next week
        //action.perform();
        scrollAndMoveSlider("Next Week");
        sleep();
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateTitle);
        dateTitle = waitForStaleElement("span.tc-economic-calendar-item-header-left-title");
        //set calendar to Monday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //move to next Monday (add 7days)
        cal.add(Calendar.DATE, 7);
        String nextWeekDate = simpleDateFormat.format(cal.getTime());
        Assert.assertTrue(dateTitle.getText().equals(nextWeekDate), "nextWeekDate: " + dateTitle.getText() + " compare " + tomorrowDate);

        //Click <Educational Videos> link
        driver.switchTo().defaultContent();
        //TO REMOVE
        WebElement navResearchLink = driver.findElement(By.cssSelector("#main-nav > li.main_nav_research > a"));

        navResearchLink = waitForStaleElement("#main-nav > li.main_nav_research > a");
        navResearchLink.click();
        WebElement educationalVideosLink = driver.findElement(By.partialLinkText("Educational Videos"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", educationalVideosLink);
        educationalVideosLink.click();
        //waitForComplete();
        // Click the Lesson 1.1
        /*WebElement lessons1_1Link = driver.findElement(By.partialLinkText("Lesson 1.1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lessons1_1Link);*/

        WebElement introToTheMarketsButton = driver.findElement(By.xpath("//button[text()='Intro to the Markets']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", introToTheMarketsButton);
        WebElement lessons1_1Link = driver.findElement(By.partialLinkText("Lesson 1.1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lessons1_1Link);
        // switchToIframe and click play
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("div.videowrapper > iframe")));
        WebElement playerBigPlayButton = driver.findElement(By.cssSelector("div.player-big-play-button"));
        playerBigPlayButton.click();
        //video should play for a minimum of 5 seconds
        checkPlayerProgressTime(5);

    }

private void scrollAndMoveSlider(String whatDay){
    WebElement timeFrame = driver.findElement(By.cssSelector("span.tc-timeframe > span > div"));
    WebElement sliderContainer = driver.findElement(By.cssSelector("div.mat-slider-thumb-container"));
    WebElement dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
    WebElement dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
    WebElement slider = driver.findElement(By.cssSelector("mat-slider[role='slider']"));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    Actions move = new Actions(driver);
    //jse.executeScript("arguments[0].scrollIntoView(true);", timeFrame);
    //move.scrollByAmount(0,300);

    switch (whatDay) {
        case "Today":
            int width = slider.getSize().getWidth();
            //System.out.println("with: "+width);
            sleep300();
            sleep300();
            dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
            //Action action = (Action) actions.dragAndDropBy(dateSlider, 30, 0).build();
            //move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build().perform();
            //Action action = (Action) move.moveToElement(dateSlider, (width * 17) / 100, 0).click().build();
            //Action action = (Action) move.clickAndHold(dateSlider).moveByOffset(30, 0).release().build();
            sleep300();
            //move.scrollByAmount(0,300).moveToElement(dateSlider, (width * 17) / 100, 0).click().build().perform();
            //sleep();
            //move.scrollByAmount(0,600);
            move.sendKeys(Keys.PAGE_DOWN).build().perform();
            //sleep();
            //sleep300();
            sleep();
            //move.clickAndHold(dateSlider).build();
            move.clickAndHold(dateSlider).moveByOffset(30, 0).release().build().perform();

            sleep();
            move.sendKeys(Keys.PAGE_DOWN).moveToElement(dateSlider, (width * 17) / 100, 0).click().build().perform();
            sleep300();
            sleep300();
            sleep300();//action.perform();
            break;
        case "Tomorrow":
            System.out.println(dateSlider.getLocation());
            jse.executeScript("arguments[0].style = 'transform: translateX(-66.6667%);' ", sliderContainer);
            sleep300();
            dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
            dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
            System.out.println(dateSlider.getLocation());
            //((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSlider);
            jse.executeScript("window.scrollTo("+dateSlider.getLocation().x+", "+dateSlider.getLocation().y+")");

            break;
        case "Next Week":
            System.out.println(dateSlider.getLocation());
            jse.executeScript("arguments[0].style = 'transform: translateX(-33.3333%);' ", sliderContainer);
            sleep300();
            dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
            dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
            System.out.println(dateSlider.getLocation());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSlider);
            break;
    }
 }

    private void actionsSlider(String whatDay){
        WebElement timeFrame = driver.findElement(By.cssSelector("span.tc-timeframe > span > div"));
        WebElement sliderContainer = driver.findElement(By.cssSelector("div.mat-slider-thumb-container"));
        WebElement dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
        WebElement dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
        WebElement slider = driver.findElement(By.cssSelector("mat-slider[role='slider']"));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", timeFrame);

        switch (whatDay) {
            case "Today":
                System.out.println(dateSlider.getLocation());
                jse.executeScript("arguments[0].style = 'transform: translateX(-86.6667%);' ", sliderContainer);
                sleep300();
                dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
                System.out.println(dateSlider.getLocation());
                dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSlider);
                break;
            case "Tomorrow":
                System.out.println(dateSlider.getLocation());
                jse.executeScript("arguments[0].style = 'transform: translateX(-66.6667%);' ", sliderContainer);
                sleep300();
                dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
                dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
                System.out.println(dateSlider.getLocation());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSlider);
                break;
            case "Next Week":
                System.out.println(dateSlider.getLocation());
                jse.executeScript("arguments[0].style = 'transform: translateX(-33.3333%);' ", sliderContainer);
                sleep300();
                dateSlider = driver.findElement(By.cssSelector("div.mat-slider-focus-ring"));
                dateSliderThumb = driver.findElement(By.cssSelector("div.mat-slider-thumb"));
                System.out.println(dateSlider.getLocation());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSlider);
                break;
        }
    }

    private void checkPlayerProgressTime(Integer timeInSeconds){
        int i = 0;
        Actions mouseMoveAction = new Actions(driver);
        WebElement playerVideoHolder = driver.findElement(By.cssSelector("div.player-video-holder"));
        mouseMoveAction.moveToElement(playerVideoHolder).perform();
        WebElement playerProgressTime = driver.findElement(By.cssSelector("div.player-progress-time"));
        while (Integer.valueOf(playerProgressTime.getText().replaceAll(":",""))<timeInSeconds){
            sleep300();
            mouseMoveAction.moveToElement(playerVideoHolder).perform();
            if(i>20) break;
            i++;
        }
        System.out.println("played time: " + playerProgressTime.getText());
    }




    private WebElement waitForStaleElement (String locatorCss){
     new WebDriverWait(driver, Duration.ofSeconds(5))
            .ignoring(StaleElementReferenceException.class)
                .until((WebDriver d) -> {
        WebElement htmlElement = d.findElement(By.cssSelector(locatorCss));
                    return htmlElement;
    });
        return driver.findElement(By.cssSelector(locatorCss));
    }

    private void waitForComplete(){        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep300() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void afterTest() {
        driver.close();
        driver.quit();
    }
}