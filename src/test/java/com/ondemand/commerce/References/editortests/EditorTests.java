package com.ondemand.commerce.References.editortests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.Reference.EditorPage;
import com.ondemand.commerce.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditorTests extends TestUtilities {

	@Test
	public void defaultEditorValueTest() {
		log.info("Starting defaultEditorValueTest");

		// open main page
		WelcomePage welcomePage = new WelcomePage(driver, log);
		welcomePage.openPage();
		
		// Scroll to the bottom
		sleep(5000);
		welcomePage.scrollToBottom();
		sleep(5000);

		// Click on WYSIWYG Editor link
		EditorPage editorPage = welcomePage.clickWYSIWYGEditorLink();

		// Get default editor text
		String editorText = editorPage.getEditorText();

		// Verification that new page contains expected text in source
		Assert.assertTrue(editorText.equals("Your content goes here."),
				"Editor default text is not expected. It is: " + editorText);
	}
}
