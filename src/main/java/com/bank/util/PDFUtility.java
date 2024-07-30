package com.bank.util;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.bank.model.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtility {

	public static byte[] generateTransactionPDF(String accountHolderName, String accountNumber,
			BigDecimal currentBalance, ArrayList<Transaction> transactions) throws DocumentException {
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();

		// Add account details
		document.add(new Paragraph("Account Holder Name: " + accountHolderName));
		document.add(new Paragraph("Account Number: " + accountNumber));
		document.add(new Paragraph("Current Balance: " + currentBalance));
		document.add(new Paragraph(" ")); // Add a blank line

		// Add transaction table
		PdfPTable table = new PdfPTable(3); // 3 columns: Date, Type, Amount
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);

		// Add table headers
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Date"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Type"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Amount"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		// Add transaction data
		for (Transaction transaction : transactions) {
			table.addCell(transaction.getTransactionDate().toString());
			table.addCell(transaction.getTransactionType());
			table.addCell(String.valueOf(transaction.getAmount()));
		}

		document.add(table);
		document.close();

		return baos.toByteArray();
	}
}
