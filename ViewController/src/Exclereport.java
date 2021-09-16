import java.io.IOException; //Exclereport.java
import java.io.OutputStream;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.faces.context.FacesContext;

import model.services.AppModuleImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;


//test

//test


//import test barchart//


//....................//




public class Exclereport {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFCellStyle style1 = workbook.createCellStyle();
    HSSFSheet worksheet = null;
    HSSFRow excelrow = null;
    HSSFCell excelcell = null;
    HSSFCellStyle tableHeaderStyle = workbook.createCellStyle();
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    HSSFCellStyle dataStyle = workbook.createCellStyle();
    HSSFFont hSSFFont1 = workbook.createFont();
    HSSFFont hSSFFont2 = workbook.createFont();
    HSSFFont hSSFFont3 = workbook.createFont();
    HSSFPalette palette = workbook.getCustomPalette();
    StringBuilder sb;
    StringBuilder sb1;
    
    //  -----------------------------------------------------------------------------------
    
    String tableHeaderFont = HSSFFont.FONT_ARIAL;
    String colHeaderFont = HSSFFont.FONT_ARIAL;
    String data=HSSFFont.FONT_ARIAL;
    Short tableHeaderFontSize = 11;
    Short colHeaderFontSize = 11;
    Short dataForntSize=13;
    Short tableHeaderFontColor = HSSFColor.BLACK.index;
    Short colHeaderFontColor = HSSFColor.BLACK.index;
    Short tableHeaderBgColor = HSSFColor.GREEN.index;
    
    byte r1 = (byte)153, r2 = (byte)255, r3 = (byte)153;
    Short colHeaderBgColor = HSSFColor.BLUE.index;
    byte r4 = (byte)153, r5 = (byte)255, r6 = (byte)153;
    Short dataBgColor = HSSFColor.YELLOW.index;
    byte r7 = (byte)240, r8 = (byte)255, r9 = (byte)240;

    String headerHeaderText = "Fabric Header";
    String lineHeaderText = "Fabric Line";
    String detailHeaderText = "Fabric Details";
    String wetHeaderText = "Wet Processing";
    String dryHeaderText = "Dry Processing";

    //----------------------------------------------------------------------------------------
    
    int headerNumOfCol = 7;
    int lineNumOfCol = 10;
    int detailNumOfCol = 6;
    int dryNumOfCol = 2;
    int wetNumOfCol = 2;

    int xlRow = 0;
    int xlRowNew = 0;
    int xlCol = 0;
    int xlColHeader = 0;
    int xlColLine = 7;
    int xlColDetail = 0;
    int xlColDry = 7;
    int xlColWet = 7;
    String colName; 
    
    
    ///////////////////////////////////////////////////
    
    {
        ///////////////////////////////////////////////////
        palette.setColorAtIndex(tableHeaderBgColor, r1, r2, r3);
        palette.setColorAtIndex(colHeaderBgColor, r4, r5, r6);
        palette.setColorAtIndex(dataBgColor, r7, r8, r9);

        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(colHeaderBgColor);
        hSSFFont1.setFontName(colHeaderFont);
        hSSFFont1.setFontHeightInPoints(colHeaderFontSize);
        // hSSFFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hSSFFont1.setColor(colHeaderFontColor);
        headerStyle.setFont(hSSFFont1);
        hSSFFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //style.setBorderBottom((short) 6); // double line border
        headerStyle.setBorderTop((short)1); // single lines border
        headerStyle.setBorderBottom((short)1);
        headerStyle.setBorderLeft((short)1);
        headerStyle.setBorderRight((short)1);
        headerStyle.setWrapText(true);
       
        dataStyle.setFont(hSSFFont3);
       hSSFFont3.setFontHeightInPoints (dataForntSize);
        dataStyle.setAlignment(CellStyle.ALIGN_CENTER);
        dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataStyle.setFillForegroundColor(dataBgColor);
    //  hSSFFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        dataStyle.setBorderBottom((short)1);
        dataStyle.setBorderLeft((short)1);
        dataStyle.setBorderRight((short)1);
        dataStyle.setLocked(true);
        dataStyle.setWrapText(true);
        

       
        tableHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        hSSFFont2.setFontHeightInPoints(tableHeaderFontSize);
        hSSFFont2.setFontName(tableHeaderFont);
        hSSFFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hSSFFont2.setColor(tableHeaderFontColor);
        tableHeaderStyle.setFont(hSSFFont2);
        tableHeaderStyle.setWrapText(true);
        tableHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        tableHeaderStyle.setFillForegroundColor(tableHeaderBgColor);


        ///////////////////////////////////////////////////////
        
       
    }
    ///////////////////////////////////////////////////////
    
    
    
    
    public Exclereport() {
    }
        
