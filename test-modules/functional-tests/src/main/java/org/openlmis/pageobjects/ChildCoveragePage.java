package org.openlmis.pageobjects;


import org.openlmis.UiUtils.TestWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static org.openqa.selenium.support.How.ID;

public class ChildCoveragePage extends DistributionTab {

  @FindBy(how = ID, using = "childCoverageTabLabel")
  private static WebElement childCoverageTab = null;

  @FindBy(how = ID, using = "coverageHeader")
  private static WebElement childCoverageHeader = null;

  @FindBy(how = ID, using = "colBCG")
  private static WebElement regimenBCG = null;

  @FindBy(how = ID, using = "colPolio (Newborn)")
  private static WebElement regimenPolioNewborn = null;

  @FindBy(how = ID, using = "colPolio 1a dose")
  private static WebElement regimenPolioDose1 = null;

  @FindBy(how = ID, using = "colPolio 2a dose")
  private static WebElement regimenPolioDose2 = null;

  @FindBy(how = ID, using = "colPolio 3a dose")
  private static WebElement regimenPolioDose3 = null;

  @FindBy(how = ID, using = "colIPV")
  private static WebElement regimenIPV = null;

  @FindBy(how = ID, using = "colPenta 1a dose")
  private static WebElement regimenPentaDose1 = null;

  @FindBy(how = ID, using = "colPenta 2a dose")
  private static WebElement regimenPentaDose2 = null;

  @FindBy(how = ID, using = "colPenta 3a dose")
  private static WebElement regimenPentaDose3 = null;

  @FindBy(how = ID, using = "colPCV10 1a dose")
  private static WebElement regimenPCV10Dose1 = null;

  @FindBy(how = ID, using = "colPCV10 2a dose")
  private static WebElement regimenPCV10Dose2 = null;

  @FindBy(how = ID, using = "colPCV10 3a dose")
  private static WebElement regimenPCV10Dose3 = null;

  @FindBy(how = ID, using = "colRV Rotarix 1a dose")
  private static WebElement regimenRVRotarixDose1 = null;

  @FindBy(how = ID, using = "colRV Rotarix 2a dose")
  private static WebElement regimenRVRotarixDose2 = null;

  @FindBy(how = ID, using = "colSarampo 1a dose")
  private static WebElement regimenSarampoDose1 = null;

  @FindBy(how = ID, using = "colSarampo 2a dose")
  private static WebElement regimenSarampoDose2 = null;

  @FindBy(how = ID, using = "vaccination")
  private static WebElement headerChildVaccination = null;

  @FindBy(how = ID, using = "targetGroup")
  private static WebElement headerTargetGroup = null;

  @FindBy(how = ID, using = "categoryOneHealthCenter")
  private static WebElement headerCategoryOneHealthCenter = null;

  @FindBy(how = ID, using = "categoryOneMobileBrigade")
  private static WebElement headerCategoryOneMobileBrigade = null;

  @FindBy(how = ID, using = "categoryOneTotal")
  private static WebElement headerCategoryOneTotal = null;

  @FindBy(how = ID, using = "coverageRate")
  private static WebElement headerCoverageRate = null;

  @FindBy(how = ID, using = "categoryTwoHealthCenter")
  private static WebElement headerCategoryTwoHealthCenter = null;

  @FindBy(how = ID, using = "categoryTwoMobileBrigade")
  private static WebElement headerCategoryTwoMobileBrigade = null;

  @FindBy(how = ID, using = "categoryTwoTotal")
  private static WebElement headerCategoryTwoTotal = null;

  @FindBy(how = ID, using = "totalVaccination")
  private static WebElement headerTotalVaccination = null;

  @FindBy(how = ID, using = "openedVials")
  private static WebElement headerOpenedVials = null;

  @FindBy(how = ID, using = "openedVialsWastageRate")
  private static WebElement headerOpenedVialsWastageRate = null;

