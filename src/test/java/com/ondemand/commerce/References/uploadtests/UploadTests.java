package com.ondemand.commerce.References.uploadtests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.Reference.FileUploaderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UploadTests extends TestUtilities {

	@Test(dataProvider="files")
	public void fileUploadTest(int no, String fileName) {
		log.info("Starting fileUploadTest #" + no + " for " + fileName);

		// open File Uploader Page
		FileUploaderPage fileUploaderPage = new FileUploaderPage(driver, log);
		fileUploaderPage.openPage();

		// Select file
		fileUploaderPage.selectFile(fileName);

		// Push upload button
		fileUploaderPage.pushUploadButton();

		// Get uploaded files
		String fileNames = fileUploaderPage.getUploadedFilesNames();

		// Verify selected file uploaded
		Assert.assertTrue(fileNames.contains(fileName),
				"Our file (" + fileName + ") is not one of the uploaded (" + fileNames + ")");
	}
}
