package com.bank.web.account.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.bank.web.account.entity.Account;
import com.bank.web.account.model.AccountStatement;
import com.bank.web.account.model.TransactionEvent;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementPDFExporter {
	private AccountStatement accountStatement;
    
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("S.No", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Transaction Date", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Remarks", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Withdrawal Amount", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Deposit Amount", font));
        table.addCell(cell);       
    }
     
    private void writeTableData(PdfPTable table) {
    	PdfPCell cell = new PdfPCell();
        cell.setPadding(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Font font = new Font();
        font.setSize(8);
        
    	List<TransactionEvent> transactionEvents = accountStatement.getTransactionEvents();
    	long sNo = 1;
        for (TransactionEvent transactionEvent : transactionEvents) {
        	cell.setPhrase(new Phrase(String.valueOf(sNo), font));
        	table.addCell(cell);
        	
        	cell.setPhrase(new Phrase(transactionEvent.getTransactionDate(), font));
        	table.addCell(cell);
            
            cell.setPhrase(new Phrase(transactionEvent.getRemark(), font));
            table.addCell(cell);
            
            cell.setPhrase(new Phrase(String.valueOf(transactionEvent.getWithdrawalAmount()), font));
            table.addCell(cell);
            
            cell.setPhrase(new Phrase(String.valueOf(transactionEvent.getDepositAmount()), font));
            table.addCell(cell);
            
            sNo++;
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        addMetaInfo(document);
         
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {0.8f, 3f, 3.0f, 2.4f, 2f});
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }

	private void addMetaInfo(Document document) {
		
		Font fontFirstLine = FontFactory.getFont(FontFactory.COURIER_OBLIQUE);
		fontFirstLine.setSize(20);
		
		Paragraph globalHeader = new Paragraph("DETAILED STATEMENT", fontFirstLine);
		globalHeader.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(globalHeader);
		
		document.add(new Paragraph());
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
	    font.setSize(10);
	        
		Account account = accountStatement.getAccount();
        Paragraph acNumber = new Paragraph("Account Number - "+account.getAccountNo(), font);
        document.add(acNumber);
        
        Paragraph acType = new Paragraph("Type - "+StringUtils.toRootLowerCase(account.getAccountType().toString()), font);
        document.add(acType);
        
        Paragraph balance = new Paragraph("Balance - "+account.getBalance(), font);
        document.add(balance);
        
        Paragraph customerNames = new Paragraph("Customer Name - "+account.getCustomerNames(), font);
        document.add(customerNames);
        
        Paragraph transactionPeriod = new Paragraph("Transaction Period - "+accountStatement.getTransactionPeriod(), font);
        document.add(transactionPeriod);
	}
}