  @FindBy(how = ID, using = "childrenAgeGroup1")
  private static WebElement headerChildrenAgeGroup1 = null;

  @FindBy(how = ID, using = "childrenAgeGroup2")
  private static WebElement headerChildrenAgeGroup2 = null;

  @FindBy(how = ID, using = "BCG")
  private static WebElement openedVialsBCGLabel = null;

  @FindBy(how = ID, using = "Polio10")
  private static WebElement openedVialsPolio10Label = null;

  @FindBy(how = ID, using = "Polio20")
  private static WebElement openedVialsPolio20Label = null;

  @FindBy(how = ID, using = "IPV")
  private static WebElement openedVialsIPVLabel = null;

  @FindBy(how = ID, using = "Penta1")
  private static WebElement openedVialsPenta1Label = null;

  @FindBy(how = ID, using = "Penta10")
  private static WebElement openedVialsPenta10Label = null;

  @FindBy(how = ID, using = "PCV")
  private static WebElement openedVialsPCVLabel = null;

  @FindBy(how = ID, using = "RV Rotarix")
  private static WebElement openedVialsRVRotarixLabel = null;

  @FindBy(how = ID, using = "Sarampo")
  private static WebElement openedVialsSarampoLabel = null;

  @FindBy(how = ID, using = "MSD")
  private static WebElement openedVialsMSDLabel = null;

  @FindBy(how = ID, using = "coverageOpenedVial00")
  private static WebElement openedVialsBcgNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial11")
  private static WebElement openedVialsPolioNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial61")
  private static WebElement openedVialsPentaNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial90")
  private static WebElement openedVialsPcvNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial120")
  private static WebElement openedVialsRVRotarixNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial140")
  private static WebElement openedVialsSarampoNR = null;

  @FindBy(how = ID, using = "coverageOpenedVial150")
  private static WebElement openedVialsMSDNR = null;

  @FindBy(how = ID, using = "childCoverageTable")
  private static WebElement childCoverageTable = null;

  @FindBy(how = ID, using = "childCoverageApplyNRAll")
  private static WebElement applyNrToAllButton = null;

  @FindBy(how = ID, using = "button_OK")
  private static WebElement okButton = null;

  @FindBy(how = ID, using = "button_Cancel")
  private static WebElement cancelButton = null;

  @FindBy(how = ID, using = "childCoverageTabIcon")
  private static WebElement childCoverageStatusIcon = null;

  public ChildCoveragePage(TestWebDriver driver) {
    super(driver);
  }

  @Override
  public void verifyIndicator(String color) {
    verifyOverallIndicator(childCoverageStatusIcon, color);
  }

