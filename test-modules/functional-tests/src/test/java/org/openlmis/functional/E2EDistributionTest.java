/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.functional;


import com.thoughtworks.selenium.SeleneseTestBase;
import org.openlmis.UiUtils.CaptureScreenshotOnFailureListener;
import org.openlmis.UiUtils.TestCaseHelper;
import org.openlmis.UiUtils.TestWebDriver;
import org.openlmis.pageobjects.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

@Listeners(CaptureScreenshotOnFailureListener.class)

public class E2EDistributionTest extends TestCaseHelper {

  public String userSIC, password;
  private RefrigeratorPage refrigeratorPage;
  private String wifiInterface;


  @BeforeMethod(groups = {"offline"})
  public void setUp() throws Exception {
    super.setup();
    wifiInterface = getWiFiInterface();
  }

  @Test(groups = {"offline"}, dataProvider = "Data-Provider-Function")
  public void testE2EManageDistribution(String userSIC, String password, String deliveryZoneCodeFirst, String deliveryZoneCodeSecond,
                                        String deliveryZoneNameFirst, String deliveryZoneNameSecond,
                                        String facilityCodeFirst, String facilityCodeSecond,
                                        String programFirst, String programSecond, String schedule) throws Exception {

    List<String> rightsList = new ArrayList<String>();
    rightsList.add("MANAGE_DISTRIBUTION");
    rightsList.add("ADMIN");
    setupTestDataToInitiateRnRAndDistribution(facilityCodeFirst, facilityCodeSecond, true, programFirst, userSIC, "200", rightsList, programSecond, "District1", "Ngorongoro", "Ngorongoro");
    setupDataForDeliveryZone(true, deliveryZoneCodeFirst, deliveryZoneCodeSecond,
      deliveryZoneNameFirst, deliveryZoneNameSecond,
      facilityCodeFirst, facilityCodeSecond,
      programFirst, programSecond, schedule);
    dbWrapper.insertRoleAssignmentForDistribution(userSIC, "store in-charge", deliveryZoneCodeFirst);
    dbWrapper.insertRoleAssignmentForDistribution(userSIC, "store in-charge", deliveryZoneCodeSecond);
    dbWrapper.insertProductGroup("PG1");
    dbWrapper.insertProductWithGroup("Product5", "ProductName5", "PG1", true);
    dbWrapper.insertProductWithGroup("Product6", "ProductName6", "PG1", true);
    dbWrapper.insertProgramProduct("Product5", programFirst, "10", "false");
    dbWrapper.insertProgramProduct("Product6", programFirst, "10", "true");
    dbWrapper.deleteDeliveryZoneMembers(facilityCodeSecond);
    dbWrapper.setUpDataForChildCoverage();
    dbWrapper.insertRegimenProductMapping();
    configureISA();

    LoginPage loginPage = new LoginPage(testWebDriver, baseUrlGlobal);
    testWebDriver.sleep(1000);
    HomePage homePage = loginPage.loginAs(userSIC, password);
    testWebDriver.sleep(1000);
    DistributionPage distributionPage = homePage.navigateToDistributionWhenOnline();
    distributionPage.selectValueFromDeliveryZone(deliveryZoneNameFirst);
    distributionPage.selectValueFromProgram(programFirst);
    distributionPage.clickInitiateDistribution();

    waitForAppCacheComplete();

    switchOffNetworkInterface(wifiInterface);

    testWebDriver.sleep(3000);
    homePage.navigateHomePage();
    homePage.navigateOfflineDistribution();
    assertFalse("Delivery Zone selectBox displayed.", distributionPage.verifyDeliveryZoneSelectBoxNotPresent());
    assertFalse("Period selectBox displayed.", distributionPage.verifyPeriodSelectBoxNotPresent());
    assertFalse("Program selectBox displayed.", distributionPage.verifyProgramSelectBoxNotPresent());


    distributionPage.clickRecordData(1);
    FacilityListPage facilityListPage = new FacilityListPage(testWebDriver);
    RefrigeratorPage refrigeratorPage = facilityListPage.selectFacility(facilityCodeFirst);
    facilityListPage.verifyFacilityIndicatorColor("Overall", "AMBER");

    refrigeratorPage.onRefrigeratorScreen();
    refrigeratorPage.clickAddNew();
    refrigeratorPage.enterValueInBrandModal("LG");
    refrigeratorPage.enterValueInModelModal("800 LITRES");
    refrigeratorPage.enterValueInManufacturingSerialNumberModal("GR-J287PGHV");
    refrigeratorPage.clickDoneOnModal();

    homePage.navigateHomePage();
    homePage.navigateOfflineDistribution();


    distributionPage.clickRecordData(1);
    facilityListPage.selectFacility(facilityCodeFirst);

    String[] refrigeratorDetails = "LG;800 LITRES;GR-J287PGHV".split(";");
    for (int i = 0; i < refrigeratorDetails.length; i++) {
      assertEquals(testWebDriver.getElementByXpath("//div[@class='list-row ng-scope']/ng-include/form/div[1]/div[" + (i + 2) + "]").getText(), refrigeratorDetails[i]);
    }

    facilityListPage.verifyFacilityIndicatorColor("Overall", "RED");

    refrigeratorPage.verifyRefrigeratorColor("overall", "RED");
    refrigeratorPage.clickShowForRefrigerator1();
    refrigeratorPage.verifyRefrigeratorColor("individual", "RED");

    refrigeratorPage.enterValueInRefrigeratorTemperature("3");
    refrigeratorPage.verifyRefrigeratorColor("overall", "AMBER");
    refrigeratorPage.verifyRefrigeratorColor("individual", "AMBER");

    refrigeratorPage.clickFunctioningCorrectlyYesRadio();
    refrigeratorPage.enterValueInLowAlarmEvents("1");
    refrigeratorPage.enterValueInHighAlarmEvents("0");
    refrigeratorPage.clickProblemSinceLastVisitDontKnowRadio();

    refrigeratorPage.verifyRefrigeratorColor("overall", "GREEN");
    refrigeratorPage.verifyRefrigeratorColor("individual", "GREEN");

    refrigeratorPage.enterValueInNotesTextArea("miscellaneous");
    refrigeratorPage.clickDone();

    EPIUsePage epiUsePage = refrigeratorPage.navigateToEpiUse();
    epiUsePage.verifyProductGroup("PG1-Name", 1);
    epiUsePage.verifyIndicator("RED");

    epiUsePage.enterValueInStockAtFirstOfMonth("10", 1);
    epiUsePage.verifyIndicator("AMBER");
    epiUsePage.enterValueInReceived("20", 1);
    epiUsePage.enterValueInDistributed("30", 1);
    epiUsePage.checkApplyNRToLoss0();
    epiUsePage.enterValueInStockAtEndOfMonth("50", 1);
    epiUsePage.enterValueInExpirationDate("10/2011", 1);
    epiUsePage.verifyIndicator("GREEN");

    GeneralObservationPage generalObservationPage = epiUsePage.navigateToGeneralObservations();
    generalObservationPage.enterData("some observations", "samuel", "Doe", "Verifier", "XYZ");

    ChildCoveragePage childCoveragePage=generalObservationPage.navigateToChildCoverage();
    SeleneseTestBase.assertEquals(childCoveragePage.getTextOfTargetGroupValue(9), "300");
    SeleneseTestBase.assertEquals(childCoveragePage.getTextOfTargetGroupValue(10), "300");
    SeleneseTestBase.assertEquals(childCoveragePage.getTextOfTargetGroupValue(11), "300");
    SeleneseTestBase.assertEquals(childCoveragePage.getTextOfTargetGroupValue(1), "");
    SeleneseTestBase.assertEquals(childCoveragePage.getTextOfTargetGroupValue(12), "");

    ResultSet childCoverageDetails = dbWrapper.getChildCoverageDetails(childCoveragePage.getTextOfRegimenPCV10Dose1(),"F10");
    SeleneseTestBase.assertEquals("300", childCoverageDetails.getInt("targetGroup"));

    homePage.navigateHomePage();
    homePage.navigateOfflineDistribution();
    distributionPage.clickRecordData(1);
    facilityListPage.selectFacility(facilityCodeFirst);

    refrigeratorPage.clickShowForRefrigerator1();
    assertEquals(refrigeratorPage.getRefrigeratorTemperateTextFieldValue(), "3");
    assertEquals(refrigeratorPage.getLowAlarmEventsTextFieldValue(), "1");
    assertEquals(refrigeratorPage.getHighAlarmEventsTextFieldValue(), "0");
    assertEquals(refrigeratorPage.getNotesTextAreaValue(), "miscellaneous");
    refrigeratorPage.verifyRefrigeratorColor("overall", "GREEN");
    refrigeratorPage.verifyRefrigeratorColor("individual", "GREEN");

    refrigeratorPage.navigateToEpiUse();
    epiUsePage.verifyIndicator("GREEN");

    epiUsePage.verifyTotal("30", 1);
    epiUsePage.verifyStockAtFirstOfMonth("10", 1);
    epiUsePage.verifyReceived("20", 1);
    epiUsePage.verifyDistributed("30", 1);
    epiUsePage.verifyLoss("", 1);
    epiUsePage.verifyLossStatus(false, 1);
    epiUsePage.verifyStockAtEndOfMonth("50", 1);
    epiUsePage.verifyExpirationDate("10/2011", 1);

    EpiInventoryPage epiInventoryPage = epiUsePage.navigateToEpiInventory();
    epiInventoryPage.applyNRToAll();
    epiInventoryPage.fillDeliveredQuantity(1, "10");
    epiInventoryPage.fillDeliveredQuantity(2, "20");
    epiInventoryPage.fillDeliveredQuantity(3, "30");

    epiInventoryPage.verifyIndicator("GREEN");

    FullCoveragePage fullCoveragePage = epiInventoryPage.navigateToFullCoverage();
    fullCoveragePage.enterData(5, 7, 0, "9999999");
    fullCoveragePage.verifyIndicator("GREEN");

    facilityListPage.verifyFacilityIndicatorColor("Overall", "GREEN");

    homePage.navigateHomePage();
    homePage.navigateOfflineDistribution();

    distributionPage.syncDistribution(1);
    assertTrue(distributionPage.isFacilitySyncFailed());

    switchOnNetworkInterface(wifiInterface);
    testWebDriver.sleep(7000);

    distributionPage.clickRetryButton();
    assertTrue("Incorrect Sync Facility", distributionPage.getSyncMessage().contains("F10-Village Dispensary"));

    distributionPage.syncDistributionMessageDone();
    distributionPage.clickRecordData(1);
    facilityListPage.selectFacility(facilityCodeFirst);
    facilityListPage.verifyFacilityIndicatorColor("Overall", "BLUE");

    verifyEpiUseDataInDatabase(10, 20, 30, null, 50, "10/2011", "PG1", facilityCodeFirst);
    verifyRefrigeratorReadingDataInDatabase(facilityCodeFirst, "GR-J287PGHV", 3F, "Y", 1, 0, "D", "miscellaneous");
    verifyRefrigeratorProblemDataNullInDatabase("GR-J287PGHV", facilityCodeFirst);
    verifyGeneralObservationsDataInDatabase(facilityCodeFirst, "some observations", "samuel", "Doe", "Verifier", "XYZ");
    verifyFullCoveragesDataInDatabase(5, 7, 0, 9999999, facilityCodeFirst);
    verifyEpiInventoryDataInDatabase(null, "10", null, "P10", facilityCodeFirst);
    verifyEpiInventoryDataInDatabase(null, "20", null, "Product6", facilityCodeFirst);
    verifyEpiInventoryDataInDatabase(null, "30", null, "P11", facilityCodeFirst);

    refrigeratorPage.navigateToGeneralObservations();
    generalObservationPage.verifyAllFieldsDisabled();

    generalObservationPage.navigateToEpiUse();
    epiUsePage.verifyAllFieldsDisabled();

    epiUsePage.navigateToRefrigerators();
    refrigeratorPage.clickShowForRefrigerator1();
    refrigeratorPage.verifyAllFieldsDisabled();

    refrigeratorPage.navigateToFullCoverage();
    assertFalse(fullCoveragePage.getStatusForField("femaleHealthCenter"));
    assertFalse(fullCoveragePage.getStatusForField("femaleMobileBrigade"));
    assertFalse(fullCoveragePage.getStatusForField("maleHealthCenter"));
    assertFalse(fullCoveragePage.getStatusForField("maleMobileBrigade"));

    loginPage = new LoginPage(testWebDriver, baseUrlGlobal);
    testWebDriver.sleep(1000);
    homePage = loginPage.loginAs(userSIC, password);
    testWebDriver.sleep(1000);

    distributionPage = homePage.navigateToDistributionWhenOnline();
    testWebDriver.sleep(1000);
    distributionPage.deleteDistribution();
    distributionPage.clickOk();

    distributionPage.selectValueFromDeliveryZone(deliveryZoneNameFirst);
    distributionPage.selectValueFromProgram(programFirst);
    distributionPage.clickInitiateDistribution();

    testWebDriver.sleep(1000);
    distributionPage.selectValueFromPeriod("Period13");
    distributionPage.clickInitiateDistribution();

    waitForAppCacheComplete();

    distributionPage.clickRecordData(1);
    assertTrue(facilityListPage.getFacilitiesInDropDown().contains("F10"));
    refrigeratorPage = facilityListPage.selectFacility(facilityCodeFirst);
    facilityListPage.verifyFacilityIndicatorColor("Overall", "RED");

    refrigeratorPage.verifyIndicator("RED");

    String data = "LG;800 LITRES;GR-J287PGHV";
    String[] refrigeratorDetailsOnUI = data.split(";");
    for (int i = 0; i < refrigeratorDetails.length; i++)
      assertEquals(testWebDriver.getElementByXpath("//div[@class='list-row ng-scope']/ng-include/form/div[1]/div[" + (i + 2) + "]").getText(), refrigeratorDetailsOnUI[i]);
  }

  private void configureISA() throws IOException {

    LoginPage loginPage = new LoginPage(testWebDriver, baseUrlGlobal);
    HomePage homePage = loginPage.loginAs("Admin123", "Admin123");

    ProgramProductISAPage programProductISAPage = homePage.navigateProgramProductISA();
    programProductISAPage.fillProgramProductISA("VACCINES", "90", "1", "50", "30", "0", "100", "2000", "333");
    homePage.logout();
  }

  @AfterMethod(groups = {"offline"})
  public void tearDownNew() throws Exception {
    switchOnNetworkInterface(wifiInterface);
    testWebDriver.sleep(5000);
    dbWrapper.deleteData();
    dbWrapper.closeConnection();
    ((JavascriptExecutor) TestWebDriver.getDriver()).executeScript("indexedDB.deleteDatabase('open_lmis');");
  }

  @DataProvider(name = "Data-Provider-Function")
  public Object[][] parameterIntTestProviderPositive() {
    return new Object[][]{
      {"storeIncharge", "Admin123", "DZ1", "DZ2", "Delivery Zone First", "Delivery Zone Second",
        "F10", "F11", "VACCINES", "TB", "M"}
    };
  }
}