            AppModuleImpl appM = getAppModuleImpl();

    private AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
        AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
        
        
        
    public double addCellValue(int col) {
        return 0.0;
    }
    /**-------------------------------------------------------------------------------------------------
                     main method for writing data in excel   (excelReport Method)
    -----------------------------------------------------------------------------------------------------*/
        

    public void Report(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
       
        
        
        ViewObject HeaderVo = appM.getXX_OM_POC_H_TVO1();
        Row[] r = HeaderVo.getAllRowsInRange();
        Row row = HeaderVo.getCurrentRow();
        int count = 1;

																			  

        RowSet lineRowSet = (RowSet)row.getAttribute("XX_OM_POC_L_TVO");
          System.out.println("lineRowSet is ....."+lineRowSet);
        while (lineRowSet.hasNext()) {
            xlRow = 0;
            xlCol = 0;
            worksheet = workbook.createSheet("Line" + count++);
            /* password required for locks to become effective */
          // worksheet.protectSheet("protected");
          worksheet.getPrintSetup().setLandscape(true);
           /* password required for locks to become effective */
          // worksheet.protectSheet("protected");
          HSSFPrintSetup ps = (HSSFPrintSetup)  worksheet.getPrintSetup();
          worksheet.setAutobreaks(true);
          worksheet.setFitToPage(true);
          ps.setFitWidth((short)1);
           ps.setFitHeight((short)0); 
        ps.setHeaderMargin(.25);
            ps.setFooterMargin(.25);
           worksheet.setMargin(worksheet.LeftMargin, 0.25);
               worksheet.setMargin(worksheet.RightMargin, 0.25);
               worksheet.setMargin(worksheet.TopMargin, 0.75);
               worksheet.setMargin(worksheet.BottomMargin, 0.75);
          worksheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
           // worksheet.getPrintSetup().setLandscape(true);
        // worksheet.setFitToPage(true);
         // PrintSetup ps =worksheet.getPrintSetup();
         // ps.setFitWidth( (short) 1);
         // ps.setFitHeight( (short) 0);
   
     System.out.println("printarea.xlsx written successfully"); 
          
            
            
            for (int i = 0; i < 60; i++) {
                worksheet.createRow(i);
               
            }
           System.out.println("xlRow is ----:"+xlRow);
           printDate(xlRow);
          //  printTableHeader(headerNumOfCol, xlCol, headerHeaderText);
          xlRow++;
            System.out.println("parameter for print row header is :"+row+headerNumOfCol+xlCol);
            printRowHeader(row, headerNumOfCol, xlCol);
            printRowData(row, headerNumOfCol, xlCol);

            Row lineRow = lineRowSet.next();
            this.xlCol = xlColLine;
            this.xlRow = 1;

           // printTableHeader(lineNumOfCol, xlCol, lineHeaderText);
            System.out.println("parameter for line numof col is :2"+lineRow+lineNumOfCol+xlCol);
            printRowHeader(lineRow, lineNumOfCol, xlCol);
            printRowData(lineRow, lineNumOfCol, xlCol);

            this.xlRowNew = xlRow;
            this.xlRow = xlRowNew;


            RowSet detailRowSet;
            
            //to print item details table only for item prefix 11 and 12
          
            
            detailRowSet = (RowSet)lineRow.getAttribute("XX_OM_POC_D1_TVO");
            System.out.println("detail ROW SET is ....."+detailRowSet);
            xlCol = xlColDetail;
             detailNumOfCol = 5;
            detailHeaderText="Fabric Detail";
            if (detailRowSet.hasNext()) {
                Row detailRow = detailRowSet.next();
                detailRowSet.reset();
System.out.println("line 272 data is :"+ detailNumOfCol+   xlCol+  detailHeaderText); 
                printTableHeader(detailNumOfCol, xlCol, detailHeaderText);
                printRowHeader(detailRow, detailNumOfCol, xlCol);
            }

            while (detailRowSet.hasNext()) {
                xlCol = xlColDetail;
                Row detailRow = detailRowSet.next();
                
                oracle.jbo.domain.Number prefix = (Number)detailRow.getAttribute("ItemPrefix"); 
                if ((prefix.intValue() >= 11 && prefix.intValue() <= 12)){
                    printRowData(detailRow, detailNumOfCol, xlCol);
                }
                
                //this condition has been applied to view object's query 
            //    if (detailRow.getAttribute("ItemPrefix").toString().equals("11") ||        //to check that item prefix with 11 and 12 
            //        detailRow.getAttribute("ItemPrefix").toString().equals("12")) {         //isn't shown in the table
                   
            //    }
            }
            detailRowSet.reset();
            if (detailRowSet.hasNext()) {
               // the sum of all row values of FinalCostPerPcs column  will be printed
               colName = "CostPerPcs";
              addColValues(detailRowSet, colName, xlColDetail+2);
               
            }
            
            
            
            
            
            detailRowSet  = (RowSet)lineRow.getAttribute("trimsVO");
           
            xlCol = xlColDetail;
            detailNumOfCol = 5;
            detailHeaderText="Trim Detail";
            if (detailRowSet.hasNext()) {
               Row detailRow = detailRowSet.next();
               detailRowSet.reset();
               printTableHeader(detailNumOfCol, xlCol, detailHeaderText);
               printRowHeader(detailRow, detailNumOfCol, xlCol);
            }
            

            while (detailRowSet.hasNext()) {
               xlCol = xlColDetail;
               Row detailRow = detailRowSet.next();
                
                oracle.jbo.domain.Number prefix =
                    (Number)detailRow.getAttribute("ItemPrefix"); //ItemPrefix
                if(prefix.intValue() > 12 && prefix.intValue() <= 36){
                    printRowData(detailRow, detailNumOfCol, xlCol);
                }
                
                
                //this condition has been applied to item_Dnew view object's query
             //   if (!detailRow.getAttribute("ItemPrefix").toString().equals("11")&&         //to check that item prefix with 11 and 12 
             //      !detailRow.getAttribute("ItemPrefix").toString().equals("12")) {         //isn't shown in the table
                 
            //   }
            }
            detailRowSet.reset();
           
            if (detailRowSet.hasNext()) {
               // the sum of all row values of FinalCostPerPcs column  will be printed
               colName = "CostPerPcs";
              addColValues(detailRowSet, colName, xlColDetail+2);
               
            }
            

            RowSet dryRowSet = (RowSet)lineRow.getAttribute("XX_OM_POC_D2_TVO");
            this.xlRow = xlRowNew;
            xlCol = xlColDry;

            if (dryRowSet.hasNext()) {
                Row dryRow = dryRowSet.next();
                dryRowSet.reset();

                printTableHeader(dryNumOfCol, xlCol, dryHeaderText);
                printRowHeader(dryRow, dryNumOfCol, xlCol);

            }

            while (dryRowSet.hasNext()) {
                xlCol = xlColDry;
                Row dryRow = dryRowSet.next();

                printRowData(dryRow, dryNumOfCol, xlCol);
            }
            dryRowSet.reset();
            if (dryRowSet.hasNext()) {

                colName = "ManualPrice";
                addColValues(dryRowSet, colName, xlColDry+2);
            }


            RowSet wetRowSet = (RowSet)lineRow.getAttribute("XX_OM_POC_D2_TVO2");
            // this.xlRow=xlRowNew;
            xlCol = xlColWet;
            if (wetRowSet.hasNext()) {
                Row wetRow = wetRowSet.next();
                wetRowSet.reset();
                printTableHeader(wetNumOfCol, xlCol, wetHeaderText);
                printRowHeader(wetRow, wetNumOfCol, xlCol);
            }

            while (wetRowSet.hasNext()) {
                xlCol = xlColWet;
                Row wetRow = wetRowSet.next();
                printRowData(wetRow, wetNumOfCol, xlColWet);
            }
            wetRowSet.reset();
            if (wetRowSet.hasNext()) {
                // the sum of CostAmount column  will be printed
                colName = "ManualPrice";
                addColValues(wetRowSet, colName, xlColWet+2);
            }
            //....................others:...................//
            
            
            detailRowSet  = (RowSet)lineRow.getAttribute("others_VO");
             this.xlRow = xlRowNew;
            xlCol = xlColDetail+11;
            detailNumOfCol = 5;
            detailHeaderText="Others charge";
            if (detailRowSet.hasNext()) {
               Row detailRow = detailRowSet.next();
               detailRowSet.reset();
               printTableHeader(detailNumOfCol, xlCol, detailHeaderText);
               printRowHeader(detailRow, detailNumOfCol, xlCol);
            }

            while (detailRowSet.hasNext()) {
               xlCol = xlColDetail+11;
               Row detailRow = detailRowSet.next();
                
                oracle.jbo.domain.Number prefix =
                    (Number)detailRow.getAttribute("ItemPrefix"); 
                if (prefix.intValue()==55){
                    printRowData(detailRow, detailNumOfCol, xlCol);
                }
                //this condition has been applied to item_Dnew view object's query
             //   if (!detailRow.getAttribute("ItemPrefix").toString().equals("11")&&         //to check that item prefix with 11 and 12 
             //      !detailRow.getAttribute("ItemPrefix").toString().equals("12")) {         //isn't shown in the table
                  
            //   }
            }
            detailRowSet.reset();
            
            if (detailRowSet.hasNext()) {
               // the sum of all row values of FinalCostPerPcs column  will be printed
               colName = "CostPerPcs";
              addColValues(detailRowSet, colName, xlColDetail+13);
               
            }
            
            //.........................sam value..............//
          xlCol=11;  
      //  this.xlRow = xlRowNew;
       printAttributeForOtherCOst(detailNumOfCol, xlCol);
          System.out.println("NUM OF DETAIL NUM OF C0L AND XLCOL FOR PRRLINEH ,SAM,SRFFI"+detailNumOfCol+"   "+xlCol);
            
        }
       
        try {
            //test001
//            FileOutputStream out = new FileOutputStream(new File("printarea.xlsx"));
//                  workbook.write(out);
//                  out.close();
             
            workbook.write(outputStream);
            outputStream.flush();
           
        } catch (IOException e) {
        }
    }
    
