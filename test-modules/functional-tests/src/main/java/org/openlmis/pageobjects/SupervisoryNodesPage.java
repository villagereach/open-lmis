/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.pageobjects;

import org.openlmis.UiUtils.TestWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;
import java.util.NoSuchElementException;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static org.openqa.selenium.support.How.CLASS_NAME;
import static org.openqa.selenium.support.How.ID;

public class SupervisoryNodesPage extends Page {

  @FindBy(how = ID, using = "supervisoryNodeTab")
  private static WebElement supervisoryNodeTab = null;

  @FindBy(how = ID, using = "searchOptionButton")
  private static WebElement searchOptionButton = null;

  @FindBy(how = ID, using = "searchSupervisoryNode")
  private static WebElement searchSupervisoryNodeParameter = null;

  @FindBy(how = ID, using = "supervisoryNodeAddNew")
  private static WebElement supervisoryNodeAddNew = null;

  @FindBy(how = ID, using = "searchOption0")
  private static WebElement searchOption1 = null;

  @FindBy(how = ID, using = "searchOption1")
  private static WebElement searchOption2 = null;

  @FindBy(how = ID, using = "searchSupervisoryNodeLabel")
  private static WebElement searchSupervisoryNodeLabel = null;

  @FindBy(how = ID, using = "noResultMessage")
  private static WebElement noResultMessage = null;

  @FindBy(how = ID, using  = "noFacilityResultMessage")
  private static WebElement noFacilityResultMessage = null;

  @FindBy(how = ID, using = "oneResultMessage")
  private static WebElement oneResultMessage = null;

  @FindBy(how = ID, using = "nResultsMessage")
  private static WebElement nResultsMessage = null;

  @FindBy(how = ID, using = "supervisoryNodeHeader")
  private static WebElement supervisoryNodeHeader = null;

  @FindBy(how = ID, using = "codeHeader")
  private static WebElement codeHeader = null;

  @FindBy(how = ID, using = "facilityHeader")
  private static WebElement facilityHeader = null;

  @FindBy(how = ID, using = "parentHeader")
  private static WebElement parentHeader = null;

  @FindBy(how = ID, using = "closeButton")
  private static WebElement closeSearchResultsButton = null;

  @FindBy(how = ID, using = "searchIcon")
  private static WebElement searchIcon = null;

  @FindBy(how = ID, using = "code")
  private static WebElement supervisoryNodeCode = null;

  @FindBy(how = ID, using = "name")
  private static WebElement supervisoryNodeName = null;

  @FindBy(how = ID, using = "description")
  private static WebElement supervisoryNodeDescription = null;

  @FindBy(how = ID, using = "searchParentNode")
  private static WebElement searchParentNode = null;

  @FindBy(how = CLASS_NAME, using = "search-list" )
  private static WebElement search_list = null;

  @FindBy(how = ID, using = "result0")
  private static WebElement result0 = null;

  @FindBy(how = ID, using = "clearSearch")
  private static WebElement clearSearch = null;

  @FindBy(how = ID, using = "associatedFacilityField")
  private static WebElement associatedFacilityField = null;

  @FindBy(how = ID, using = "searchAndFilter")
  private static WebElement searchAndFilter = null;

  @FindBy(how = ID, using = "searchFacility")
  private static WebElement searchFacility = null;

  @FindBy(how = ID, using = "facilityResult0")
  private static WebElement facilityResult0 = null;

  @FindBy(how = ID, using = "saveButton")
  private static WebElement saveButton = null;

  @FindBy(how = ID, using = "codeFieldError")
  private static WebElement codeFieldError = null;

  @FindBy(how = ID, using = "nameFieldError")
  private static WebElement nameFieldError = null;

  @FindBy(how = ID, using = "facilityFieldError")
  private static WebElement facilityFieldError = null;

  @FindBy(how = ID, using = "saveErrorMsgDiv")
  private static WebElement saveErrorMsgDiv = null;

  @FindBy(how = ID, using = "close_button")
  private static WebElement close_button = null;

  @FindBy(how = ID, using = "name0")
  private static WebElement firstLink = null;

  @FindBy(how = ID, using = "result0")
  private static WebElement firstSearchResultLink = null;

