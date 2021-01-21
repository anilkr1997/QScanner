package com.bspl.qscanner.pdfconverter;

import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class imagetopdf  {



    public static void pdfgeneration(ArrayList arrayList) {


        ImageData data = null;
        Image image ;
        PageSize pagesize= PageSize.A4;
        float margins=5.0f;
        try {
            String dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/BSPL"+System.currentTimeMillis()+".pdf";
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.addNewPage(pagesize);
            Document document = new Document(pdf, pagesize);
            pdf.setDefaultPageSize(pagesize);


            Table table = new Table(UnitValue.createPercentArray(1));
            table.setWidth(UnitValue.createPercentValue(100));

             document.setMargins(margins,margins,margins,margins);

            for(int j=0;j<=arrayList.size()-1;j++)
            {


                data = ImageDataFactory.create(String.valueOf(Uri.parse(String.valueOf(arrayList.get(j)))));
                image=new Image(data);

                image.scaleToFit(0,0);
                image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());

               // document.add(new Paragraph(String.format(" Page No %s  ",j+1)).setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.TOP).setBold());
                document.add(image);
             // document.setUnderline();

            }
            document.close();

            System.out.println("Image added");
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating an Image object

    }


}