    /**---------------------------------------------------------------------------------------------
                      method for printing  column headers in table in excel
    -------------------------------------------------------------------------------------------------*/


    public void printRowHeader(Row row, int numOfCol, int xlCol) {
       // xlRow++;
        excelrow = (HSSFRow)worksheet.getRow(xlRow);
        for (String colName : row.getAttributeNames()) {
            if (row.getAttributeIndexOf(colName) < numOfCol) {
                excelcell = excelrow.createCell(xlCol);
                excelcell.setCellStyle(headerStyle);
                sb = new StringBuilder(colName);
                 sb1 = new StringBuilder();
                char index;
                for (int i = 0; i < sb.length(); i++) {
                    index = sb.charAt(i);
                    if (i != 0 && Character.isUpperCase(index)) {
                        sb1.append(" ");
                        sb1.append(index);
                    } else {
                        sb1.append(index);
                    }
                }
             
                colName = sb1.toString();
                System.out.println("printing column name:"+colName);
                
                if(colName.equals("Poc Id")) {
                    colName="POC NO";
                }
                if(colName.equals("Fabric Details")) {
                    colName="Fabric Details";
                }
                if(colName.equals("Fob Value")) {
                    colName="FOB";
                }
                if(colName.equals("Manual Price")) {
                    colName="Price";
                }
                if(colName.equals("Cm Value")) {
                    colName="CM";
                }
                if(colName.equals("Fabric Cost")) {
                    colName="Fabric Cost with Finance";
                }
              
                
                excelcell.setCellValue(colName);
                worksheet.autoSizeColumn(xlCol);
             
                xlCol += 1;
            }
        }
        this.xlRow++;
    }
    
    
    /**------------------------------------------------------------------------------------------
                           method for printing  column data in table in excel
     ---------------------------------------------------------------------------------------------*/