  @FindBy(how = ID, using = "searchSupervisoryNode")
  private static WebElement searchSupervisoryNode = null;

  public SupervisoryNodesPage(TestWebDriver driver) {
    super(driver);
    PageFactory.initElements(new AjaxElementLocatorFactory(TestWebDriver.getDriver(), 1), this);
    testWebDriver.setImplicitWait(1);
  }

  public String getSearchSupervisoryNodeLabel() {
    testWebDriver.waitForElementToAppear(searchSupervisoryNodeLabel);
    return searchSupervisoryNodeLabel.getText();
  }

  public String getSupervisoryNodeTabLabel() {
    testWebDriver.waitForElementToAppear(supervisoryNodeTab);
    return supervisoryNodeTab.getText();
  }

  public void clickSupervisoryNodeTab() {
    testWebDriver.waitForElementToAppear(supervisoryNodeTab);
    supervisoryNodeTab.click();
  }

  public boolean isAddNewButtonDisplayed() {
    try {
      testWebDriver.waitForElementToAppear(supervisoryNodeAddNew);
    } catch (TimeoutException e) {
      return false;
    } catch (NoSuchElementException e) {
      return false;
    }
    testWebDriver.waitForElementToAppear(supervisoryNodeAddNew);
    return supervisoryNodeAddNew.isDisplayed();
  }

  public void clickAddNewButton() {
    testWebDriver.waitForElementToAppear(supervisoryNodeAddNew);
    supervisoryNodeAddNew.click();
  }

  public void clickSearchOptionButton() {
    testWebDriver.waitForElementToAppear(searchOptionButton);
    searchOptionButton.click();
  }

  public String getSelectedSearchOption() {
    testWebDriver.waitForElementToAppear(searchOptionButton);
    return searchOptionButton.getText();
  }

  public void selectSupervisoryNodeAsSearchOption() {
    testWebDriver.waitForElementToAppear(searchOption1);
    searchOption1.click();
  }

  public void selectSupervisoryNodeParentAsSearchOption() {
    testWebDriver.waitForElementToAppear(searchOption2);
    searchOption2.click();
  }

  public void clickOnSearchOptionButton(){
    testWebDriver.click(searchOptionButton);
  }

  public void enterSearchParameter(String searchParameter) {
    testWebDriver.waitForElementToAppear(searchSupervisoryNodeParameter);
    sendKeys(searchSupervisoryNodeParameter, searchParameter);
  }

  public boolean isNoResultMessageDisplayed() {
    try {
      testWebDriver.waitForElementToAppear(noResultMessage);
    } catch (TimeoutException e) {
      return false;
    } catch (NoSuchElementException e) {
      return false;
    }
    return noResultMessage.isDisplayed();
  }

  public boolean isOneResultMessageDisplayed() {
    try {
      testWebDriver.waitForElementToAppear(oneResultMessage);
    } catch (TimeoutException e) {
      return false;
    } catch (NoSuchElementException e) {
      return false;
    }
    return oneResultMessage.isDisplayed();
  }

  public boolean isNResultsMessageDisplayed() {
    try {
      testWebDriver.waitForElementToAppear(nResultsMessage);
    } catch (TimeoutException e) {
      return false;
    } catch (NoSuchElementException e) {
      return false;
    }
    return nResultsMessage.isDisplayed();
  }

  public String getNResultsMessage() {
    testWebDriver.waitForElementToAppear(nResultsMessage);
    return nResultsMessage.getText();
  }

  public void closeSearchResults() {
    testWebDriver.waitForElementToAppear(closeSearchResultsButton);
    closeSearchResultsButton.click();
  }

  public boolean isSupervisoryNodeHeaderPresent() {
    try {
      testWebDriver.waitForElementToAppear(supervisoryNodeHeader);
    } catch (TimeoutException e) {
      return false;
    } catch (NoSuchElementException e) {
      return false;
    }
    return supervisoryNodeHeader.isDisplayed();
  }

  public String getSupervisoryNodeHeader() {
    testWebDriver.waitForElementToAppear(supervisoryNodeHeader);
    return supervisoryNodeHeader.getText();
  }

  public String getCodeHeader() {
    testWebDriver.waitForElementToAppear(codeHeader);
    return codeHeader.getText();
  }

