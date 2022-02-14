package com.ondemand.commerce.References.draganddroptests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.Reference.DragAndDropPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragAndDropTests extends TestUtilities {

	@Test
	public void dragAToBTest() {
		log.info("Starting dragAToBTest");

		// Open DragAndDropPage
		DragAndDropPage dragAndDropPage = new DragAndDropPage(driver, log);
		dragAndDropPage.openPage();

		// Drag box A and drop it on box B
		dragAndDropPage.dragAtoB();

		// Verify correct headers in correct boxes
		String columnAText = dragAndDropPage.getColumnAText();
		Assert.assertTrue(columnAText.equals("B"), "Column A header should be B, but it is: " + columnAText);
		
		String columnBText = dragAndDropPage.getColumnBText();
		Assert.assertTrue(columnBText.equals("A"), "Column A header should be B, but it is: " + columnBText);
	}
}