  @Override
  public void enterValues(List<Map<String, String>> data) {
    Map<String, String> dataMap = data.get(0);
    for (int rowNumber = 1; rowNumber <= 16; rowNumber++) {
      if (rowNumber != 16) {
        enterTotalHealthCenter11MonthsDataForGivenRow(rowNumber, dataMap.get("healthCenter11"));
        enterTotalOutreach11MonthsDataForGivenRow(rowNumber, dataMap.get("outreach11"));
      }

      if (rowNumber != 2 && rowNumber != 6) {
        enterTotalHealthCenter23MonthsDataForGivenRow(rowNumber, dataMap.get("healthCenter23"));
        enterTotalOutreach23MonthsDataForGivenRow(rowNumber, dataMap.get("outreach23"));
      }
    }
    enterOpenedVialsCountForGivenGroupAndRow(1, 1, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(2, 1, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(2, 2, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(6, 1, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(6, 2, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(9, 1, dataMap.get("openedVial"));
    enterOpenedVialsCountForGivenGroupAndRow(12, 1, dataMap.get("openedVial"));
  }

  @Override
  public void verifyData(List<Map<String, String>> data) {
    Map<String, String> dataMap = data.get(0);
    assertEquals(dataMap.get("targetGroup"), getTextOfTargetGroupValue(9));
    assertEquals(dataMap.get("healthCenter11"), getTotalHealthCenter11MonthsDataForGivenRow(9));
    assertEquals(dataMap.get("outreach11"), getTotalOutreach11MonthsDataForGivenRow(9));
    assertEquals(dataMap.get("total1"), getTotalForGivenColumnAndRow(1, 9));
    assertEquals(dataMap.get("coverageRate"), getCoverageRateForGivenRow(9));
    assertEquals(dataMap.get("healthCenter23"), getTotalHealthCenter23MonthsDataForGivenRow(9));
    assertEquals(dataMap.get("outreach23"), getTotalOutreach23MonthsDataForGivenRow(9));
    assertEquals(dataMap.get("total2"), getTotalForGivenColumnAndRow(2, 9));
    assertEquals(dataMap.get("total3"), getTotalForGivenColumnAndRow(3, 9));
    assertEquals(dataMap.get("openedVial"), getOpenedVialsCountForGivenGroupAndRow(9, 1));
    assertEquals(dataMap.get("wastageRate"), getWastageRateForGivenRow(9));
  }

  @Override
  public void navigate() {
    testWebDriver.waitForElementToAppear(childCoverageTab);
    childCoverageTab.click();
    removeFocusFromElement();
  }

  @Override
  public void verifyAllFieldsDisabled() {
    for (int rowNumber = 1; rowNumber <= 12; rowNumber++) {
      assertFalse(isTotalHealthCenter11MonthsEnabledForGivenRow(rowNumber));
      assertFalse(isTotalOutreach11MonthsEnabledForGivenRow(rowNumber));
      if (rowNumber != 2) {
        assertFalse(isTotalHealthCenter23MonthsEnabledForGivenRow(rowNumber));
        assertFalse(isTotalOutreach23MonthsEnabledForGivenRow(rowNumber));
      }
    }
    assertFalse(isOpenVialEnabled(1, 1));
    assertFalse(isOpenVialEnabled(2, 1));
    assertFalse(isOpenVialEnabled(2, 2));
    assertFalse(isOpenVialEnabled(6, 1));
    assertFalse(isOpenVialEnabled(6, 2));
    assertFalse(isOpenVialEnabled(9, 1));
    assertFalse(isOpenVialEnabled(12, 1));
  }

  public String getTextOfRegimenBCG() {
    testWebDriver.waitForElementToAppear(regimenBCG);
    return regimenBCG.getText();
  }

  public String getTextOfRegimenPolioNewBorn() {
    testWebDriver.waitForElementToAppear(regimenPolioNewborn);
    return regimenPolioNewborn.getText();
  }

  public String getTextOfRegimenPolioDose1() {
    testWebDriver.waitForElementToAppear(regimenPolioDose1);
    return regimenPolioDose1.getText();
  }

  public String getTextOfRegimenPolioDose2() {
    testWebDriver.waitForElementToAppear(regimenPolioDose2);
    return regimenPolioDose2.getText();
  }

  public String getTextOfRegimenPolioDose3() {
    testWebDriver.waitForElementToAppear(regimenPolioDose3);
    return regimenPolioDose3.getText();
  }

  public String getTextOfRegimenIPV() {
    testWebDriver.waitForElementToAppear(regimenIPV);
    return regimenIPV.getText();
  }

  public String getTextOfRegimenPentaDose1() {
    testWebDriver.waitForElementToAppear(regimenPentaDose1);
    return regimenPentaDose1.getText();
  }

  public String getTextOfRegimenPentaDose2() {
    testWebDriver.waitForElementToAppear(regimenPentaDose2);
    return regimenPentaDose2.getText();
  }

  public String getTextOfRegimenPentaDose3() {
    testWebDriver.waitForElementToAppear(regimenPentaDose3);
    return regimenPentaDose3.getText();
  }

  public String getTextOfRegimenPCV10Dose1() {
    testWebDriver.waitForElementToAppear(regimenPCV10Dose1);
    return regimenPCV10Dose1.getText();
  }

  public String getTextOfRegimenPCV10Dose2() {
    testWebDriver.waitForElementToAppear(regimenPCV10Dose2);
    return regimenPCV10Dose2.getText();
  }

  public String getTextOfRegimenPCV10Dose3() {
    testWebDriver.waitForElementToAppear(regimenPCV10Dose3);
    return regimenPCV10Dose3.getText();
  }

  public String getTextOfRegimenRVRotarixDose1() {
    testWebDriver.waitForElementToAppear(regimenRVRotarixDose1);
    return regimenRVRotarixDose1.getText();
  }

  public String getTextOfRegimenRVRotarixDose2() {
    testWebDriver.waitForElementToAppear(regimenRVRotarixDose2);
    return regimenRVRotarixDose2.getText();
  }

  public String getTextOfRegimenSarampoDose1() {
    testWebDriver.waitForElementToAppear(regimenSarampoDose1);
    return regimenSarampoDose1.getText();
  }

  public String getTextOfRegimenSarampoDose2() {
    testWebDriver.waitForElementToAppear(regimenSarampoDose2);
    return regimenSarampoDose2.getText();
  }

  public String getTextOfHeaderChildrenVaccination() {
    testWebDriver.waitForElementToAppear(headerChildVaccination);
    return headerChildVaccination.getText();
  }

  public String getTextOfHeaderTargetGroup() {
    testWebDriver.waitForElementToAppear(headerTargetGroup);
    return headerTargetGroup.getText();
  }

  public String getTextOfHeaderHealthCenter1() {
    testWebDriver.waitForElementToAppear(headerCategoryOneHealthCenter);
    return headerCategoryOneHealthCenter.getText();
  }

  public String getTextOfHeaderMobileBrigade1() {
    testWebDriver.waitForElementToAppear(headerCategoryOneMobileBrigade);
    return headerCategoryOneMobileBrigade.getText();
  }

  public String getTextOfHeaderTotal1() {
    testWebDriver.waitForElementToAppear(headerCategoryOneTotal);
    return headerCategoryOneTotal.getText();
  }

  public String getTextOfHeaderCoverageRate() {
    testWebDriver.waitForElementToAppear(headerCoverageRate);
    return headerCoverageRate.getText();
  }

  public String getTextOfHeaderHealthCenter2() {
    testWebDriver.waitForElementToAppear(headerCategoryTwoHealthCenter);
    return headerCategoryTwoHealthCenter.getText();
  }

  public String getTextOfHeaderMobileBrigade2() {
    testWebDriver.waitForElementToAppear(headerCategoryTwoMobileBrigade);
    return headerCategoryTwoMobileBrigade.getText();
  }

  public String getTextOfHeaderTotal2() {
    testWebDriver.waitForElementToAppear(headerCategoryTwoTotal);
    return headerCategoryTwoTotal.getText();
  }

  public String getTextOfHeaderTotalVaccination() {
    testWebDriver.waitForElementToAppear(headerTotalVaccination);
    return headerTotalVaccination.getText();
  }

  public String getTextOfHeaderOpenedVials() {
    testWebDriver.waitForElementToAppear(headerOpenedVials);
    return headerOpenedVials.getText();
  }

  public String getTextOfHeaderWastageRate() {
    testWebDriver.waitForElementToAppear(headerOpenedVialsWastageRate);
    return headerOpenedVialsWastageRate.getText();
  }

  public String getTextOfHeaderCategory1() {
    testWebDriver.waitForElementToAppear(headerChildrenAgeGroup1);
    return headerChildrenAgeGroup1.getText();
  }

  public String getTextOfHeaderCategory2() {
    testWebDriver.waitForElementToAppear(headerChildrenAgeGroup2);
    return headerChildrenAgeGroup2.getText();
  }

  public String getTextOfOpenedVialsBCG() {
    testWebDriver.waitForElementToAppear(openedVialsBCGLabel);
    return openedVialsBCGLabel.getText();
  }

  public String getTextOfOpenedVialsPolio10() {
    testWebDriver.waitForElementToAppear(openedVialsPolio10Label);
    return openedVialsPolio10Label.getText();
  }

  public String getTextOfOpenedVialsPolio20() {
    testWebDriver.waitForElementToAppear(openedVialsPolio20Label);
    return openedVialsPolio20Label.getText();
  }

  public String getTextOfOpenedVialsIPV() {
    testWebDriver.waitForElementToAppear(openedVialsIPVLabel);
    return openedVialsIPVLabel.getText();
  }

  public String getTextOfOpenedVialsPenta1() {
    testWebDriver.waitForElementToAppear(openedVialsPenta1Label);
    return openedVialsPenta1Label.getText();
  }

  public String getTextOfOpenedVialsPenta10() {
    testWebDriver.waitForElementToAppear(openedVialsPenta10Label);
    return openedVialsPenta10Label.getText();
  }

  public String getTextOfOpenedVialsPCV() {
    testWebDriver.waitForElementToAppear(openedVialsPCVLabel);
    return openedVialsPCVLabel.getText();
  }

  public String getTextOfOpenedVialsRVRotarix() {
    testWebDriver.waitForElementToAppear(openedVialsRVRotarixLabel);
    return openedVialsRVRotarixLabel.getText();
  }

  public String getTextOfOpenedVialsSarampo() {
    testWebDriver.waitForElementToAppear(openedVialsSarampoLabel);
    return openedVialsSarampoLabel.getText();
  }

  public String getTextOfOpenedVialsMSD() {
    testWebDriver.waitForElementToAppear(openedVialsMSDLabel);
    return openedVialsMSDLabel.getText();
  }

  public String getTextOfTargetGroupValue(int rowNumber) {
    testWebDriver.waitForElementToAppear(testWebDriver.findElement(By.id("target" + (rowNumber - 1))));
    return testWebDriver.findElement(By.id("target" + (rowNumber - 1))).getText();
  }

  @Override
  public void removeFocusFromElement() {
    testWebDriver.waitForElementToAppear(childCoverageHeader);
    childCoverageHeader.click();
  }

  public String getTextOfChildCoverageTable() {
    testWebDriver.waitForElementToAppear(childCoverageTable);
    return childCoverageTable.getText();
  }

  public void enterTotalHealthCenter11MonthsDataForGivenRow(int rowNumber, String value) {
    WebElement totalHealthCenter11Months = testWebDriver.getElementById("totalHealthCenter11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter11Months);
    totalHealthCenter11Months.clear();
    totalHealthCenter11Months.sendKeys(value);
    totalHealthCenter11Months.sendKeys(Keys.TAB);
    testWebDriver.setImplicitWait(100);
  }

  public void enterTotalOutreach11MonthsDataForGivenRow(int rowNumber, String value) {
    WebElement totalOutreach11Months = testWebDriver.getElementById("totalOutreach11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach11Months);
    totalOutreach11Months.clear();
    totalOutreach11Months.sendKeys(value);
    totalOutreach11Months.sendKeys(Keys.TAB);
  }

  public void enterTotalHealthCenter23MonthsDataForGivenRow(int rowNumber, String value) {
    WebElement totalHealthCenter23Months = testWebDriver.getElementById("totalHealthCenter23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter23Months);
    totalHealthCenter23Months.clear();
    totalHealthCenter23Months.sendKeys(value);
    totalHealthCenter23Months.sendKeys(Keys.TAB);
  }

  public void enterTotalOutreach23MonthsDataForGivenRow(int rowNumber, String value) {
    WebElement totalOutreach23Months = testWebDriver.getElementById("totalOutreach23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach23Months);
    totalOutreach23Months.clear();
    totalOutreach23Months.sendKeys(value);
    totalOutreach23Months.sendKeys(Keys.TAB);
  }

  public String getTotalHealthCenter11MonthsDataForGivenRow(int rowNumber) {
    WebElement totalHealthCenter11Months = testWebDriver.getElementById("totalHealthCenter11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter11Months);
    return totalHealthCenter11Months.getAttribute("value");
  }

  public String getTotalOutreach11MonthsDataForGivenRow(int rowNumber) {
    WebElement totalOutreach11Months = testWebDriver.getElementById("totalOutreach11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach11Months);
    return totalOutreach11Months.getAttribute("value");
  }

  public String getTotalHealthCenter23MonthsDataForGivenRow(int rowNumber) {
    WebElement totalHealthCenter23Months = testWebDriver.getElementById("totalHealthCenter23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter23Months);
    return totalHealthCenter23Months.getAttribute("value");
  }

  public String getTotalOutreach23MonthsDataForGivenRow(int rowNumber) {
    WebElement totalOutreach23Months = testWebDriver.getElementById("totalOutreach23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach23Months);
    return totalOutreach23Months.getAttribute("value");
  }

  public void applyNRToTotalHealthCenter11MonthsForGivenRow(int rowNumber) {
    WebElement totalHealthCenter11MonthsNR = testWebDriver.getElementById("coverageTotalHealthCenter11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter11MonthsNR);
    totalHealthCenter11MonthsNR.click();
    removeFocusFromElement();
  }

  public void applyNRToTotalOutreach11MonthsForGivenRow(int rowNumber) {
    WebElement totalOutreach11MonthsNR = testWebDriver.getElementById("coverageTotalOutreach11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach11MonthsNR);
    totalOutreach11MonthsNR.click();
    removeFocusFromElement();
  }

  public void applyNRToTotalHealthCenter23MonthsForGivenRow(int rowNumber) {
    WebElement totalHealthCenter23MonthsNR = testWebDriver.getElementById("coverageTotalHealthCenter23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter23MonthsNR);
    totalHealthCenter23MonthsNR.click();
    removeFocusFromElement();
  }

  public void applyNRToTotalOutreach23MonthsDataForGivenRow(int rowNumber) {
    WebElement totalOutreach23MonthsNR = testWebDriver.getElementById("coverageTotalOutreach23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach23MonthsNR);
    totalOutreach23MonthsNR.click();
    removeFocusFromElement();
  }

  public void enterOpenedVialsCountForGivenGroupAndRow(int rowNumber, int position, String value) {
    WebElement openedVialsTextField = testWebDriver.getElementById("openedVial" + (rowNumber - 1) + (position - 1));
    testWebDriver.waitForElementToAppear(openedVialsTextField);
    openedVialsTextField.clear();
    openedVialsTextField.sendKeys(value);
    openedVialsTextField.sendKeys(Keys.TAB);
  }

  public String getOpenedVialsCountForGivenGroupAndRow(int rowNumber, int position) {
    WebElement openedVialsTextField = testWebDriver.getElementById("openedVial" + (rowNumber - 1) + (position - 1));
    testWebDriver.waitForElementToAppear(openedVialsTextField);
    return openedVialsTextField.getAttribute("value");
  }

  public void applyNrToBcgOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsBcgNR);
    openedVialsBcgNR.click();
    removeFocusFromElement();
  }

  public void applyNrToPolioOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsPolioNR);
    openedVialsPolioNR.click();
    removeFocusFromElement();
  }

  public void applyNrToPentaOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsPentaNR);
    openedVialsPentaNR.click();
    removeFocusFromElement();
  }

  public void applyNrToPcvOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsPcvNR);
    openedVialsPcvNR.click();
    removeFocusFromElement();
  }

  public void applyNrToRVRotarixOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsRVRotarixNR);
    openedVialsRVRotarixNR.click();
    removeFocusFromElement();
  }

  public void applyNrToSarampoOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsSarampoNR);
    openedVialsSarampoNR.click();
    removeFocusFromElement();
  }

  public void applyNrToMSDOpenedVials() {
    testWebDriver.waitForElementToAppear(openedVialsMSDNR);
    openedVialsMSDNR.click();
    removeFocusFromElement();
  }

  public String getTotalForGivenColumnAndRow(int columnNumber, int rowNumber) {
    WebElement total = testWebDriver.getElementById("total" + (columnNumber - 1) + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(total);
    return total.getText();
  }

  public String getWastageRateForGivenRow(int rowNumber) {
    WebElement wastageRate = testWebDriver.getElementById("wastageRateCalculated" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(wastageRate);
    return wastageRate.getText();
  }

  public String getCoverageRateForGivenRow(int rowNumber) {
    WebElement coverageRate = testWebDriver.getElementById("coverageRateCalculated" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(coverageRate);
    return coverageRate.getText();
  }

  public boolean isOpenVialEnabled(int groupNumber, int rowNumber) {
    WebElement openedVialsTextField = testWebDriver.getElementById("openedVial" + (groupNumber - 1) + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(openedVialsTextField);
    return openedVialsTextField.isEnabled();
  }

  public boolean isTotalHealthCenter11MonthsEnabledForGivenRow(int rowNumber) {
    WebElement totalHealthCenter11Months = testWebDriver.getElementById("totalHealthCenter11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter11Months);
    return totalHealthCenter11Months.isEnabled();
  }

  public boolean isTotalOutreach11MonthsEnabledForGivenRow(int rowNumber) {
    WebElement totalOutreach11Months = testWebDriver.getElementById("totalOutreach11Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach11Months);
    return totalOutreach11Months.isEnabled();
  }

  public boolean isTotalHealthCenter23MonthsEnabledForGivenRow(int rowNumber) {
    WebElement totalHealthCenter23Months = testWebDriver.getElementById("totalHealthCenter23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalHealthCenter23Months);
    return totalHealthCenter23Months.isEnabled();
  }

  public boolean isTotalOutreach23MonthsEnabledForGivenRow(int rowNumber) {
    WebElement totalOutreach23Months = testWebDriver.getElementById("totalOutreach23Months" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(totalOutreach23Months);
    return totalOutreach23Months.isEnabled();
  }

  public void applyNRToAll() {
    testWebDriver.waitForElementToAppear(applyNrToAllButton);
    applyNrToAllButton.click();
  }

  public void clickOK() {
    testWebDriver.waitForElementToAppear(okButton);
    okButton.click();
  }

  public void clickCancel() {
    testWebDriver.waitForElementToAppear(cancelButton);
    cancelButton.click();
  }

  public void enterOpenedVialsData() {
    enterOpenedVialsCountForGivenGroupAndRow(1, 1, "100");
    enterOpenedVialsCountForGivenGroupAndRow(2, 1, "200");
    enterOpenedVialsCountForGivenGroupAndRow(2, 2, "300");
    enterOpenedVialsCountForGivenGroupAndRow(6, 1, "400");
    enterOpenedVialsCountForGivenGroupAndRow(6, 2, "500");
    enterOpenedVialsCountForGivenGroupAndRow(9, 1, "600");
    enterOpenedVialsCountForGivenGroupAndRow(12, 1, "700");
  }

  public void enterAllChildCoverageValues() {
    for (int i = 1; i <= 12; i++) {
      if (i == 2) {
        enterTotalHealthCenter11MonthsDataForGivenRow(i, String.valueOf(i));
        enterTotalOutreach11MonthsDataForGivenRow(i, String.valueOf(i + 10));
      } else {
        enterTotalHealthCenter11MonthsDataForGivenRow(i, String.valueOf(i));
        enterTotalOutreach11MonthsDataForGivenRow(i, String.valueOf(i + 10));
        enterTotalHealthCenter23MonthsDataForGivenRow(i, String.valueOf(i + 100));
        enterTotalOutreach23MonthsDataForGivenRow(i, String.valueOf(i + 11));
      }
    }
  }
}