  public String getParentHeader() {
    testWebDriver.waitForElementToAppear(parentHeader);
    return parentHeader.getText();
  }

  public String getFacilityHeader() {
    testWebDriver.waitForElementToAppear(facilityHeader);
    return facilityHeader.getText();
  }

  public String getSupervisoryNodeName(int rowNumber) {
    WebElement name = testWebDriver.getElementById("name" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(name);
    return name.getText();
  }

  public String getSupervisoryNodeCode(int rowNumber) {
    WebElement code = testWebDriver.getElementById("code" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(code);
    return code.getText();
  }

  public String getFacility(int rowNumber) {
    WebElement facility = testWebDriver.getElementById("facility" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(facility);
    return facility.getText();
  }

  public String getSearchSupervisoryNodeText(){
    testWebDriver.waitForElementToAppear(searchSupervisoryNode);
    return searchSupervisoryNode.getText();
  }

  public String getParent(int rowNumber) {
    WebElement parent = testWebDriver.getElementById("parent" + (rowNumber - 1));
    testWebDriver.waitForElementToAppear(parent);
    return parent.getText();
  }

  public void clickSearchIcon() {
    testWebDriver.waitForElementToAppear(searchIcon);
    searchIcon.click();
  }

  public boolean isSearchIconDisplayed() {
    testWebDriver.waitForElementToAppear(searchIcon);
    return searchIcon.isDisplayed();
  }



  public void editSelectedSupervisoryNode(String parentNode, String facilityName){
    searchParentNode.sendKeys(parentNode);
    verifySearchParentNodeResult();
    testWebDriver.waitForElementToAppear(search_list);
    result0.click();
    searchAssociatedFacility(facilityName);
    selectFirstFacilityToBeAssociated();
  }

  public void clickOnFirstSearchResult(){

    firstSearchResultLink.click();
    testWebDriver.waitForElementToAppear(supervisoryNodeName);

  }

  public void clickOnClearSearchResultButton(){
    clearSearch.click();
  }

  public boolean isClearSearchButtonIsVisible(){
    return clearSearch.isDisplayed();
  }



  public void clickOnFirstElement(){

      firstLink.click();
      testWebDriver.waitForElementToAppear(supervisoryNodeName);

    }

  public void verifySearchParentNodeResult()
  {
    assertTrue(search_list.isDisplayed());
    assertTrue(result0.isDisplayed());
    testWebDriver.waitForElementToAppear(search_list);
  }

  public void enterSupervisoryNodeCodeValue(String code){
    supervisoryNodeCode.sendKeys(code);
  }

  public void enterSupervisoryNodeNameValues(String name){
    supervisoryNodeName.sendKeys(name);
  }

  public void enterSupervisoryNodeDescriptionValue(String description){
    supervisoryNodeDescription.sendKeys(description);
  }

  public void enterSearchParentNodeParameter(String parentCode){
    searchParentNode.sendKeys(parentCode);
    testWebDriver.waitForElementToAppear(search_list);
    assertTrue(search_list.isDisplayed());
    assertTrue(result0.isDisplayed());
  }

  public void searchAssociatedFacility(String facilityCode){
    associatedFacilityField.click();

    testWebDriver.waitForElementToAppear(searchAndFilter);

    assertTrue(searchAndFilter.isDisplayed());

    searchFacility.sendKeys(facilityCode);

    searchIcon.click();

  }

  public void verifyAbsenceOfDisabledFacility(){

    assertEquals("No matches found for 'F10'", noFacilityResultMessage.getText());

  }

  public void clickCrossButton(){
    testWebDriver.waitForElementToAppear(close_button);
    close_button.click();
  }

  public String getSearchFacilityText(){
    return searchFacility.getText();
  }

  public void selectFirstFacilityToBeAssociated(){

    testWebDriver.click(facilityResult0);


}

  public void verifyErrorMessages(){
    assertEquals("Please fill this value", codeFieldError.getText());
    assertEquals("Please fill this value", nameFieldError.getText());
    assertEquals("Please select a value", facilityFieldError.getText());
    assertEquals("There are some errors in the form. Please resolve them.", saveErrorMsgDiv.getText());
  }

  public void clickSaveButton(){
    saveButton.click();
  }
}