    public void printRowData(Row row, int numOfCol, int xlCol) {
  System.out.println("data value are##########:"+row+numOfCol+xlCol);
        excelrow = (HSSFRow)worksheet.getRow(xlRow);
        for (String colName : row.getAttributeNames()) {
            if (row.getAttributeIndexOf(colName) < numOfCol) {
                excelcell = excelrow.createCell(xlCol);
               
                if (row.getAttribute(colName) != null) {
                   
                    excelcell.setCellValue(row.getAttribute(colName).toString());
                 
                    excelcell.setCellStyle(dataStyle);
                 
                    worksheet.autoSizeColumn(xlCol);
                   
                   
                }
                else  excelcell.setCellStyle(dataStyle);
                xlCol += 1;
            }
            this.xlCol = xlCol;
        }
        this.xlRow++;
    }
       
     /**--------------------------------------------------------------------------------------------
                                method for printing title of table in excel 
      -----------------------------------------------------------------------------------------------*/
    

    private void printTableHeader(int numOfCol, int xlCol, String headerText) {
        if (xlRow > 0) {
            this.xlRow += 3;
        }
        excelrow = (HSSFRow)worksheet.getRow(xlRow);
        excelcell = excelrow.createCell(xlCol);
        //
        CellRangeAddress cellRange =
            new CellRangeAddress(xlRow, //first row (0-based)
                xlRow, //last row  (0-based)
                xlCol, //first column (0-based)
                xlCol + numOfCol - 1); //last column  (0-based)
        excelcell.setCellStyle(tableHeaderStyle);
        worksheet.addMergedRegion(cellRange);
        excelcell.setCellValue(headerText);
        HSSFRegionUtil.setBorderTop(tableHeaderStyle.BORDER_THIN, cellRange,
                                    worksheet, workbook);
        HSSFRegionUtil.setBorderLeft(tableHeaderStyle.BORDER_THIN, cellRange,
                                     worksheet, workbook);
        HSSFRegionUtil.setBorderRight(tableHeaderStyle.BORDER_THIN, cellRange,
                                      worksheet, workbook);
        HSSFRegionUtil.setBorderBottom(tableHeaderStyle.BORDER_THIN, cellRange,
                                       worksheet, workbook);
        this.xlRow++;
    }
     
