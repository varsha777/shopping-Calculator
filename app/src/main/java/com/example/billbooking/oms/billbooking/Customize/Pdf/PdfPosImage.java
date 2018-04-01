package com.example.billbooking.oms.billbooking.Customize.Pdf;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModelImage;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PdfPosImage {

    private PdfWriter pdfWriter;

    private static ArrayList<PosModelImage> arrayListRProductModel = new ArrayList<PosModelImage>();

    public boolean createPDF(Context context, String reportName, ArrayList<PosModelImage> posModelArrayList, String locationStore) {

        this.arrayListRProductModel = posModelArrayList;
        try {
            //creating a directory in SD card
            File mydir = new File(Environment.getExternalStorageDirectory()
                    + locationStore);
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            //getting the full path of the PDF report name
            String mPath = Environment.getExternalStorageDirectory().toString()
                    + locationStore
                    + reportName + ".pdf"; //reportName could be any name

            File pdfFile = new File(mPath);

            Document document = new Document(PageSize.A4);

            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            PageHeaderFooter event = new PageHeaderFooter();
            pdfWriter.setPageEvent(event);
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            Log.e("PDF Exception", e.getMessage());

            return false;
        }
        return true;
    }


    private static void addMetaData(Document document) {
        document.addTitle("All Product");
        document.addSubject("none");
        document.addKeywords("");
        document.addAuthor("Varsha");
        document.addCreator("BillBook Online");
    }


    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph paragraph = new Paragraph();

        Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
        Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph("BillBook Online Invoice", FONT_TITLE); //public static
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph("Product List", FONT_SUBTITLE); //public static
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = s.format(new Date());

        childParagraph = new Paragraph("Report generated on: " + currentTime, FONT_SUBTITLE);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

        addEmptyLine(paragraph, 2);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        //End of adding several titles

    }


    private static void addContent(Document document) throws DocumentException {

        Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        Paragraph reportBody = new Paragraph();
        reportBody.setFont(FONT_BODY); //public static

        createTable(reportBody);

        document.add(reportBody);

    }

    /**
     * This method is responsible to add table using iText
     *
     * @param reportBody
     * @throws BadElementException
     */
    private static void createTable(Paragraph reportBody)
            throws BadElementException {

        float[] columnWidths = {5, 5, 5, 2}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(columnWidths);

        table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);

        Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        //Adding table headers
        PdfPCell cell = new PdfPCell(new Phrase("Product Name", FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //alignment
        cell.setBackgroundColor(new GrayColor(0.75f)); //cell background color
        cell.setFixedHeight(30); //cell height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quantity", FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Price", FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Total", FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);


        for (int i = 0; i < arrayListRProductModel.size(); i++) { //
            cell = new PdfPCell(new Phrase(arrayListRProductModel.get(i).getCommodity()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(arrayListRProductModel.get(i).getQuantity())));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(arrayListRProductModel.get(i).getPrice())));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(arrayListRProductModel.get(i).getTotal())));
            cell.setFixedHeight(28);
            table.addCell(cell);
        }

        reportBody.add(table);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


    class PageHeaderFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {

            PdfContentByte cb = writer.getDirectContent();

            Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);

            Phrase footer_poweredBy = new Phrase("Powered By JackFruit", FONT_HEADER_FOOTER); //public static
            Phrase footer_pageNumber = new Phrase("Page " + document.getPageNumber(), FONT_HEADER_FOOTER);

            // Header
            // ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header,
            // (document.getPageSize().getWidth()-10),
            // document.top() + 10, 0);

            // footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    footer_pageNumber,
                    (document.getPageSize().getWidth() - 10),
                    document.bottom() - 10, 0);

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer_poweredBy, (document.right() - document.left()) / 2
                            + document.leftMargin(), document.bottom() - 10, 0);
        }
    }


    private static void generateTableData() {

    }
}