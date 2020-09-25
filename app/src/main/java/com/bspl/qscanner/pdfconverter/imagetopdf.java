package com.bspl.qscanner.pdfconverter;

import android.os.Environment;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

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
        float leftmorging,rightmorging,topmargin,bottemmargin,pagehight,pagewirth;
        try {
            String dest = Environment.getExternalStorageDirectory()+"/BSPL"+System.currentTimeMillis()+".pdf";
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf,pagesize);
            document.setMargins(margins,margins,margins,margins);



            for(int j=0;j<=arrayList.size()-1;j++)
            {
                data = ImageDataFactory.create(arrayList.get(j).toString());
                document.add(new Paragraph(String.format(" Page No %s  ",j+1)).setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.TOP).setBold());
                image=new Image(data);

               // image.scaleToFit(pagesize.getHeight(),pagesize.getWidth());
                image.setMargins(margins,margins,margins,margins);
                image.setAutoScale(true);
                document.add(image);

                //pdf.addNewPage(pagesize);


            }

            document.close();

            System.out.println("Image added");
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating an Image object

    }

}
