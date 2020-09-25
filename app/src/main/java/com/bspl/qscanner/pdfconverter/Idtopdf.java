package com.bspl.qscanner.pdfconverter;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Idtopdf {

    public static void Idgeneration(ArrayList arrayList) {
        ImageData data = null,data1=null ;
        Image image = null,image1=null ;
        PageSize pagesize= PageSize.A4;
        try {
            String dest = Environment.getExternalStorageDirectory()+"/BSPL"+System.currentTimeMillis()+".pdf";
            PdfWriter writer = new PdfWriter(dest);
            // Creating a PdfDocument
            PdfDocument pdf = new PdfDocument(writer);
            pdf.addNewPage(pagesize);
            // Creating a Document
            Document document = new Document(pdf,pagesize);
            Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            table.setMarginBottom(5.0f);
            table.setMarginTop(5.0f);
            table.setMarginRight(5.0f);
            table.setMarginLeft(5.0f);


            for(int j=0;j<=arrayList.size()-1;j++){
                if(j==0){
                    Log.e("j","0" + arrayList.get(j).toString());
                    data = ImageDataFactory.create(arrayList.get(j).toString());
                    image = new Image(data);


                }
                else if (j==1){
                    Log.e("j","1" + arrayList.get(j).toString());
                    data1 = ImageDataFactory.create(arrayList.get(j).toString());
                    image1 = new Image(data1);

                }

            }
            table.addCell(image.setAutoScale(true));
            table.addCell(image1.setAutoScale(true));


            document.add(table);
            document.close();
            System.out.println("Image added");
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating an Image object

    }

}