    /**----------------------------------------------------------------------------------------------
                   method for adding all values of a particular column and printing in excel
     -------------------------------------------------------------------------------------------------*/
    
    private void addColValues(RowSet rowSet, String colName, int xlCol) {
       // this.xlRow += 1;
        System.out.println("value for custom row :"+rowSet+"colname: "+colName+"xlcol"+xlCol);
        int colIndex = 0;
        rowSet.reset();
        if (rowSet.hasNext()) {
            Row row = rowSet.next();
            colIndex = row.getAttributeIndexOf(colName);
            excelrow = (HSSFRow)worksheet.getRow(xlRow);
            int a = xlCol + colIndex - 3;
            System.out.println("vaule of total field is :___xcol of a__"+xlCol+"col index"+ colIndex);
            //excelcell = excelrow.createCell(a);
          // excelcell.setCellStyle(dataStyle);
          // excelcell.setCellValue("Total :");
            worksheet.autoSizeColumn(xlColDry);
        }
        rowSet.reset();
        double total = 0;
        while (rowSet.hasNext()) {
            Row row = rowSet.next();
            String s1 = null;
            Double d1=0.0;
            try{
                s1= row.getAttribute(colName).toString();
                 d1 = Double.parseDouble(s1);
            }
            catch(Exception e){
               ;
            }
            total += d1;
        }
        total =Double.parseDouble(new DecimalFormat("##.####").format(total));
       //excelcell = excelrow.createCell(xlCol + colIndex-2);
       System.out.println("vaule of total field is :_____"+total+"___xcol_"+xlCol +"col index"+ colIndex);
        //excelcell.setCellStyle(dataStyle);
        //excelcell.setCellValue("" + total);
        worksheet.autoSizeColumn(xlCol);
    }


    public void doOnPageLoad() {
        System.out.println("+++++++++++++++++++++++ doOnPageLoad called +++++++++++++++++++");
    }

    private void printHeaderForSAM( int numOfCol, int xlCol) {
        excelrow = (HSSFRow)worksheet.getRow(xlRow);
         String[] array = {"Cheese", "Pepperoni", "Black Olives","Apple","Orange"};
         
        excelcell = excelrow.createCell(xlCol);
        excelcell.setCellStyle(headerStyle);
        for(int i=0;i<4;i++){
            
            
            
         excelcell = excelrow.createCell(xlCol);
            excelcell.setCellStyle(headerStyle);
            excelcell.setCellValue(array[i]);
            worksheet.autoSizeColumn(xlCol);  
              
             
           
                
                
             
                xlCol += 1;
        }
        this.xlRow++;
        
        
        
    }

