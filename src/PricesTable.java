import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


// Initiates price models and copy excel data into virtual price-model table
public class PricesTable {

    private RawMaterialsPriceModelList rmpml;

    private RawMaterialsPricesModel rmpm;
    private Object[] priceModelsList = new Object[1];

    private JFileChooser jfc;
//    private JFrame currentFrame;
    private String rawMaterialsTablePricesPath = "C:\\Users\\kuba2\\IdeaProjects\\LnCogsV3\\TestExcelFiles\\TestPrices.xls";
    private double euroRate, usdRate;

//    private String[] bottleChooserList;
//    private String[] capChooserList;
//    private String[] labelChooserList;
//    private String[] measurerChooserList;
//    private String[] unitBoxChooserList;
//    private String[] leafletChooserList;
//    private String[] collectiveBoxChooserList;


    public PricesTable() {
        this.rmpml = new RawMaterialsPriceModelList();
        this.jfc = new JFileChooser();
        this.euroRate = 4.3;
        this.usdRate = 3.8;
        try {
            getPricesList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("New path have been set "+rawMaterialsTablePricesPath);
    }

    //    Choose file with file chooser java set chosen file address and read data from file calling read func
    public void loadPricesTableDataSource( JFrame currentFrame) {
        jfc.setDialogTitle("Open file");
        int returnValue = jfc.showOpenDialog(currentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String extension = PricesTable.Utils.getExtension(selectedFile);
            if (extension != null) {
                if (extension.equals(PricesTable.Utils.xls)) {
                    rawMaterialsTablePricesPath = selectedFile.getAbsolutePath();
                    try {
                        getPricesList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("New path have been set "+rawMaterialsTablePricesPath);
                }
            }
        }
    }

//    Opens raw materials price list, reads it and creates price models based on number of rows in the price offer
    private void initiatePriceModels(){
        File inputWorkbook = new File(rawMaterialsTablePricesPath);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);

//            Read number of sheet, creates instance of RMPM for each sheet with number of rows in sheet
//            Each sheet by class is automaticcly added to RMPML
            for (int i=0;i<w.getNumberOfSheets();i++){
                new RawMaterialsPricesModel(new Integer[w.getSheet(i).getRows()], new String[w.getSheet(i).getRows()],new String[w.getSheet(i).getRows()],
                        new Double[w.getSheet(i).getRows()], new Double[w.getSheet(i).getRows()], new String[w.getSheet(i).getRows()], new String[w.getSheet(i).getRows()]);
            }

//            working solution - taking only prices for 1st sheet
            // Get the first sheet
//            Sheet sheet = w.getSheet(0);
//
//            this.rmpm = new RawMaterialsPricesModel(new Integer[sheet.getRows()], new String[sheet.getRows()],new String[sheet.getRows()],
//                    new Double[sheet.getRows()], new Double[sheet.getRows()], new String[sheet.getRows()], new String[sheet.getRows()]);
//
//            priceModelsList[0] = rmpm;

    } catch (Exception e){
            System.out.println("Couldn't load Models");}
    }



// Calls initiatePriceList(), reads price list and fill model with prices from table
    private void getPricesList() throws IOException {

            initiatePriceModels();

//            TODO: Remove double sheet
            File inputWorkbook = new File(rawMaterialsTablePricesPath);
            Workbook w;
            try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet

//                Sheet sheet = w.getSheet(0);

//                        TODO: FIX temporary solution of creating table
//                this.pricesTable = new Object[][]{
//                        counter, systemNumbers, rawMaterialsNames,
//                        minPrice, maxPrice, currency, supplier};

                for (int k=0; k<w.getNumberOfSheets();k++) {
                    for (int j = 0; j < 7; j++) {
                        for (int i = 0; i < w.getSheet(k).getRows(); i++) {
                            Cell cell = w.getSheet(k).getCell(j, i);
                            CellType type = cell.getType();
//  TODO: add verify data type
//                    model.getModelListData()[j][i] = cell.getContents();
                            if (type == CellType.LABEL) {
//                            priceModelsList[0].get [j][i] = cell.getContents();

                                rmpml.getRmpml().get(k).getPricesTable()[j][i] = cell.getContents();
//                                rmpm.getPricesTable()[j][i] = cell.getContents();

                                System.out.println("I got a label "
                                        + cell.getContents());
                            }

                            if (type == CellType.NUMBER) {
                                System.out.println("I got a number "
                                        + cell.getContents());
                                if (j == 3 | j == 4) {
//                                    rmpm.getPricesTable()[j][i] = Double.valueOf(cell.getContents().replace(",", "."));
                                    rmpml.getRmpml().get(k).getPricesTable()[j][i] = Double.valueOf(cell.getContents().replace(",", "."));
                                } else {
//                                    rmpm.getPricesTable()[j][i] = Integer.valueOf(cell.getContents());
                                    rmpml.getRmpml().get(k).getPricesTable()[j][i] = Integer.valueOf(cell.getContents());
                                }
                            }
                        }
                    }
                }
            } catch (BiffException e) {
                e.printStackTrace();
            }
    }

    //Files extension for file chooser
    private static class Utils {
        public final static String txt = "txt";
        public final static String jpg = "jpg";
        public final static String gif = "gif";
        public final static String tiff = "tiff";
        public final static String tif = "tif";
        public final static String png = "png";
        public final static String xls = "xls";


        /*
         * Get the extension of a file.
         */
        public static String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                ext = s.substring(i + 1).toLowerCase();
            }
            return ext;
        }
    }

    // ============== Getters n Setters ==============


    public String getRawMaterialsTablePricesPath() {
        return rawMaterialsTablePricesPath;
    }

    public void setRawMaterialsTablePricesPath(String rawMaterialsTablePricesPath) {
        this.rawMaterialsTablePricesPath = rawMaterialsTablePricesPath;
    }

    public double getEuroRate() {
        return euroRate;
    }

    public double getUsdRate() {
        return usdRate;
    }

    public RawMaterialsPricesModel getRmpm() {
        return rmpm;
    }
}