    private void printAttributeForOtherCOst(int detailNumOfCol, int xlCol) {
       int dataxlCol= xlCol;
       ViewObject linedataVO= appM.getXX_OM_POC_L_TVO1();
      ViewObject headerdataVO=appM.getXX_OM_POC_H_TVO1();
        excelrow = (HSSFRow)worksheet.getRow(xlRow+3);
        String[] ArrayData = new String[5];
        try{
            ArrayData[0]=linedataVO.getCurrentRow().getAttribute("ProdPerLinePerH").toString();
            System.out.println("arraydata 1:"+ArrayData[0]);
        }
        catch(Exception e) {
            ArrayData[0]=null;
            System.out.println("in catch arraydata 0:"+ArrayData[0]);
           
        }
        try{
            ArrayData[1]=linedataVO.getCurrentRow().getAttribute("StyleEfficiency").toString();
            System.out.println("arraydata 1:"+ArrayData[1]);
        }
        catch(Exception e) {
            ArrayData[1]=null;
            System.out.println("in catch arraydata 1:"+ArrayData[1]);
        }
        try{
            ArrayData[2]=linedataVO.getCurrentRow().getAttribute("Sam").toString();
            System.out.println("arraydata 1:"+ArrayData[2]);
        }
        catch(Exception e) {
            ArrayData[2]=null;
            System.out.println("in catch arraydata 2:"+ArrayData[2]);
        }
        try{
            ArrayData[3]=linedataVO.getCurrentRow().getAttribute("CommisonPrcnt").toString();
           System.out.println("arraydata 1:"+ArrayData[3]);
       }
        catch(Exception e) {
            ArrayData[3]=null;
           System.out.println("in catch arraydata 3:"+ArrayData[3]);
       }
        try{
            ArrayData[4]=linedataVO.getCurrentRow().getAttribute("Shipment").toString();
            System.out.println("arraydata 1:"+ArrayData[4]);
        }
        catch(Exception e) {
            ArrayData[4]=null;
            System.out.println("in catch arraydata 4:"+ArrayData[4]);
        }
			
																						
		 
							
							  
		 
//        ArrayData[0]=linedataVO.getCurrentRow().getAttribute("ProdPerLinePerH").toString();
//        ArrayData[1]=linedataVO.getCurrentRow().getAttribute("StyleEfficiency").toString();
//        ArrayData[2]=linedataVO.getCurrentRow().getAttribute("Sam").toString();
//        ArrayData[3]=linedataVO.getCurrentRow().getAttribute("AgencyCommission").toString();
//        ArrayData[4]=linedataVO.getCurrentRow().getAttribute("Shipment").toString();
        
            String[] Array = {"Productivity/Hr", "Style Effi", "SAM", "Commison Prcnt", "Shipment"};
        for(int i=0;i<5;i++) {
            excelcell = excelrow.createCell(xlCol);
            excelcell.setCellStyle(headerStyle);
            System.out.println("string array data "+Array[i]);
            
            excelcell.setCellValue(Array[i]);
            worksheet.autoSizeColumn(xlCol);
            
            xlCol += 1;
            
        }
        
        excelrow = (HSSFRow)worksheet.getRow(xlRow+4);
            //String[] Arrayy = {"1", "2", "3", "4", "5"};
        for(int i=0;i<5;i++) {
            excelcell = excelrow.createCell(dataxlCol);
            excelcell.setCellStyle(dataStyle);
            System.out.println("string array data "+ArrayData[i]);
            excelcell.setCellValue(ArrayData[i]);
            worksheet.autoSizeColumn(dataxlCol);
            
            dataxlCol += 1;
            
        }
        this.xlRow++;
       
        
    }


    private void printDate(int xlRow) {
        excelrow = (HSSFRow)worksheet.getRow(xlRow);
        excelcell = excelrow.createCell(7);
        excelcell.setCellStyle(headerStyle);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date date = new Date();  
        
        excelcell.setCellValue("Reporting: "+formatter.format(date));
        worksheet.autoSizeColumn(0);  
        
                    
                 
                     
                  
    }
}
 