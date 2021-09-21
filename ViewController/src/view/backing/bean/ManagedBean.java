package view.backing.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import java.sql.SQLException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.services.AppModuleImpl;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashSet;

import javax.faces.application.FacesMessage;

import javax.faces.application.ViewHandler;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.LaunchPopupEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Number;

import oracle.jdbc.OracleTypes;


public class ManagedBean {
    private RichSelectOneRadio selectedServiceItem;
    private RichPopup servicePopup;
    private RichTable itemTable;
    private RichInputListOfValues prefixName;
    private RichInputText actualUnitPrice;
    private RichInputText addChargLC;
    private RichInputText addChrgPrcnt;
    private RichInputText finalUnitPrice;
    private RichInputText consPerPcs;
    private RichInputText wasteInPercent;
    private RichInputText costPerPcs;
    private RichInputText finalCostPerPcs;
    private RichInputText uomConversion;
    private RichInputText addPriceFOB;
    private RichInputText addPriceMOQ;
    private RichInputText buffer;
    private RichInputText addDeductCost;
    private RichInputText wasteInQty;
    private RichInputText consWithWaste;
    private RichInputText finalCons;
    private RichInputText fabFinanceCost;
    private RichInputText fabricD;
    private RichInputText smsQty;
    private RichInputText orderQty;
    private RichInputText cmMerchad;
    private RichInputText producPerLine;
    private RichInputText styleEff;
    private RichInputText samValue;
    private RichInputText cost;
    private RichInputText prodCostPerLine;
    private RichInputText finance;
    private RichInputText profit;
    private RichInputText cm;
    private RichSelectOneChoice productionUnit;
    private RichInputText fabricFinancePrice;
    private RichInputText trimsFinance;
    private RichTable subCostingTable;
    private RichInputText washFinanceValue;
    private RichInputText cmMerchandiser;
    static DecimalFormat format = new DecimalFormat("#");
    private RichInputText dryCost;
    private RichInputText dryProfit;
    private RichInputText dryTotal;
    private RichInputText wetCost;
    private RichInputText wetProfit;
    private RichInputText wetTotal;
    private RichSelectOneRadio selectRadio;
    private RichInputText pocId;
    private RichTable dryDetailsTable;
    private RichTable wetDetailsTable;
    private RichTable queryTable;
    private RichSelectOneChoice organizationId;
    private RichInputListOfValues buyerId;
    private RichSelectOneChoice seasonId;
    private RichInputText totalCostCal;
    private RichInputText costSan;
    private RichSelectOneRadio selectedShipItem;
    private RichInputText cost_per_pcs_trims;
    private RichInputText consPerPcs_trim;
    private RichInputText actualUnitPrice_trim;
    private RichInputText buffer_trims;
    private RichInputText other_bufer;
    private RichInputText otherActualPrc;
    private RichInputText oteherConPcs;
    private RichInputText othersCosPerPc;
    private RichTable other_table;

    public ManagedBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
        AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
    AppModuleImpl appM = (AppModuleImpl)this.getAppModuleImpl();

    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam =
            getBindingsCont().getOperationBinding(operation);
        return createParam;

    }

    /*****Generic Method to Get BindingContainer**/
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

  

    public String refershButton() {
        // Add event code here...
        //   ViewObject vo=appM.getXX_OM_POC_H_TVO1();
        //   System.out.println("=========Commkit ===========");
        //   appM.getDBTransaction().commit();
       
        ViewObject linenumber = appM.getXX_OM_POC_L_TVO1();
    
    
        ViewObject testlineVo = appM.getXX_OM_POC_H_TVO1();
        remark();
        trimStyleNoAndName();
        //OperationBinding ob = executeOperation("Commit");
       // ob.execute();
        appM.getDBTransaction().commit();
        productionCalculation();
      
        try {
            linenumber.getCurrentRow().getAttribute("FobId").toString();
            System.out.println("..........befor");
            refreshValues();
            setfabricDescription();
            System.out.println("..........after");
            RefreshWashCost();
            System.out.println("..........after Refresh ---");
            AdfFacesContext.getCurrentInstance().addPartialTarget(subCostingTable);

        } catch (Exception e) {
            ;
        }


        try {

            double fob =
                Double.parseDouble(testlineVo.getCurrentRow().getAttribute("FobWithComm").toString());
            //double CM= Double.parseDouble( testlineVo.getCurrentRow().getAttribute("Cm").toString());
            // adjustment();
        } catch (Exception e) {
            ;
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(subCostingTable);
        // Code For Style Tracking
        // OperationBinding operationBinding = executeOperation("StyleTracking");
        //  operationBinding.execute();
        // End Code For Style Tracking
        TotalCostCal();
        //OperationBinding ob1 = executeOperation("Commit");
      //  ob1.execute();
    appM.getDBTransaction().commit();
        ViewObject lineVo = appM.getXX_OM_POC_L_TVO1();
        ViewObject vo = appM.getXX_OM_POC_D1_TVO1();
        ViewObject vo1 = appM.gettrimsVO1();
        ViewObject vo3 = appM.getothers_VO1();
       // ViewObject vo4 =appM.getXX_OM_POC_L_TVO1();
        ViewObject v5 = appM.getXX_OM_POC_D2_TVO1();
        ViewObject v6 = appM.getXX_OM_POC_D2_TVO2_1();
        
         refreshQueryKeepingCurrentRow(lineVo); 
       refreshQueryKeepingCurrentRow(vo);
        refreshQueryKeepingCurrentRow(vo1);
        refreshQueryKeepingCurrentRow(vo3);
        refreshQueryKeepingCurrentRow(v5);
        refreshQueryKeepingCurrentRow(v6 );
     
        vo.clearCache();
        vo1.clearCache();
        vo3.clearCache();
       // vo4.clearCache();
        v5.clearCache();
        v6.clearCache();

        return null;
    }


    private void trimStyleNoAndName() {

        String styleName = null, styleNo = null;

        ViewObject headerVo = appM.getXX_OM_POC_H_TVO1();
        try {
            styleNo =
                    headerVo.getCurrentRow().getAttribute("StyleNo").toString().trim();

            System.out.println("999999999999999999=================== styleNo ===================" +
                               styleNo);

            headerVo.getCurrentRow().setAttribute("StyleNo", styleNo);
        } catch (Exception e) {
            ;
        }

        try {
            styleName =
                    headerVo.getCurrentRow().getAttribute("StyleNameNew").toString().trim();
            headerVo.getCurrentRow().setAttribute("StyleNameNew", styleName);
        } catch (Exception e) {
            ;
        }

    }


    public void productionCalculation() {
        // Add event code here...
        String unit = null;

        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("AppModuleDataControl");
        ApplicationModule appM = dc.getApplicationModule();
        ViewObject findViewObject = appM.findViewObject("XX_OM_POC_H_TVO1");

        try {
            unit =
findViewObject.getCurrentRow().getAttribute("LcUnit").toString();
            System.out.println("=========== unit ==============" + unit);

        } catch (Exception e) {
            ;
        }
        System.out.println("Unit----->>" + unit);

        // System.out.println("Unit123----->>");

        //System.out.println("Unit----->>"+unit);

        try {
            // producPerLine.setValue(MyMath.toNumber(MyMath.round(getProdPerLineFromProc(unit,getProjectionNo().getValue()))));
            //  prodCostPerLine.setValue(MyMath.toNumber(getProdCostUnitWise(unit)));
            // AdfFacesContext.getCurrentInstance().addPartialTarget(producPerLine);
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(prodCostPerLine);
        } catch (Exception e) {
            ;
        }
        // refreshValues();
    }

    public void RefreshWashCost() {
        System.out.println("===============RefreshWashCost===============");
        ViewObject v1 = appM.getXX_OM_POC_D2_TVO1();
        v1.executeQuery();
        ViewObject v2 = appM.getXX_OM_POC_D2_TVO2_1();
        v2.executeQuery();
        System.out.println("==============Last=RefreshWashCost===============");
        refreshValues();
    }

    private void setfabricDescription() {
        System.out.println("febric enter");
//        OperationBinding ob = executeOperation("Commit");
//        ob.execute();
        appM.getDBTransaction().commit();
        ViewObject FabricVo = appM.getXX_OM_POC_D1_TVO1();
        FabricVo.executeQuery();
        ViewObject linevo = appM.getXX_OM_POC_L_TVO1();
        try {
            String feb2 = null;
            String feb3 = null;
            String feb4 = null;
            // RowSet child = (RowSet)linevo.getCurrentRow().getAttribute("FebricView");
            Row[] r = FabricVo.getAllRowsInRange();
            System.out.println("febric clid row---------------->" + r.length);

            StringBuilder setVal2 = null;
            setVal2 = new StringBuilder();
            StringBuilder setVal3 = null;
            setVal3 = new StringBuilder();
            StringBuilder setVal4 = null;
            setVal4 = new StringBuilder();
            int count = 0, check = 0;
            // for (Row row : r){
            // oracle.jbo.domain.Number prefix = (Number)row.getAttribute("ItemPrefix");
            // if ((prefix.intValue() >= 11 && prefix.intValue() <= 12)){
            // count++;
            // }
            // }

            for (Row row : r) {

                System.out.println("--------Start Number prefix ----->");

                // oracle.jbo.domain.Number prefix = (Number)row.getAttribute("ItemPrefix");

                String pre =
                    row.getAttribute("ItemPrefix").toString(); //ItemPrefix

                int prefix = Integer.parseInt(pre);


                System.out.println("-------- Number prefix ----->" + prefix);

                if ((prefix >= 11 && prefix <= 12)) {
                    check++;
                    if (check == 1) {
                        try {
                            feb2 = row.getAttribute("RefCode").toString();
                        } catch (Exception e) {
                            feb2 = null;
                        }
                    }

                    if (check == 1) {
                        try {
                            feb3 = row.getAttribute("ItemDesc").toString();
                        } catch (Exception e) {
                            feb3 = null;
                        }
                    }


                    // setVal2.append(String.format(feb2));
                    // setVal3.append(String.format(feb3));
                    // if(count!=check) {
                    // setVal2.append(String.format("/"));
                    // // setVal3.append(String.format("/"));
                    // }

                }

            }
            setVal2.append(String.format(feb2));
            setVal4.append(setVal2.toString());
            setVal4.append(String.format(" & "));
            setVal4.append(feb3);


            System.out.println("......................feb cont dec string= " +
                               setVal2.toString());
            System.out.println("......................feb cont dec string= " +
                               setVal4.toString());
            linevo.getCurrentRow().setAttribute("FabricDesc",
                                                setVal2.toString());
            linevo.getCurrentRow().setAttribute("FabricContent",
                                                setVal4.toString());
            // AdfFacesContext.getCurrentInstance().addPartialTarget(fabDescrip);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(fabricC); /* Sanoar */
            AdfFacesContext.getCurrentInstance().addPartialTarget(fabricD);

        } catch (Exception e) {
            ;
        }


        // String TBA=String.valueOf(TB);

    }


    private void remark() {
        appM.validateLines();
        String ab = "check remark";

        System.out.println(" *****************check remark ************************");

        // if(re=="Same") {
        // String message ="Check remark";
        // FacesMessage fm = new FacesMessage(message);
        // fm.setSeverity(FacesMessage.SEVERITY_INFO);
        // FacesContext context = FacesContext.getCurrentInstance();
        // context.addMessage(null, fm);
        // checkremark();
        //
        // }

    }


    public void autoPopup(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        try {
            String val = null;
            try {
                val = valueChangeEvent.getNewValue().toString();

            } catch (Exception e) {
                ;
            }

            if (val.equalsIgnoreCase("Service Item")) {
                System.out.println("calling custom popup here....");
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                getServicePopup().show(hints);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedServiceItem(RichSelectOneRadio selectedServiceItem) {
        this.selectedServiceItem = selectedServiceItem;
    }

    public RichSelectOneRadio getSelectedServiceItem() {
        return selectedServiceItem;
    }


    //    public double getProdCostUnitWise(String unit) {
    //
    //    OperationBinding operationBinding =
    //    executeOperation("getProdCostUnitWise");
    //    operationBinding.getParamsMap().put("LcUnit", unit);
    //    operationBinding.execute();
    //
    //    Object methodReturnValue = operationBinding.getResult();
    //    double value = Double.parseDouble(methodReturnValue.toString());
    //    return value;
    //
    //    }

    public void serviceItemDilogListener(DialogEvent dialogEvent) {
        // Add event code here...
        ViewObject othersView = appM.getothers_VO1();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            String test = getSelectedServiceItem().getValue().toString();
            // callCopy(getSelectRadio().getValue().toString());
            othersView.getCurrentRow().setAttribute("RefCode", test);
            AdfFacesContext.getCurrentInstance().addPartialTarget(other_table);
            System.out.println("............test................selected item " +
                               test);
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();

        }
    }

    public void setServicePopup(RichPopup servicePopup) {
        this.servicePopup = servicePopup;
    }

    public RichPopup getServicePopup() {
        return servicePopup;
    }

    public void setItemTable(RichTable itemTable) {
        this.itemTable = itemTable;
    }

    public RichTable getItemTable() {
        return itemTable;
    }

    public void setPrefixName(RichInputListOfValues prefixName) {
        this.prefixName = prefixName;
    }

    public RichInputListOfValues getPrefixName() {
        return prefixName;
    }

    public void setMerchValues() {

        // double merchPc = MyMath.numeric(getCmMerchandiser());
        // double result = 0.0;
        // result = merchPc * 12;

        System.out.println("==================setMerchValues========== ");

        // cmMerchDzn.setValue(MyMath.toNumber(result));
        // AdfFacesContext.getCurrentInstance().addPartialTarget(cmMerchDzn);

    }

    /***medthod added by arif to get total value of  the trims***/

    public double findtrims() {
        System.out.println("im in arifs trims method");
        double fabric = 0.00, trims = 0.00, finalCostPerPcsVal =
            0.00, service = 0.0;
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings =
            bindings.findIteratorBinding("trimsVO1Iterator");
        // Get all the rows of a iterator

        System.out.println("==============setFinanceCalc=======3========");

        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        for (Row row : rows) {


            String pre =
                row.getAttribute("ItemPrefix").toString(); //ItemPrefix

            int prefix = Integer.parseInt(pre);

            System.out.println("==============Number prefix===============" +
                               prefix);

            try {
                finalCostPerPcsVal =
                        Double.parseDouble(row.getAttribute("CostPerPcs").toString());//changed the field name before it was finalcostperpec
                // Double.parseDouble(row.getAttribute("FinalCostPerPcs").toString());
                System.out.println("==============Number prefix===============" +
                                   finalCostPerPcsVal);
            } catch (Exception e) {
                ;
            }

            // prefixVal = Integer.parseInt(prefix);
            if (prefix >= 11 && prefix <= 12) {
                fabric = fabric + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } else if ((prefix > 12 && prefix <= 36) || prefix == 00) {
                trims = trims + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } else if (prefix == 55) {
                service = service + finalCostPerPcsVal;
            }

        } //end of for each loop


        return trims;


    } //end of method by arif for trims


    /***method to get total value of services by arif****/

    public double findservices() {
        double fabric = 0.00, trims = 0.00, finalCostPerPcsVal =
            0.00, services = 0.0;
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings =
            bindings.findIteratorBinding("others_VO1Iterator");
        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        for (Row row : rows) {


            String pre =
                row.getAttribute("ItemPrefix").toString(); //ItemPrefix

            int prefix = Integer.parseInt(pre);

            System.out.println("==============Number prefix===============" +
                               prefix);

            try {
                finalCostPerPcsVal =
                        Double.parseDouble(row.getAttribute("CostPerPcs").toString());
                // Double.parseDouble(row.getAttribute("FinalCostPerPcs").toString());
                System.out.println("==============Number prefix===============" +
                                   finalCostPerPcsVal);
            } catch (Exception e) {
                ;
            }

            // prefixVal = Integer.parseInt(prefix);
            if (prefix >= 11 && prefix <= 12) {
                fabric = fabric + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } else if ((prefix > 12 && prefix <= 36) || prefix == 00) {
                trims = trims + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } else if (prefix == 55) {
                services = services + finalCostPerPcsVal;
            }

        }


        return services;


    }


    public void setFinanceCalc(int marchand) {
        int check = marchand;

        System.out.println("==============setFinanceCalc=======1========");
        // try {

        double fabric = 0.00, fobVal = 0.00, trim = 0.00, finalCostPerPcsVal =
            0.00, washTotalVal = 0.00, financialCostValue = 0.00, totalCMVal =
            0.00, refelectedVal = 0, service = 0.0, wash = 0.0;

        System.out.println("==============setFinanceCalc=======2========");

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings =
            bindings.findIteratorBinding("XX_OM_POC_D1_TVO1Iterator");
        // Get all the rows of a iterator

        System.out.println("==============setFinanceCalc=======3========");

        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        for (Row row : rows) {

            /***************************************/
            // refreshFabricFinance(row);
            /***************************************/

            //  oracle.jbo.domain.Number prefix = (Number)row.getAttribute("ItemPrefix"); //ItemPrefix

            String pre =
                row.getAttribute("ItemPrefix").toString(); //ItemPrefix

            int prefix = Integer.parseInt(pre);

            System.out.println("==============Number prefix===============" +
                               prefix);

            try {
                finalCostPerPcsVal =
                        Double.parseDouble(row.getAttribute("CostPerPcs").toString());
                // Double.parseDouble(row.getAttribute("FinalCostPerPcs").toString());
                System.out.println("==============Number prefix===============" +
                                   finalCostPerPcsVal);
            } catch (Exception e) {
                ;
            }

            // prefixVal = Integer.parseInt(prefix);
            if (prefix >= 11 && prefix <= 12) {
                fabric = fabric + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } /*else if ((prefix > 12 && prefix <= 36) || prefix == 00) {
                trim = trim + finalCostPerPcsVal;
                finalCostPerPcsVal = 0.00;
            } else if (prefix == 55) {
                service = service + finalCostPerPcsVal;
            }*/

        } //end of for each loop

        // double fabricFinanceVal = MyMath.numeric3(getFabricFinance());
        // double washFinanceVal = MyMath.numeric3(getWashFinance());
        // double trimFinanceVal = MyMath.numeric3(getTrimFinance());
        trim = findtrims();
        service = findservices();
        washTotalVal = getSelectedDryTotal() + getSelectedWetTotal();

        System.out.println("...................getSelectedDryTotal() + getSelectedWetTotal()---------" +
                           washTotalVal);
        // financialCostValue =
        // ((fabric 0.06) - (fabric (fabricFinanceVal / 100))) +
        // ((trim 0.06) - (trim (trimFinanceVal / 100))) +
        // ((washTotalVal * 0.01) -
        // (washTotalVal * (washFinanceVal / 100)));
        /****************************************/
        // financialCostValue =
        // ((fabric getPOCFinanceCost("FABRIC")) - (fabric (fabricFinanceVal / 100))) +
        // ((trim getPOCFinanceCost("TRIMS")) - (trim (trimFinanceVal / 100))) +
        // ((washTotalVal * getPOCFinanceCost("WASH")) -
        // (washTotalVal * (washFinanceVal / 100)));


        double fabricPrice = 0.00;
        double trimsPrice = 0.00;
        double washPrice = 0.00;
        fabricPrice = febricValue();
        trimsPrice = trimValue();
        washPrice = washValue();

        System.out.println("...................fabricPrice=" + fabricPrice);

        double fabricPriceByMrch = 0.00;
        try {
            fabricPriceByMrch =
                    Double.parseDouble((getFabricFinancePrice().getValue().toString()));
        } catch (Exception e) {
            fabricPriceByMrch = 0.00;
        }

        double trimsPriceByMrch = 0.00;
        try {
            trimsPriceByMrch =
                    Double.parseDouble((getTrimsFinance().getValue().toString()));
        } catch (Exception e) {
            trimsPriceByMrch = 0.00;
        }
        double washPriceByMrch = 0.00;
        try {
            washPriceByMrch =
                    Double.parseDouble((getWashFinanceValue().getValue().toString()));
        } catch (Exception e) {
            washPriceByMrch = 0.00;
        }


        System.out.println("...................fabricPrice-fabricPriceByMrch=" +
                           fabricPriceByMrch);
        fabricPrice = febricValue() - fabricPriceByMrch;
        //fabric=fabric+((fabric*fabricPriceByMrch)/100);

        trimsPrice = trimValue() - trimsPriceByMrch;
        // trim=trim+((trim*trimsPriceByMrch)/100);
        washPrice = washValue() - washPriceByMrch;
        financialCostValue =
                ((fabric * fabricPrice) / 100) + ((trim * trimsPrice) / 100) +
                ((washTotalVal * washPrice) / 100);


        // totalCMVal = financialCostValue + MyMath.numeric(getCost()) + MyMath.numeric(getProfit());
        // double totalcost=0.0;
        ViewObject line = appM.getXX_OM_POC_L_TVO1();
        // totalcost=financialCostValue + MyMath.numeric(getCost()) ;
        // line.getCurrentRow().setAttribute("TotalCost",totalcost);


        finance.setValue(MyMath.toNumber(MyMath.round(financialCostValue)));
        AdfFacesContext.getCurrentInstance().addPartialTarget(finance);


        refelectedVal =
                MyMath.numeric(getCmMerchandiser()) - MyMath.numeric(getCost()) -
                financialCostValue;


        System.out.println(fabric +
                           "------------------------FOBS value print ------------>" +
                           trim);
        double otherAdjustMent = 0.0;
        try {
            otherAdjustMent =
                    Double.parseDouble(line.getCurrentRow().getAttribute("OtherCharge").toString());
        } catch (Exception e) {
            otherAdjustMent = 0.0;
        }
        service = service + otherAdjustMent;

        fabric = fabric + ((fabric * fabricPriceByMrch) / 100);
        trim = trim + ((trim * trimsPriceByMrch) / 100);
        washTotalVal = washTotalVal + ((washTotalVal * washPriceByMrch) / 100);


        System.out.println("check value fabric=" + fabric);

        System.out.println("check value trim=" + trim);

        System.out.println("check value wash=" + washTotalVal);

        setFOBValues(trim, fabric, washTotalVal, service, financialCostValue,
                     check);

        System.out.println("check done");
        // } catch (Exception e) {
        // ;
        // }

    }


    public double getSelectedDryTotal() {

        // System.out.println("---------- step-1 -------  getSelectedDryTotal() -------------------");

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings =
            bindings.findIteratorBinding("XX_OM_POC_D2_TVO1Iterator");
        // Get all the rows of a iterator

        //   System.out.println("---------- step-2 -------  getSelectedDryTotal() -------------------");
        double total = 0.00, val = 0.00, val2 = 0.0;

        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        //  System.out.println("=============Dry rows.length================"+rows.length);
        for (Row row : rows) {
            try { //ManualPrice
                val =
Double.parseDouble(String.valueOf(row.getAttribute("Total")));
                val2 =
Double.parseDouble(String.valueOf(row.getAttribute("ManualPrice")));
            } catch (Exception e) {
                val2 = 0;
                e.printStackTrace();
            }
            if (val > 0)
                total += val;
            else
                total += val2;

        } //end of for each loop

        //   System.out.println("---------- step-4-- Dry -------  Total -------------------"+total);

        return total;

    }

    public double getSelectedWetTotal() {

        System.out.println("---------- step-1 -------  getSelectedWetTotal() -------------------");

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings =
            bindings.findIteratorBinding("XX_OM_POC_D2_TVO2_1Iterator");
        // Get all the rows of a iterator
        System.out.println("---------- step-2 -------  getSelectedWetTotal() -------------------");
        double total = 0.00, val = 0.00, val2 = 0.0;
        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        //     System.out.println("=============Wet rows.length================"+rows.length);
        for (Row row : rows) {

            try { //ManualPrice

                val =
Double.parseDouble(String.valueOf(row.getAttribute("Total")));
                val2 =
Double.parseDouble(String.valueOf(row.getAttribute("ManualPrice")));

            } catch (Exception e) {
                val2 = 0;
                e.printStackTrace();
            }
            if (val > 0)
                total += val;
            else
                total += val2;

        } //end of for each loop
        System.out.println("---------- step-4 -------Wet  Total -------------------" +
                           total);
        return total;

    }


    private double febricValue() {
        String name = null;
        String itemType = null;
        ViewObject hvo = appM.getXX_OM_POC_H_TVO1();
        try {
            itemType = hvo.getCurrentRow().getAttribute("ItemPurchaseType").toString();
        } catch (Exception e) {
            itemType = "Fabric";
            ;
        }
        if (itemType.equalsIgnoreCase("Imported")) {
            name = "Fabric-Foreign";
        } else if (itemType.equalsIgnoreCase("Local")) {
            name = "Fabric-Local";
        } else if (itemType.equalsIgnoreCase("Fabric")) {
            name = "Fabric";
        } else {
            name = "Fabric";
        }

        System.out.println("febricValue - ItemPurchaseType " + itemType);
        System.out.println("febricValue - Item " + name);
        
        double value = 0;

        String date = null;
        String p_buyer = null;
        try {
            date = hvo.getCurrentRow().getAttribute("CreationDate").toString();
            p_buyer = hvo.getCurrentRow().getAttribute("BuyerId").toString();
        } catch (Exception e) {
            ;
        }
        
        System.out.println("febricValue - CreationDate " + date);
        System.out.println("febricValue - BuyerId " + p_buyer);
        
        String stmt = "BEGIN :1 := mnj_get_costing_finance_price(:2,to_date(:3,'YYYY-MM-DD'),:4); end;";
        java.sql.CallableStatement cs = appM.getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setString(2, name);
            cs.setString(3, date);
            cs.setString(4, p_buyer);
            cs.execute();
            value = cs.getDouble(1);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("febricValue - fabric percent " + value);
        System.out.println("-------------------------------------");
        return value;

        //end of if condition
    }

    private double trimValue() {
        String name = "Trims";
        double value = 0;
        ViewObject hvo = appM.getXX_OM_POC_H_TVO1();

        String date = null;
        String p_buyer = null;
        try {
            date = hvo.getCurrentRow().getAttribute("CreationDate").toString();
            p_buyer = hvo.getCurrentRow().getAttribute("BuyerId").toString();
        } catch (Exception e) {
            ;
        }
        
        System.out.println("trimValue - (get)name " + name);
        System.out.println("trimValue - (get)CreationDate " + date);
        System.out.println("trimValue - (get)BuyerId " + p_buyer);
        
        String stmt = "BEGIN :1 := mnj_get_costing_finance_price(:2,to_date(:3,'YYYY-MM-DD'),:4); end;";
        java.sql.CallableStatement cs = appM.getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setString(2, name);
            cs.setString(3, date);
            cs.setString(4, p_buyer);
            cs.execute();
            value = cs.getDouble(1);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("trimValue - trim percent " + value);
        System.out.println("-------------------------------------");
        return value;
    }

    private double washValue() {
        String name = "Wash";
        double value = 0;
        ViewObject hvo = appM.getXX_OM_POC_H_TVO1();

        String date = null;
        String p_buyer = null;
        try {
            date = hvo.getCurrentRow().getAttribute("CreationDate").toString();
            p_buyer = hvo.getCurrentRow().getAttribute("BuyerId").toString();
        } catch (Exception e) {
            ;
        }

        System.out.println("washValue - name " + name);
        System.out.println("washValue - (get)CreationDate " + date);
        System.out.println("washValue - (get)BuyerId "      + p_buyer);

        String stmt = "BEGIN :1 := mnj_get_costing_finance_price(:2,to_date(:3,'YYYY-MM-DD'),:4); end;";
        java.sql.CallableStatement cs = appM.getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setString(2, name);
            cs.setString(3, date);
            cs.setString(4, p_buyer);
            cs.execute();
            value = cs.getDouble(1);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("washValue - wash percent " + value);
        System.out.println("-------------------------------------");
        return value;
    }

    public void setFOBValues(double trim, double fabric, double washTotalVal,
                             double service, double financialCostValue,
                             int check) {


        format.setMinimumFractionDigits(2);


        ViewObject MnjLineV = appM.getXX_OM_POC_L_TVO1();
        ViewObject headerVo = appM.getXX_OM_POC_H_TVO1();
        double otherVal = service;
        /***********************changed by MT*****************************************/
        double samValue = 0.00, smvValue = 0.00, factoryAvrgEff =
            0.00, StyleEff = 0.00, newCost = 0.00;

        try {
            StyleEff =
                    Double.parseDouble((getStyleEff().getValue().toString()));
        } catch (Exception e) {
            StyleEff = 0.00;
        }

        try {
            samValue =
                    Double.parseDouble((getSamValue().getValue().toString()));
        } catch (Exception e) {
            samValue = 0.00;
        }
        factoryAvrgEff = FactoryAvgEff();
        smvValue = SMV();
        System.out.println("==============factoryAvrgEff===================" +
                           factoryAvrgEff);
        System.out.println("==============smvValue===================" +
                           smvValue);
        MnjLineV.getCurrentRow().setAttribute("FactoryAvgEf", factoryAvrgEff);
        MnjLineV.getCurrentRow().setAttribute("Smv", smvValue);
        if (StyleEff != 0) {
            newCost = (((samValue * smvValue) / StyleEff) * factoryAvrgEff);
        }

        newCost = MyMath.round(newCost);
        MnjLineV.getCurrentRow().setAttribute("CostNew", newCost);
        double totalcost = 0.0;

        totalcost = financialCostValue + newCost;

        newCost = MyMath.round(newCost);
        MnjLineV.getCurrentRow().setAttribute("CostNew", newCost);
        double CM = 0.00;
        try {
            CM = Double.parseDouble((getCmMerchad().getValue().toString()));
        } catch (Exception e) {
            CM = 0.00;
        }


        double calProfit = 0.00;
        double setProfit = 0.00;
        double cmValue = 0.00;
        calProfit = MyMath.round(CM - totalcost);
        cmValue = totalcost + calProfit;

        double setCm = 0.00;
        double setManagementCm = 0.00;
        if (check == 1) {
            int set = 1;
            headerVo.getCurrentRow().setAttribute("CmCalculation", set);
            setCm = cmValue;

        }
        try {
            headerVo.getCurrentRow().getAttribute("CmCalculation").toString();
            double prof = 0.00;
            try {
                prof =
Double.parseDouble(MnjLineV.getCurrentRow().getAttribute("Profit").toString());
            } catch (Exception e) {
                prof = calProfit;
            }
            setCm = totalcost + prof;
            setProfit = prof;
            setManagementCm = setCm;
        } catch (Exception e) {
            setCm = CM;
            setProfit = calProfit;
            setManagementCm = cmValue;
        }


        MnjLineV.getCurrentRow().setAttribute("Profit", setProfit);
        AdfFacesContext.getCurrentInstance().addPartialTarget(profit);


        MnjLineV.getCurrentRow().setAttribute("Cm", MyMath.round(setCm));

        MnjLineV.getCurrentRow().setAttribute("CmManagement",
                                              MyMath.round(setManagementCm));


        /***********************changed by MT*****************************************/


        double fob = 0.0;

        // if (MyMath.numeric(getProfit()) > 0 ){
        //
        // fob = trim + fabric + washTotalVal + totalCMVal + otherVal;
        // }
        // else {
        // fob = trim + fabric + washTotalVal + MyMath.numeric(getCmMerchad()) + otherVal;
        // }

        fob = trim + fabric + washTotalVal + setCm + otherVal;

        /*************************************************************************************************/

        double commVal = 0.0; // MyMath.numeric3(getCommission());
        try {
            commVal =
                    Double.parseDouble(MnjLineV.getCurrentRow().getAttribute("CommisonPrcnt").toString());
        } catch (Exception e) {
            commVal = 0.0;
        }
        double fobWitcommVal = (fob * commVal / 100) + fob;
        double orderQtyVal = MyMath.numeric(getOrderQty());
        double smsQtyVal = MyMath.numeric3(getSMSQtyFromTable());
        //    System.out.println("=====================smsQtyVal===================->"+smsQtyVal);
        //    System.out.println("=====================smsQtyVal===================->"+smsQtyVal);
        double fobWithSmsVal = 0.00;
        //  double targetPrice = MyMath.numeric(getTargetPrice());
        double targetPrice = 0.00;


        try {
            if (orderQtyVal > 0)
                fobWithSmsVal =
                        ((fobWitcommVal) * orderQtyVal + (fobWitcommVal) *
                         smsQtyVal) / orderQtyVal;
            else
                fobWithSmsVal = 0;
        } catch (Exception e) {

            fobWithSmsVal = 0.0;
            System.out.println("Exception value --===================->" +
                               fobWithSmsVal);
        }


        oracle.adf.view.rich.component.UIXTable table = getSubCostingTable();
        java.util.Iterator selectionIt = table.getSelectedRowKeys().iterator();


        while (selectionIt.hasNext()) {
            Object rowKey = selectionIt.next();
            table.setRowKey(rowKey);
            int index = table.getRowIndex();
            FacesCtrlHierNodeBinding row =
                (FacesCtrlHierNodeBinding)table.getRowData(index);
            Row selectedRow = row.getRow();
            selectedRow.setAttribute("FobWitoutComm",
                                     Double.parseDouble(format.format(fob)));


            //       System.out.println("=====================fobWithSmsVal=============1======->"+fobWithSmsVal);
            //       System.out.println("=====================fobWitcommVal=============2======->"+fobWitcommVal);


            if (fobWithSmsVal > fobWitcommVal) {
                try {

                    // selectedRow.setAttribute("FobWithComm", MyMath.toNumber(MyMath.round(fobWithSmsVal)));
                    selectedRow.setAttribute("FobWithComm",
                                             Double.parseDouble(format.format(fobWithSmsVal))); //testCase1

                    //            selectedRow.setAttribute("Difference",
                    //            MyMath.toNumber(Double.parseDouble(format.format(fobWithSmsVal))- targetPrice));

                } catch (Exception e) {
                    ;
                }

            } else {

                //selectedRow.setAttribute("FobWithComm", MyMath.toNumber(MyMath.round(fobWitcommVal)));
                selectedRow.setAttribute("FobWithComm",
                                         Double.parseDouble(format.format(fobWithSmsVal))); //testCase1

                //    selectedRow.setAttribute("Difference",
                //    MyMath.toNumber(Double.parseDouble(format.format(fobWitcommVal))- targetPrice));

            }

            //        System.out.println("=====================fobWithSmsVal===================->"+fobWithSmsVal);
            //        System.out.println("=====================fobWitcommVal===================->"+fobWitcommVal);

            try {
                selectedRow.setAttribute("Fob",
                                         Double.parseDouble(format.format(fobWitcommVal)));
                selectedRow.setAttribute("FobWithSmsSample",
                                         Double.parseDouble(format.format(fobWithSmsVal)));
                selectedRow.setAttribute("FabricCost",
                                         MyMath.toNumber(MyMath.roundTo3(fabric)));
                selectedRow.setAttribute("TrimCost",
                                         MyMath.toNumber(MyMath.roundTo3(trim)));
                selectedRow.setAttribute("WashCost",
                                         MyMath.toNumber(MyMath.roundTo3(washTotalVal)));
                selectedRow.setAttribute("Others", MyMath.roundTo3(otherVal));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // if (MyMath.numeric(getProfit()) > 0 ){
            // selectedRow.setAttribute("Cm",
            // MyMath.toNumber(MyMath.round(totalCMVal)));
            // selectedRow.setAttribute("CmManagement",MyMath.round(totalCMVal));
            // selectedRow.setAttribute("CmMerchand",MyMath.round(totalCMVal));
            //
            // }
            // else {
            // double value=0.0;
            // selectedRow.setAttribute("Cm", MyMath.toNumber(MyMath.numeric(getCmMerchad())));
            // selectedRow.setAttribute("CmManagement",value);
            //
            // }
        } //end of while loop
        System.out.println("final value for line table are *************************************************" +
                           "fabric" + fabric + "trims:" + trim + "other:" +
                           otherVal + "wash:" + washTotalVal);

    }

    public Object getSMSQtyFromTable() {

        oracle.adf.view.rich.component.UIXTable table = getSubCostingTable();
        // Get the Selected Row key set iterator

        //((FOB*Comission(%))*Order Qty+(FOB*Comission(%))*Sample Qty)/Order Qty
        java.util.Iterator selectionIt = table.getSelectedRowKeys().iterator();
        Object qty = null;
        while (selectionIt.hasNext()) {
            Object rowKey = selectionIt.next();
            table.setRowKey(rowKey);
            int index = table.getRowIndex();
            FacesCtrlHierNodeBinding row =
                (FacesCtrlHierNodeBinding)table.getRowData(index);
            Row selectedRow = row.getRow();

            qty = selectedRow.getAttribute("SmsQty");

        }
        System.out.println("SMS Qty-->" + qty);
        return qty;
    }

    public double FactoryAvgEff() {
        String unitId = null;
        ViewObject hvo = appM.getXX_OM_POC_H_TVO1();
        try {
            unitId = hvo.getCurrentRow().getAttribute("LcUnit").toString();
        } catch (Exception e) {
            ;
        }
        String date = null;
        try {
            date = hvo.getCurrentRow().getAttribute("CreationDate").toString();
        } catch (Exception e) {
            ;
        }
        //end of if condition
        System.out.println("-------unitId ------ unitId ----------------------->" +
                           unitId);
        System.out.println("-------FactoryAvgEff ------ creation date is ----------------------->" +
                           date);
        double srno = 0;
        String stmt = "BEGIN :1 :=poc_get_Factory_avg_eff(:2,:3); end;";
        java.sql.CallableStatement cs =
            appM.getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setString(2, unitId);
            cs.setString(3, date);
            cs.execute();
            srno = cs.getDouble(1);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srno;
    }

    public double SMV() {
        String unitId = null;
        ViewObject hvo = appM.getXX_OM_POC_H_TVO1();
        try {
            unitId = hvo.getCurrentRow().getAttribute("LcUnit").toString();
        } catch (Exception e) {
            ;
        }
        //end of if condition
        String date = null;
        try {
            date = hvo.getCurrentRow().getAttribute("CreationDate").toString();
        } catch (Exception e) {
            ;
        }
        //end of if condition

        System.out.println(" creation date is ----------------------->" +
                           date);


        double srno = 0;
        String stmt = "BEGIN :1 := poc_get_preccost_smv(:2,:3); end;";
        java.sql.CallableStatement cs =
            appM.getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setString(2, unitId);
            cs.setString(3, date);
            cs.execute();
            srno = cs.getDouble(1);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srno;
    }

    public void refreshValues() {

        //setOthersValues();
        setMerchValues();

        System.out.println("==================refreshValues==========1== ");

        double actualUnitPriceVal = MyMath.numeric(getActualUnitPrice());

        double addPriceMoqVal = MyMath.numeric(getAddPriceMOQ());

        double addChrgLCVal = MyMath.numeric(getAddChargLC());
        double addChrgPrcntVal = MyMath.numeric(getAddChrgPrcnt());
        double addPriceFOBVal = MyMath.numeric(getAddPriceFOB());
        double lcCalculated = 0.00;
        double actualUnitPriceCalc = 0.00;

        //    System.out.println("==================actualUnitPriceVal==========22== "+actualUnitPriceVal);
        //
        //    System.out.println("==================addPriceMoqVal==========22== "+addPriceMoqVal);
        //
        //    System.out.println("==================addChrgLCVal==========22== "+addChrgLCVal);
        //
        //    System.out.println("==================addChrgPrcntVal==========22== "+addChrgPrcntVal);
        //
        //    System.out.println("==================addPriceFOBVal==========22== "+addPriceFOBVal);
        //
        //    System.out.println("==================refreshValues==========2== ");

        try {
            if (MyMath.numeric(getUomConversion()) != 0) {
                addChrgLCVal =
                        addChrgLCVal / MyMath.numeric(getUomConversion());

                //    System.out.println("=======addChrgLCVal======="+addChrgLCVal);

                actualUnitPriceCalc =
                        actualUnitPriceVal / MyMath.numeric(getUomConversion());
                addPriceFOBVal =
                        addPriceFOBVal / MyMath.numeric(getUomConversion());
                addPriceMoqVal =
                        addPriceMoqVal / MyMath.numeric(getUomConversion());
            }
        } catch (Exception e) {
            ;
        }

        actualUnitPriceCalc =
                (actualUnitPriceCalc != 0 ? actualUnitPriceCalc : actualUnitPriceVal);

        try {
            lcCalculated =
                    (addChrgLCVal != 0 ? addChrgLCVal : ((addChrgPrcntVal) /
                                                         100 *
                                                         actualUnitPriceCalc));
        } catch (Exception e) {
            ;
        }


        double finalUnitPriceVal =
            MyMath.round(actualUnitPriceCalc + addPriceMoqVal + lcCalculated +
                         +addPriceFOBVal);
        System.out.println("================= finalUnitPriceVal ================" +
                           finalUnitPriceVal);
        //if (finalUnitPriceVal > 0)
        finalUnitPrice.setValue(MyMath.toNumber(MyMath.roundTo4(finalUnitPriceVal)));

        System.out.println("================= MyMath.toNumber(MyMath.roundTo4(finalUnitPriceVal)) ================" +
                           MyMath.toNumber(MyMath.roundTo4(finalUnitPriceVal)));

        AdfFacesContext.getCurrentInstance().addPartialTarget(finalUnitPrice);

        double consperPcsVal = MyMath.numeric(getConsPerPcs());
        double wasteInPercVal = MyMath.numeric(getWasteInPercent());

        double wasteInQtyVal = consperPcsVal * (wasteInPercVal / 100);

        //    System.out.println("================= wasteInQtyVal ================"+wasteInQtyVal);
        // if (wasteInQtyVal > 0)
        wasteInQty.setValue(MyMath.toNumber(wasteInQtyVal));

        AdfFacesContext.getCurrentInstance().addPartialTarget(wasteInQty);

        double consWithWasteVal = consperPcsVal * (1 + wasteInPercVal / 100);
        // if (consWithWasteVal > 0)
        consWithWaste.setValue(MyMath.toNumber(consWithWasteVal));

        AdfFacesContext.getCurrentInstance().addPartialTarget(consWithWaste);

        System.out.println("=============== consWithWasteVal ==============" +
                           consWithWasteVal);

        double bufferVal = MyMath.numeric(getBuffer());
        // double finalConsVal = bufferVal + consWithWasteVal;
        double finalConsVal = consWithWasteVal;

        // if (finalConsVal > 0)
        finalCons.setValue(MyMath.toNumber(MyMath.round(finalConsVal)));
        AdfFacesContext.getCurrentInstance().addPartialTarget(finalCons);


        double costPerPcsVal = (finalUnitPriceVal * finalConsVal) + bufferVal;
        // if (costPerPcsVal > 0)
        // costPerPcsVal = Math.ceil((double)MyMath.roundTo3(costPerPcsVal)) ;
        
      //   appM.getXX_OM_POC_D1_TVO1().getCurrentRow().setAttribute("CostPerPcs" ,costPerPcsVal  );
      costPerPcs.setValue(MyMath.toNumber(MyMath.roundUp(costPerPcsVal)));
        AdfFacesContext.getCurrentInstance().addPartialTarget(costPerPcs);

        //    System.out.println("=================== costPerPcsVal ================="+costPerPcsVal);

        double addDeductCostVal = MyMath.numeric(getAddDeductCost());
        double finalCostPerPcsVal = costPerPcsVal + addDeductCostVal;

        //     System.out.println("================ finalCostPerPcsVal ==========="+finalCostPerPcsVal);


        double fabFinanceCostVal = 0;

        // if(MyMath.numeric(getPrefixCode())== 11 || MyMath.numeric(getPrefixCode())== 12) {
        // fabFinanceCostVal = finalCostPerPcsVal * (MyMath.numeric3(getFabricFinance())/100);
        // }
        fabFinanceCost.setValue(MyMath.toNumber(fabFinanceCostVal));
        AdfFacesContext.getCurrentInstance().addPartialTarget(fabFinanceCost);

        //        System.out.println("================ fabFinanceCostVal ==========="+fabFinanceCostVal);
        //        System.out.println("================ finalCostPerPcsVal ==========="+finalCostPerPcsVal);

        // refreshTotal(finalCostPerPcsVal);
        // if (finalCostPerPcsVal > 0)


       finalCostPerPcs.setValue(MyMath.toNumber(MyMath.roundUp(finalCostPerPcsVal +  fabFinanceCostVal)));


        AdfFacesContext.getCurrentInstance().addPartialTarget(finalCostPerPcs);

        // CHANGE bY BILAL 03MAR17 double costPerLineVal = MyMath.numeric(getCostPerline());
        double costPerLineVal = MyMath.numeric(getProdCostPerLine());
        double prodPerLineVal = MyMath.numeric(getProducPerLine());
        //    System.out.println("........cost per line"+costPerLineVal);
        //    System.out.println("........prod per line"+prodPerLineVal);
        double costVal = 0.00;
        if (costPerLineVal != 0 && prodPerLineVal != 0) {

            costVal = costPerLineVal / prodPerLineVal;
        }
        // if (costVal > 0)
        cost.setValue(MyMath.toNumber(MyMath.round(costVal)));

        AdfFacesContext.getCurrentInstance().addPartialTarget(cost);


        // double dryCostVal = MyMath.numeric(getDryCost());
        // double dryProfitVal = MyMath.numeric(getDryProfit());
        // double dryTotalVal = dryCostVal + dryProfitVal;
        // // refreshGrandDryTotal(dryTotalVal);
        // if (dryTotalVal > 0)
        // dryTotal.setValue(MyMath.toNumber(MyMath.round(dryTotalVal)));
        // AdfFacesContext.getCurrentInstance().addPartialTarget(dryTotal);


        // double wetCostVal = MyMath.numeric(getWetCost());
        // double wetProfitVal = MyMath.numeric(getWetProfit());
        // double wetTotalVal = wetCostVal + wetProfitVal;
        // // refreshWetGrandTotal(wetTotalVal);
        // if (wetTotalVal > 0)
        // wetTotal.setValue(MyMath.toNumber(MyMath.round(wetTotalVal)));
        // AdfFacesContext.getCurrentInstance().addPartialTarget(wetTotal);
        setFinanceCalc(0);
    }

    public void commonListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        System.out.println("Component Id in common listener ---> " +
                           valueChangeEvent.getComponent().getId());

        RichInputText inputText =
            (RichInputText)root.findComponent(valueChangeEvent.getComponent().getId());
        // inputText.setValue(valueChangeEvent);
        if (valueChangeEvent.getNewValue() == null) {
            // inputText.setSubmittedValue(null);
            // inputText.resetValue();
        } else {
            System.out.println("aluechnged e -> " +
                               valueChangeEvent.getNewValue());
            ViewObject linenumber = appM.getXX_OM_POC_D1_TVO1();
            try {

                linenumber.getCurrentRow().getAttribute("FobId").toString();
                System.out.println("..........befor");
                refreshValues();
                System.out.println("..........after");
            } catch (Exception e) {
                ;
            }
        }

    }


    public void setActualUnitPrice(RichInputText actualUnitPrice) {
        this.actualUnitPrice = actualUnitPrice;
    }

    public RichInputText getActualUnitPrice() {
        return actualUnitPrice;
    }

    public void setAddChargLC(RichInputText addChargLC) {
        this.addChargLC = addChargLC;
    }

    public RichInputText getAddChargLC() {
        return addChargLC;
    }

    public void setAddChrgPrcnt(RichInputText addChrgPrcnt) {
        this.addChrgPrcnt = addChrgPrcnt;
    }

    public RichInputText getAddChrgPrcnt() {
        return addChrgPrcnt;
    }

    public void setFinalUnitPrice(RichInputText finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }

    public RichInputText getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setConsPerPcs(RichInputText consPerPcs) {
        this.consPerPcs = consPerPcs;
    }

    public RichInputText getConsPerPcs() {
        return consPerPcs;
    }

    public void setWasteInPercent(RichInputText wasteInPercent) {
        this.wasteInPercent = wasteInPercent;
    }

    public RichInputText getWasteInPercent() {
        return wasteInPercent;
    }

    public void setCostPerPcs(RichInputText costPerPcs) {
        this.costPerPcs = costPerPcs;
    }

    public RichInputText getCostPerPcs() {
        return costPerPcs;
    }

    public void setFinalCostPerPcs(RichInputText finalCostPerPcs) {
        this.finalCostPerPcs = finalCostPerPcs;
    }

    public RichInputText getFinalCostPerPcs() {
        return finalCostPerPcs;
    }

    public void setUomConversion(RichInputText uomConversion) {
        this.uomConversion = uomConversion;
    }

    public RichInputText getUomConversion() {
        return uomConversion;
    }

    public void setAddPriceFOB(RichInputText addPriceFOB) {
        this.addPriceFOB = addPriceFOB;
    }

    public RichInputText getAddPriceFOB() {
        return addPriceFOB;
    }

    public void setAddPriceMOQ(RichInputText addPriceMOQ) {
        this.addPriceMOQ = addPriceMOQ;
    }

    public RichInputText getAddPriceMOQ() {
        return addPriceMOQ;
    }

    public void setBuffer(RichInputText buffer) {
        this.buffer = buffer;
    }

    public RichInputText getBuffer() {
        return buffer;
    }

    public void setAddDeductCost(RichInputText addDeductCost) {
        this.addDeductCost = addDeductCost;
    }

    public RichInputText getAddDeductCost() {
        return addDeductCost;
    }

    public void setWasteInQty(RichInputText wasteInQty) {
        this.wasteInQty = wasteInQty;
    }

    public RichInputText getWasteInQty() {
        return wasteInQty;
    }

    public void setConsWithWaste(RichInputText consWithWaste) {
        this.consWithWaste = consWithWaste;
    }

    public RichInputText getConsWithWaste() {
        return consWithWaste;
    }

    public void setFinalCons(RichInputText finalCons) {
        this.finalCons = finalCons;
    }

    public RichInputText getFinalCons() {
        return finalCons;
    }

    public void setFabFinanceCost(RichInputText fabFinanceCost) {
        this.fabFinanceCost = fabFinanceCost;
    }

    public RichInputText getFabFinanceCost() {
        return fabFinanceCost;
    }

    public void setFabricD(RichInputText fabricD) {
        this.fabricD = fabricD;
    }

    public RichInputText getFabricD() {
        return fabricD;
    }

    public void setSmsQty(RichInputText smsQty) {
        this.smsQty = smsQty;
    }

    public RichInputText getSmsQty() {
        return smsQty;
    }

    public void setOrderQty(RichInputText orderQty) {
        this.orderQty = orderQty;
    }

    public RichInputText getOrderQty() {
        return orderQty;
    }

    public void setCmMerchad(RichInputText cmMerchad) {
        this.cmMerchad = cmMerchad;
    }

    public RichInputText getCmMerchad() {
        return cmMerchad;
    }

    public void setProducPerLine(RichInputText producPerLine) {
        this.producPerLine = producPerLine;
    }

    public RichInputText getProducPerLine() {
        return producPerLine;
    }

    public void setStyleEff(RichInputText styleEff) {
        this.styleEff = styleEff;
    }

    public RichInputText getStyleEff() {
        return styleEff;
    }

    public void setSamValue(RichInputText samValue) {
        this.samValue = samValue;
    }

    public RichInputText getSamValue() {
        return samValue;
    }

    public void setCost(RichInputText cost) {
        this.cost = cost;
    }

    public RichInputText getCost() {
        return cost;
    }

    public void setProdCostPerLine(RichInputText prodCostPerLine) {
        this.prodCostPerLine = prodCostPerLine;
    }

    public RichInputText getProdCostPerLine() {
        return prodCostPerLine;
    }

    public void setFinance(RichInputText finance) {
        this.finance = finance;
    }

    public RichInputText getFinance() {
        return finance;
    }

    public void setProfit(RichInputText profit) {
        this.profit = profit;
    }

    public RichInputText getProfit() {
        return profit;
    }

    public void setCm(RichInputText cm) {
        this.cm = cm;
    }

    public RichInputText getCm() {
        return cm;
    }

    public void productionListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setProductionUnit(RichSelectOneChoice productionUnit) {
        this.productionUnit = productionUnit;
    }

    public RichSelectOneChoice getProductionUnit() {
        return productionUnit;
    }

    public void LaunchPopupListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

    }

    public void setFabricFinancePrice(RichInputText fabricFinancePrice) {
        this.fabricFinancePrice = fabricFinancePrice;
    }

    public RichInputText getFabricFinancePrice() {
        return fabricFinancePrice;
    }

    public void setTrimsFinance(RichInputText trimsFinance) {
        this.trimsFinance = trimsFinance;
    }

    public RichInputText getTrimsFinance() {
        return trimsFinance;
    }

    public void setSubCostingTable(RichTable subCostingTable) {
        this.subCostingTable = subCostingTable;
    }

    public RichTable getSubCostingTable() {
        return subCostingTable;
    }

    public void setWashFinanceValue(RichInputText washFinanceValue) {
        this.washFinanceValue = washFinanceValue;
    }

    public RichInputText getWashFinanceValue() {
        return washFinanceValue;
    }

    public void setCmMerchandiser(RichInputText cmMerchandiser) {
        this.cmMerchandiser = cmMerchandiser;
    }

    public RichInputText getCmMerchandiser() {
        return cmMerchandiser;
    }

    public void dry(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        double dryCostVal = MyMath.numeric(getDryCost());
        double dryProfitVal = MyMath.numeric(getDryProfit());
        double dryTotalVal = dryCostVal + dryProfitVal;
        // refreshGrandDryTotal(dryTotalVal);
        //        try{
        //        System.out.println("=======================dryCostVal========================"+dryCostVal);
        //
        //        System.out.println("=======================dryProfitVal========================"+dryProfitVal);
        //
        //        System.out.println("=======================dryTotalVal========================"+dryTotalVal);
        //        }
        //        catch(Exception e){
        //            e.printStackTrace();
        //            }
        // if (dryTotalVal > 0)
        dryTotal.setValue(MyMath.toNumber(MyMath.round(dryTotalVal)));
        AdfFacesContext.getCurrentInstance().addPartialTarget(dryTotal);
    }

    public void setDryCost(RichInputText dryCost) {
        this.dryCost = dryCost;
    }

    public RichInputText getDryCost() {
        return dryCost;
    }

    public void setDryProfit(RichInputText dryProfit) {
        this.dryProfit = dryProfit;
    }

    public RichInputText getDryProfit() {
        return dryProfit;
    }

    public void setDryTotal(RichInputText dryTotal) {
        this.dryTotal = dryTotal;
    }

    public RichInputText getDryTotal() {
        return dryTotal;
    }

    public void wetListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        double wetCostVal = MyMath.numeric(getWetCost());
        double wetProfitVal = MyMath.numeric(getWetProfit());
        double wetTotalVal = wetCostVal + wetProfitVal;
        // refreshWetGrandTotal(wetTotalVal);
        // if (wetTotalVal > 0)
        wetTotal.setValue(MyMath.toNumber(MyMath.round(wetTotalVal)));
        AdfFacesContext.getCurrentInstance().addPartialTarget(wetTotal);
    }

    public void setWetCost(RichInputText wetCost) {
        this.wetCost = wetCost;
    }

    public RichInputText getWetCost() {
        return wetCost;
    }

    public void setWetProfit(RichInputText wetProfit) {
        this.wetProfit = wetProfit;
    }

    public RichInputText getWetProfit() {
        return wetProfit;
    }

    public void setWetTotal(RichInputText wetTotal) {
        this.wetTotal = wetTotal;
    }

    public RichInputText getWetTotal() {
        return wetTotal;
    }

    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        if (popupFetchEvent.getLaunchSourceClientId().contains("cbInsert")) {
            System.out.println("Insert event called......!");

        }
    }

    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
    }

    public void editDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            try {
                callCopy(getSelectRadio().getValue().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();

        }
    }

    public void callCopy(String type) {

        //        System.out.println("========== String type ========="+type);
        //        System.out.println("==========getPocId().getValue() ========="+getPocId().getValue());
        //        System.out.println("==========getSlectedFobID() ========="+getSlectedFobID().toString());

        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("copyRecords");

        operationBinding.getParamsMap().put("type", type);
        operationBinding.getParamsMap().put("PocId", getPocId().getValue());
        operationBinding.getParamsMap().put("FobId", getSlectedFobID());


        //invoke method
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("if errors-->");
            // List errors = operationBinding.getErrors();
        }
        //optional
        Object methodReturnValue = operationBinding.getResult();
        String message = null;
        if (methodReturnValue != null) {
            message = methodReturnValue.toString();
        } else {
            message = "Failed! .";
        }
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(subCostingTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
    }

    public Number getSlectedFobID() {

        oracle.adf.view.rich.component.UIXTable table = getSubCostingTable();
        // Get the Selected Row key set iterator

        //((FOB*Comission(%))*Order Qty+(FOB*Comission(%))*Sample Qty)/Order Qty
        java.util.Iterator selectionIt = table.getSelectedRowKeys().iterator();
        Number id = new Number(0);

        while (selectionIt.hasNext()) {
            Object rowKey = selectionIt.next();
            table.setRowKey(rowKey);
            int index = table.getRowIndex();
            FacesCtrlHierNodeBinding row =
                (FacesCtrlHierNodeBinding)table.getRowData(index);
            Row selectedRow = row.getRow();
            id = (Number)selectedRow.getAttribute("FobId");
        }
        return id;

    }

    public void setSelectRadio(RichSelectOneRadio selectRadio) {
        this.selectRadio = selectRadio;
    }

    public RichSelectOneRadio getSelectRadio() {
        return selectRadio;
    }

    public void setPocId(RichInputText pocId) {
        this.pocId = pocId;
    }

    public RichInputText getPocId() {
        return pocId;
    }

    public void CopyItem_Detail_Bind(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding ob = executeOperation("CopyItemsDetail");
        ob.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
    }

    public void copyItemsPopup(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        ViewObject popVo = appM.getPOCLinesCopyVO1(); /// pop up view
        popVo.executeQuery();
    }

    public void itemDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        try {
            populateBundlesRec();
            refershButton();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dryDetailsTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wetDetailsTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(costPerPcs);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cost_per_pcs_trims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      

        
        
    }

    public void populateBundlesRec() {

        ViewObject populatevo = appM.getPOCLinesCopyVO1(); // pop up view
        ViewObject linevo = appM.getXX_OM_POC_L_TVO1();
        String ToFobId=null;
        try {
      ToFobId=linevo.getCurrentRow().getAttribute("FobId").toString();

        } catch (Exception e) {
            e.printStackTrace();
            ToFobId=null;
        }
        // populatevo.executeQuery();
        //System.out.println("--------------------------------populateBundlesRec-----------------------------------------------");
        Row[] r = populatevo.getAllRowsInRange();
        //System.out.println(" ------------------------------populateBundlesRec after row-------------------------------------------------");
        System.out.println("Drop ------->" + r.length);
        for (Row row : r) {
            //  System.out.println("MultiSelect Check--->" + row.getAttribute("MultiSelect"));
            //  System.out.println("--------------------inside for loop---------------------------"+row.getAttribute("MultiSelect"));
            if (row.getAttribute("MultiSelect") != null &&
                row.getAttribute("MultiSelect").equals("Y")) {
                System.out.println("-------------------------inside if cond------------------------------");
                //System.out.println("MultiSelect --->" + row.getAttribute("MultiSelect"));
                String FobId = row.getAttribute("FobId").toString();
                System.out.println("Fob id-------------->" + FobId);
                
                populateItemDetails(FobId); /// method to populate data
                populateDryDetails(FobId);
                insertingpocL(FobId,ToFobId);
                
                System.out.println("fob id is ------------"+FobId);
            }
        }
    }


    public void populateItemDetails(String FobId) {

        String query = " SELECT DISTINCT\n" +
            " MMPID.DETAIL_ID,\n" +
            " MMPID.FOB_ID,\n" +
            " (select t.value_description\n" +
            " from MNJ_ITEM_PREFIX_V t\n" +
            " where t.flex_value = to_char(MMPID.Item_Prefix))PREFIX_DESC,\n" +
            " MMPID.REF_CODE,\n" +
            " MMPID.ITEM_DESC,\n" +
            " MMPID.UOM,\n" +
            " MMPID.PRICE_UOM,\n" +
            " MMPID.ACTUAL_UNIT_PRICE,\n" +
            " MMPID.ADD_CHARGE_LC,\n" +
            " MMPID.ADD_CRG_PRCNT,\n" +
            " MMPID.FINAL_UNIT_PRICE,\n" +
            " MMPID.CONS_PER_PCS,\n" +
            " MMPID.WASTAGE_IN_PER,\n" +
            " MMPID.COST_PER_PCS,\n" +
            " MMPID.FINAL_COST_PER_PCS,\n" +
        " MMPID.ITEM_PREFIX,\n" +
            " MMPID.REMARKS,\n" +
            " MMPID.UOM_CONV,\n" +
            " MMPID.PAYMENT_TERM_ID,\n" +
            " MMPID.DELIVERY_TERM_ID,\n" +
            " MMPID.STATUS,\n" +
            " MMPID.ADD_PRICE_FOB,\n" +
            " MMPID.ADD_PRICE_MOQ,\n" +
            " MMPID.BUFER,\n" +
            " MMPID.ADD_DEDUCT_COST,\n" +
            " MMPID.WASTAGE_IN_QTY,\n" +
            " MMPID.CONS_WITH_WASTAGE,\n" +
            " MMPID.FINAL_CONS,\n" +
            " MMPID.FAB_FINANCE_COST\n" +
            " FROM XX_OM_POC_D1_T MMPID,XX_OM_POC_L_T MMPL,\n" +
            " QA_PLANS QP,QA_RESULTS QR\n" +
            " \n" +
            " WHERE MMPID.ITEM_PREFIX=QR.CHARACTER2\n" +
            " AND qr.plan_id=qp.plan_id\n" +
            " AND UPPER(QP.NAME) = 'SEQUENCE NO POC' \n" +
            " AND MMPID.FOB_ID=MMPL.FOB_ID\n" +
            " AND MMPID.FOB_ID=?\n" +
            " ORDER BY MMPID.DETAIL_ID";


        ResultSet resultSet = null;
        PreparedStatement createStatement =
            appM.getDBTransaction().createPreparedStatement(query, 0);
        //System.out.println("2");
        try {
            createStatement.setString(1, FobId);
            resultSet = createStatement.executeQuery();
            //System.out.println("3");
            //System.out.println("populateLinesTest ------->");
            //System.out.println("-------------------------------------populateLinesTestRec------------------------------------------");
                 
            ViewObject vo =
                appM.getXX_OM_POC_D1_TVO1(); // in which you wants to populate daa
            System.out.println("getXX_OM_POC_D1_TVO1() Query: " + '\n' +
                               vo.getQuery());
            removeData(vo);
            System.out.println("4");

            while (resultSet.next()) {
                // code to display the rows in the table.
                Row linerow = vo.createRow();
                linerow.setNewRowState(Row.STATUS_INITIALIZED);
                linerow.setAttribute("PrefixDesc",
                                     resultSet.getString("PREFIX_DESC"));
                //   System.out.println("===== PrefixDesc ====="+resultSet.getString("PREFIX_DESC"));
                linerow.setAttribute("RefCode",
                                     resultSet.getString("REF_CODE"));
                //   System.out.println("===== RefCode ====="+resultSet.getString("REF_CODE"));
                linerow.setAttribute("ItemDesc",
                                     resultSet.getString("ITEM_DESC"));
                //   System.out.println("===== ItemDesc ====="+resultSet.getString("ITEM_DESC"));
                linerow.setAttribute("Uom", resultSet.getString("UOM"));
                //   System.out.println("===== UOM ====="+resultSet.getString("UOM"));
                linerow.setAttribute("PriceUom",
                                     resultSet.getString("PRICE_UOM"));
                //   System.out.println("===== PRICE_UOM ====="+resultSet.getString("PRICE_UOM"));
                linerow.setAttribute("ActualUnitPrice",
                                     resultSet.getString("ACTUAL_UNIT_PRICE"));
                //   System.out.println("===== ACTUAL_UNIT_PRICE ====="+resultSet.getString("ACTUAL_UNIT_PRICE"));
                linerow.setAttribute("AddChargeLc",
                                     resultSet.getString("ADD_CHARGE_LC"));
                //   System.out.println("===== ADD_CHARGE_LC ====="+resultSet.getString("ADD_CHARGE_LC"));
                linerow.setAttribute("AddCrgPrcnt",
                                     resultSet.getString("ADD_CRG_PRCNT"));
                //  System.out.println("===== ADD_CRG_PRCNT ====="+resultSet.getString("ADD_CRG_PRCNT"));
                linerow.setAttribute("FinalUnitPrice",
                                     resultSet.getString("FINAL_UNIT_PRICE"));
                //  System.out.println("===== FINAL_UNIT_PRICE ====="+resultSet.getString("FINAL_UNIT_PRICE"));
                linerow.setAttribute("ConsPerPcs",
                                     resultSet.getString("CONS_PER_PCS"));
                //   System.out.println("===== CONS_PER_PCS ====="+resultSet.getString("CONS_PER_PCS"));
                linerow.setAttribute("WastageInPer",
                                     resultSet.getString("WASTAGE_IN_PER"));
                //  System.out.println("===== WASTAGE_IN_PER ====="+resultSet.getString("WASTAGE_IN_PER"));
                linerow.setAttribute("CostPerPcs",
                                     resultSet.getString("COST_PER_PCS"));
                //   System.out.println("===== COST_PER_PCS ====="+resultSet.getString("COST_PER_PCS"));
                linerow.setAttribute("FinalCostPerPcs",
                                     resultSet.getString("FINAL_COST_PER_PCS"));
                //  System.out.println("===== FINAL_COST_PER_PCS ====="+resultSet.getString("FINAL_COST_PER_PCS"));
                linerow.setAttribute("Remarks",
                                     resultSet.getString("REMARKS"));
                //   System.out.println("===== REMARKS ====="+resultSet.getString("REMARKS"));
                linerow.setAttribute("UomConv",
                                     resultSet.getString("UOM_CONV"));
                //  System.out.println("===== UOM_CONV ====="+resultSet.getString("UOM_CONV"));
                linerow.setAttribute("PaymentTermId",
                                     resultSet.getString("PAYMENT_TERM_ID"));
                //  System.out.println("===== UOM_CONV ====="+resultSet.getString("UOM_CONV"));
                linerow.setAttribute("DeliveryTermId",
                                     resultSet.getString("DELIVERY_TERM_ID"));
                //  System.out.println("===== DELIVERY_TERM_ID ====="+resultSet.getString("DELIVERY_TERM_ID"));
                linerow.setAttribute("Status", resultSet.getString("STATUS"));
                //   System.out.println("===== STATUS ====="+resultSet.getString("STATUS"));
                linerow.setAttribute("AddPriceFob",
                                     resultSet.getString("ADD_PRICE_FOB"));
                //  System.out.println("===== ADD_PRICE_FOB ====="+resultSet.getString("ADD_PRICE_FOB"));
                linerow.setAttribute("AddPriceMoq",
                                     resultSet.getString("ADD_PRICE_MOQ"));
                //  System.out.println("===== ADD_PRICE_MOQ ====="+resultSet.getString("ADD_PRICE_MOQ"));
                linerow.setAttribute("Bufer", resultSet.getString("BUFER"));
                //  System.out.println("===== BUFER ====="+resultSet.getString("BUFER"));
                linerow.setAttribute("AddDeductCost",
                                     resultSet.getString("ADD_DEDUCT_COST"));
                //  System.out.println("===== ADD_DEDUCT_COST ====="+resultSet.getString("ADD_DEDUCT_COST"));
                linerow.setAttribute("WastageInQty",
                                     resultSet.getString("WASTAGE_IN_QTY"));
                //  System.out.println("===== WASTAGE_IN_QTY ====="+resultSet.getString("WASTAGE_IN_QTY"));
                linerow.setAttribute("ConsWithWastage",
                                     resultSet.getString("CONS_WITH_WASTAGE"));
                //  System.out.println("===== CONS_WITH_WASTAGE ====="+resultSet.getString("CONS_WITH_WASTAGE"));
                linerow.setAttribute("FinalCons",
                                     resultSet.getString("FINAL_CONS"));
                //   System.out.println("===== FINAL_CONS ====="+resultSet.getString("FINAL_CONS"));
                linerow.setAttribute("FabFinanceCost",
                                     resultSet.getString("FAB_FINANCE_COST"));
                linerow.setAttribute("ItemPrefix",
                                     resultSet.getString("ITEM_PREFIX")); 
                //  System.out.println("===== FAB_FINANCE_COST ====="+resultSet.getString("FAB_FINANCE_COST"));
                vo.insertRow(linerow);
            }

        } catch (SQLException e) {
            System.out.println("Exception in getting query data");
        }

    } //end of populateLines

    public void populateDryDetails(String FobId) {
        String query = "SELECT DISTINCT\n" +
            " MMPDD.FOB_ID, \n" +
            " MMPDD.COST_AMOUNT,\n" +
            " MMPDD.MANUAL_PRICE, \n" +
            " MMPDD.PROCESS_ID, \n" +
            " MMPDD.PROCESS_NAME, \n" +
            " MMPDD.PROFIT, \n" +
            " MMPDD.TOTAL\n" +
            " \n" +
            " FROM XX_OM_POC_L_T MMPL,\n" +
            " XX_OM_POC_D2_T MMPDD \n" +
            " \n" +
            " WHERE MMPDD.FOB_ID=MMPL.FOB_ID\n" +
            " AND MMPDD.FOB_ID=?";
        ResultSet resultSet = null;
        PreparedStatement createStatement =
            appM.getDBTransaction().createPreparedStatement(query, 0);
        try {
            createStatement.setString(1, FobId);
            resultSet = createStatement.executeQuery();

            //System.out.println("populateLinesTest ------->");
            //System.out.println("-------------------------------------populateLinesTestRec------------------------------------------");

            ViewObject vo =
                appM.getXX_OM_POC_D2_TVO1(); // in which you wants to populate daa
            removeData(vo);

            while (resultSet.next()) {
                // code to display the rows in the table.
                Row linerow = vo.createRow();
                linerow.setNewRowState(Row.STATUS_INITIALIZED);

                linerow.setAttribute("ProcessName",
                                     resultSet.getString("PROCESS_NAME"));
                //  System.out.println("======= Name ======="+resultSet.getString("PROCESS_NAME"));
                linerow.setAttribute("CostAmount",
                                     resultSet.getString("COST_AMOUNT"));
                //  System.out.println("======= COST_AMOUNT ======="+resultSet.getString("COST_AMOUNT"));
                linerow.setAttribute("Profit", resultSet.getString("PROFIT"));
                //  System.out.println("======= PROFIT ======="+resultSet.getString("PROFIT"));
                linerow.setAttribute("Total", resultSet.getString("TOTAL"));
                //  System.out.println("======= TOTAL ======="+resultSet.getString("TOTAL"));
                linerow.setAttribute("ManualPrice",
                                     resultSet.getString("MANUAL_PRICE"));
                linerow.setAttribute("ProcessId",
                                     resultSet.getString("PROCESS_ID"));
                //  System.out.println("======= MANUAL_PRICE ======="+resultSet.getString("MANUAL_PRICE"));

                 System.out.println("Process ID is **************#########"+resultSet.getString("PROCESS_ID"));
                vo.insertRow(linerow);
            }

        } catch (SQLException e) {
            System.out.println("Exception in getting query data");
        }
    }

    public void removeData(ViewObject vo) {
        RowSetIterator iter = vo.createRowSetIterator(null);
        iter.reset();
        while (iter.hasNext()) {
            Row row = iter.next();
            row.remove();
            System.out.println("row removed...");
            // process the row here
        }
        iter.closeRowSetIterator();
        vo.executeEmptyRowSet();
    }
    public void insertingpocL(String FromFobId,String ToFobId){

     // System.out.println("######from fobid:"+FromFobId+"####to fobId:"+ToFobId);
                    String stmt = "BEGIN XX_OM_POC_L_UPDATE_PROC  (:1,:2); end;";

                    java.sql.CallableStatement cs =
                    appM.getDBTransaction().createCallableStatement(stmt, 1);
                    try {               
                    cs.setInt(1, Integer.parseInt(FromFobId));
                    cs.setInt(2, Integer.parseInt(ToFobId));
                    cs.execute();
                    cs.close();
                                    
                    } catch (SQLException e) {
                         e.printStackTrace();

                        }


    }
    public void setDryDetailsTable(RichTable dryDetailsTable) {
        this.dryDetailsTable = dryDetailsTable;
    }

    public RichTable getDryDetailsTable() {
        return dryDetailsTable;
    }

    public void setWetDetailsTable(RichTable wetDetailsTable) {
        this.wetDetailsTable = wetDetailsTable;
    }

    public RichTable getWetDetailsTable() {
        return wetDetailsTable;
    }

    public String sendForApproval() {
        // Add event code here...
        refershButton();
        System.out.println("=========== Sent For Approval ===========" +
                           getPocId().getValue());
        OperationBinding ob = executeOperation("Approve");
        ob.getParamsMap().put("PocId", getPocId().getValue());
        ob.execute();
        FacesMessage fm = new FacesMessage("Successfully Sent For Approval");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);

        return null;
    }

    //    public void CallCopy(ActionEvent actionEvent) {
    //        // Add event code here...
    //        System.out.println("Copy Precost AM 1---->>");
    //        OperationBinding ob = executeOperation("CopyPrecost");
    //        ob.getParamsMap().put("type","C");
    //        ob.execute();
    //        String message="POC Copied Sussesfully";
    //        FacesMessage fm = new FacesMessage(message);
    //        fm.setSeverity(FacesMessage.SEVERITY_INFO);
    //        FacesContext context = FacesContext.getCurrentInstance();
    //        context.addMessage(null, fm);
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(queryTable);
    //        System.out.println("Copy Precost AM 2---->>");
    //    //    removeCopiedRowInLine();
    //    }
    //


    public void setQueryTable(RichTable queryTable) {
        this.queryTable = queryTable;
    }

    public RichTable getQueryTable() {
        return queryTable;
    }
    //
    //    public void CallVersion(ActionEvent actionEvent) {
    //        // Add event code here...
    //        OperationBinding ob = executeOperation("CopyPrecost");
    //        ob.getParamsMap().put("type", "V");
    //        ob.execute();
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(queryTable);
    //    }

    public String createItem() {
        // Add event code here...
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("createItems");
        operationBinding.getParamsMap().put("PocId", getPocId().getValue());
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("if errors-->");
            // List errors = operationBinding.getErrors();
        }
        //optional
        Object methodReturnValue = operationBinding.getResult();
        String message = null;
        if (methodReturnValue != null) {
            message = methodReturnValue.toString();
        } else {
            message = "Failed! .";
        }
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        return null;
    }

    public String SaveManagementCal() {
        // Add event code here...
        OperationBinding ob = executeOperation("Commit");
        ob.execute();
        ViewObject linenumber = appM.getXX_OM_POC_L_TVO1();
        try {
            linenumber.getCurrentRow().getAttribute("FobId").toString();
            System.out.println("..........befor");
            setFinanceCalc(1);
            System.out.println("..........after");
        } catch (Exception e) {
            ;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(subCostingTable);
        //        // Code For Style Tracking
        //        OperationBinding operationBinding = executeOperation("StyleTracking");
        //        operationBinding.execute();
        //        // End Code For Style Tracking
        OperationBinding ob1 = executeOperation("Commit");
        ob1.execute();
        return null;
    }

    public String Search() {
        // Add event code here...

      //  String unitId = null;

        ViewObject hvo = appM.getSearchVO1();

       /* try {
            unitId = hvo.getCurrentRow().getAttribute("OrgId").toString();
            //           unitId=getOrganizationId().getValue().toString();
        } catch (Exception e) {
            unitId = null;
        }

        System.out.println("=========== LC UNIT ===========" + unitId);
        //end of if condition*/
        String buyer_name = null;
        try {
            buyer_name =
                    hvo.getCurrentRow().getAttribute("BuyerId").toString();
            //          buyer_name= getBuyerId().getValue().toString();
        } catch (Exception e) {
            buyer_name = null;
        }
        System.out.println("=========== buyer_name ===========" + buyer_name);
        String Season = null;
        try {
            Season = hvo.getCurrentRow().getAttribute("Season").toString();
            //           Season= getSeasonId().getValue().toString();
        } catch (Exception e) {
            Season = null;
        }
       // System.out.println("=========== unitId ===========" + unitId);
        System.out.println("=========== buyer_name ===========" + buyer_name);
        System.out.println("=========== Season ===========" + Season);

        ViewObject vo = appM.getXX_OM_POC_H_TVO1();

        vo.setWhereClause(null);
        vo.clearCache();
        vo.setWhereClause("BUYER_ID = '" + buyer_name + "' AND SEASON = '" +
                          Season + "'");
        vo.executeQuery();
        //        System.out.println("=========== executeQuery ==========="+vo.getQuery());
        AdfFacesContext.getCurrentInstance().addPartialTarget(queryTable);

        return null;
    }


    public void setOrganizationId(RichSelectOneChoice organizationId) {
        this.organizationId = organizationId;
    }

    public RichSelectOneChoice getOrganizationId() {
        return organizationId;
    }

    public void setBuyerId(RichInputListOfValues buyerId) {
        this.buyerId = buyerId;
    }

    public RichInputListOfValues getBuyerId() {
        return buyerId;
    }

    public void setSeasonId(RichSelectOneChoice seasonId) {
        this.seasonId = seasonId;
    }

    public RichSelectOneChoice getSeasonId() {
        return seasonId;
    }

    public void setTotalCostCal(RichInputText totalCostCal) {
        this.totalCostCal = totalCostCal;
    }

    public RichInputText getTotalCostCal() {
        return totalCostCal;
    }


    public void setCostSan(RichInputText costSan) {
        this.costSan = costSan;
    }

    public RichInputText getCostSan() {
        return costSan;
    }

    public void TotalCostCal() {
        try {
            Double CostSan = 0.0;
            Double Finance = 0.0;
            Double calculatedValue = 0.0;
            try {
                CostSan =
                        Double.parseDouble(getCostSan().getValue().toString());
            } catch (Exception e) {
                CostSan = 0.0;
            }
            try {
                Finance =
                        Double.parseDouble(getFinance().getValue().toString());
            } catch (Exception e) {
                Finance = 0.0;
            }
            calculatedValue = CostSan + Finance;

            totalCostCal.setValue(new Number(calculatedValue));
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalCostCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedShipItem(RichSelectOneRadio selectedShipItem) {
        this.selectedShipItem = selectedShipItem;
    }

    public RichSelectOneRadio getSelectedShipItem() {
        return selectedShipItem;
    }

    public void ShipmentModeChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObject itemView = appM.getXX_OM_POC_L_TVO1();
        String test = getSelectedShipItem().getValue().toString();
        itemView.getCurrentRow().setAttribute("Shipment", test);

    }

    public void DialogCopy(DialogEvent dialogEvent) {
        // Add event code here...
        if (DialogEvent.Outcome.ok == dialogEvent.getOutcome().ok) {
            System.out.println("Copy Precost AM 1---->>");
            OperationBinding ob = executeOperation("CopyPrecost");
            ob.getParamsMap().put("type", "C");
            ob.execute();
            String message = "POC Copied Sussesfully";
            FacesMessage fm = new FacesMessage(message);
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, fm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(queryTable);
            System.out.println("DIALOGUE OK");
        }
    }

    public void CreateVersion(DialogEvent dialogEvent) {
        // Add event code here...
        OperationBinding ob = executeOperation("CopyPrecost");
        ob.getParamsMap().put("type", "V");
        ob.execute();
        String message = "Version Created Sussesfully";
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(queryTable);
    }

    public void GoBpoFormWithPocId(ActionEvent actionEvent) {
        // Add event code here...
        CreateCustom();
        ViewObject vo = appM.getXX_OM_POC_H_TVO1();
        int PocId =
            Integer.parseInt(vo.getCurrentRow().getAttribute("PocId").toString());
        String newPage =
            "http://192.168.200.115:7003/BPO-ViewController-context-root/faces/searchPG?PocId=" +
            PocId;

        urlink(newPage);

    }

    public void urlink(String link) {
        String newPage = link;

        // String newPage = "http://localhost:7101/PurchaseMemo-ViewController-context-root/faces/SearchPG?headerId="+getBomId().getValue();
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks =
            Service.getService(ctx.getRenderKit(), ExtendedRenderKitService.class);
        String url =
            "window.open('" + newPage + "','_blank','toolbar=no,location=no,menubar=no,alwaysRaised=yes,height=500,width=1100');";
        erks.addScript(FacesContext.getCurrentInstance(), url);
    }

    public void setCost_per_pcs_trims(RichInputText cost_per_pcs_trims) {
        this.cost_per_pcs_trims = cost_per_pcs_trims;
    }

    public RichInputText getCost_per_pcs_trims() {
        return cost_per_pcs_trims;
    }

    public void setConsPerPcs_trim(RichInputText consPerPcs_trim) {
        this.consPerPcs_trim = consPerPcs_trim;
    }

    public RichInputText getConsPerPcs_trim() {
        return consPerPcs_trim;
    }

    public void setActualUnitPrice_trim(RichInputText actualUnitPrice_trim) {
        this.actualUnitPrice_trim = actualUnitPrice_trim;
    }

    public RichInputText getActualUnitPrice_trim() {
        return actualUnitPrice_trim;
    }

    public void trims_value_Change_cal(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        ViewObject trimsView = appM.gettrimsVO1();


        double total = 0.00;
        double actUnitpc = 0.00;
        double val = 0.00;
        double buffer = 0.00;


        try {
            actUnitpc =
                    Double.parseDouble((getActualUnitPrice_trim().getValue().toString()));
            System.out.println("........................actUnitpc for trim= " +
                               actUnitpc);
            //            double tes=Double.parseDouble((trimsView.getCurrentRow().getAttribute("ActualUnitPrice").toString()));
            //            double te=Double.parseDouble((trimsView.getCurrentRow().getAttribute("PrefixDesc").toString()));
            //            System.out.println("......................current..actUnitpc for trim= "+tes);
            //            System.out.println("........................prefix for trim= "+te);
        } catch (Exception e) {
            actUnitpc = 0.00;
        }

        try {
            val =
Double.parseDouble((getConsPerPcs_trim().getValue().toString()));
            // double test=Double.parseDouble((trimsView.getCurrentRow().getAttribute("ConsPerPcs").toString()));
            System.out.println("........................actCons for trim= " +
                               val);
        } catch (Exception e) {
            val = 0.00;
        }


        try {
            buffer =
                    Double.parseDouble((getBuffer_trims().getValue().toString()));
        } catch (Exception e) {
            buffer = 0.00;
        }
        // AdfFacesContext.getCurrentInstance().addPartialTarget(bpoQt_value);
        //System.out.println(" Total BPO Qty------------>"+getBPOTotalQty());


        total = (val * actUnitpc) + buffer;
        // TrimCost=( trimsFinance_with_percentage/100)+total;
        trimsView.getCurrentRow().setAttribute("CostPerPcs", total);
        trimsView.getCurrentRow().setAttribute("FinalCostPerPcs", total);


        AdfFacesContext.getCurrentInstance().addPartialTarget(cost_per_pcs_trims);


    }

    public void setBuffer_trims(RichInputText buffer_trims) {
        this.buffer_trims = buffer_trims;
    }

    public RichInputText getBuffer_trims() {
        return buffer_trims;
    }

    public void othersValueChngCal(ValueChangeEvent valueChangeEvent) {
        // Add event code here...


        // Add event code here...
        ViewObject othersView = appM.getothers_VO1();

        double total = 0.00;
        double actUnitpc = 0.00;
        double val = 0.00;
        double bufer = 0.00;


        try {
            actUnitpc =
                    Double.parseDouble((getOtherActualPrc().getValue().toString()));
        } catch (Exception e) {
            actUnitpc = 0.00;
        }

        try {
            val =
Double.parseDouble((getOteherConPcs().getValue().toString()));
        } catch (Exception e) {
            val = 0.00;
        }


        try {
            bufer =
                    Double.parseDouble((getOther_bufer().getValue().toString()));
        } catch (Exception e) {
            bufer = 0.00;
        }

        // AdfFacesContext.getCurrentInstance().addPartialTarget(bpoQt_value);
        //System.out.println(" Total BPO Qty------------>"+getBPOTotalQty());

        // total= MyMath.toNumber(MyMath.roundUp(val*actUnitpc+bufer));
        total = val * actUnitpc + bufer;
        othersView.getCurrentRow().setAttribute("CostPerPcs", total);
        othersView.getCurrentRow().setAttribute("FinalCostPerPcs", total);

        AdfFacesContext.getCurrentInstance().addPartialTarget(othersCosPerPc);


    }

    public void setOther_bufer(RichInputText other_bufer) {
        this.other_bufer = other_bufer;
    }

    public RichInputText getOther_bufer() {
        return other_bufer;
    }

    public void setOtherActualPrc(RichInputText otherActualPrc) {
        this.otherActualPrc = otherActualPrc;
    }

    public RichInputText getOtherActualPrc() {
        return otherActualPrc;
    }

    public void setOteherConPcs(RichInputText oteherConPcs) {
        this.oteherConPcs = oteherConPcs;
    }

    public RichInputText getOteherConPcs() {
        return oteherConPcs;
    }

    public void setOthersCosPerPc(RichInputText othersCosPerPc) {
        this.othersCosPerPc = othersCosPerPc;
    }

    public RichInputText getOthersCosPerPc() {
        return othersCosPerPc;
    }

    public void setOther_table(RichTable other_table) {
        this.other_table = other_table;
    }

    public RichTable getOther_table() {
        return other_table;
    }

    public void goToBom(ActionEvent actionEvent) {
        // Add event code here...
        CreateBomProce();
        String newPage =
                "http://192.168.200.115:7003/megabom2-ViewController-context-root/faces/Query";
               
               urlink_bom(newPage);
        
        
    }
    public void urlink_bom(String link){
         String newPage =link;
          
         // String newPage = "http://localhost:7101/PurchaseMemo-ViewController-context-root/faces/SearchPG?headerId="+getBomId().getValue();
         FacesContext ctx = FacesContext.getCurrentInstance();
         ExtendedRenderKitService erks = Service.getService(ctx.getRenderKit(), ExtendedRenderKitService.class);
         String url = "window.open('" + newPage + "','_blank','toolbar=no,location=no,menubar=no,alwaysRaised=yes,height=500,width=1100');";
         erks.addScript(FacesContext.getCurrentInstance(), url);
     }
   
   
   
   
   
   
   
   /********method to call bpo create porcedure ************/
    
   public String CreateCustom() {
       // Add event code here...
      
       ViewObject searchVO = appM.getXX_OM_POC_H_TVO1();
      
       int poc = 0;
  
      

       
       /*       String message = null;
       String message1 = null;
       String message2 = null;
       String message3 = null;*/

      

       
           try {
               poc =
   Integer.parseInt(searchVO.getCurrentRow().getAttribute("PocId").toString());
           } catch (Exception e) {
               poc = 0;
           }
           

           


           String stmt = "BEGIN XX_OM_BPO_CREATE1(:1,:2,:3,:4,:5); end;";

           java.sql.CallableStatement cs =
               appM.getDBTransaction().createCallableStatement(stmt, 1);
           try {


               cs.setInt(1,poc);
               cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
               cs.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
               cs.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);
               cs.registerOutParameter(5, oracle.jdbc.OracleTypes.VARCHAR);


               cs.execute();
              // message = cs.getString(2);
              // message1 = cs.getString(3);
              // message2 = cs.getString(4);
              // message3 = cs.getString(5);
               cs.close();


           } catch (SQLException e) {
               e.printStackTrace();

           }

         /*  processvo.executeQuery();

           AdfFacesContext.getCurrentInstance().addPartialTarget(bpoTable);
           if (message != null) {
               showMessage(message, "info");
           }
           if (message1 != null) {
               showMessage(message1, "info");
           }
           if (message2 != null) {
               showMessage(message2, "info");
           }
           if (message3 != null) {
               showMessage(message3, "info");
           }
       */


       return null;
   }


    
    public String CreateBomProce() {
    // Add event code here...
    ViewObject searchVO = appM.getXX_OM_POC_H_TVO1();

    
    int poc = 0;
    // int pocfrom_pocForm = 0;
    // String Buyer = null;
    //int final_poc = 0;
    //int OrgId = 0;

    String message = null;
    
    /**code for disabling the create button*/

    

    
    try {
        poc =
    Integer.parseInt(searchVO.getCurrentRow().getAttribute("PocId").toString());
    } catch (Exception e) {
        poc = 0;
    }
    
    


    String stmt = "BEGIN APPS.XX_OM_BOM_INSERT_PROC(:1,:2); end;";

    java.sql.CallableStatement cs =
        appM.getDBTransaction().createCallableStatement(stmt, 1);
    try {


        cs.setInt(1,poc);
        cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
       // cs.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
        //cs.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);
        //cs.registerOutParameter(5, oracle.jdbc.OracleTypes.VARCHAR);


        cs.execute();
        message = cs.getString(2);
       // message1 = cs.getString(3);
        //message2 = cs.getString(4);
        //message3 = cs.getString(5);
        cs.close();


    } catch (SQLException e) {
        e.printStackTrace();

    }

    

    //AdfFacesContext.getCurrentInstance().addPartialTarget(bpoTable);
    if (message != null) {
        showMessage(message, "info");
    }
    
    


    return null;
    }

    
    
    public void showMessage(String message, String severity) {

    FacesMessage fm = new FacesMessage(message);

    if (severity.equals("info")) {
    fm.setSeverity(FacesMessage.SEVERITY_INFO);
    System.out.println("inside message");
    } else if (severity.equals("warn")) {
    fm.setSeverity(FacesMessage.SEVERITY_WARN);
    } else if (severity.equals("error")) {
    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
    }

    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, fm);

    }


    public void CreateDryLine(ActionEvent actionEvent) {
        // Add event code here...
        
        ViewObject vo = appM.getXX_OM_POC_D2_TVO1();
        
                Row row = vo.createRow();
                vo.insertRow(row);
        //vo.getCurrentRow().setAttribute("ProcessId",1);
                row.setNewRowState(Row.STATUS_INITIALIZED);
        vo.getCurrentRow().setAttribute("ProcessId",1);
        
    }

    public void CreateWet(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject vo = appM.getXX_OM_POC_D2_TVO2_1();
        
                Row row = vo.createRow();
                vo.insertRow(row);
        //vo.getCurrentRow().setAttribute("ProcessId",1);
                row.setNewRowState(Row.STATUS_INITIALIZED);
        vo.getCurrentRow().setAttribute("ProcessId",2);
    }
        
        public void febric_costOf_value_cal(ValueChangeEvent valueChangeEvent) {
            
            ViewObject fabricView = appM.getXX_OM_POC_D1_TVO1();


            double total = 0.00;
            double actUnitpc = 0.00;
            double val = 0.00;
            double buffer = 0.00;


            try {
                actUnitpc =
                        Double.parseDouble((getActualUnitPrice().getValue().toString()));
                System.out.println("........................actUnitpc for trim= " +
                                   actUnitpc);
                //            double tes=Double.parseDouble((fabricView.getCurrentRow().getAttribute("ActualUnitPrice").toString()));
                //            double te=Double.parseDouble((fabricView.getCurrentRow().getAttribute("PrefixDesc").toString()));
                //            System.out.println("......................current..actUnitpc for trim= "+tes);
                //            System.out.println("........................prefix for trim= "+te);
            } catch (Exception e) {
                actUnitpc = 0.00;
            }

            try {
                val =
            Double.parseDouble((this.getConsPerPcs().getValue().toString()));
                // double test=Double.parseDouble((fabricView.getCurrentRow().getAttribute("ConsPerPcs").toString()));
                System.out.println("........................actCons for trim= " +
                                   val);
            } catch (Exception e) {
                val = 0.00;
            }


            try {
                buffer =
                        Double.parseDouble((this.getBuffer().getValue().toString()));
            } catch (Exception e) {
                buffer = 0.00;
            }
            // AdfFacesContext.getCurrentInstance().addPartialTarget(bpoQt_value);
            //System.out.println(" Total BPO Qty------------>"+getBPOTotalQty());


            total = (val * actUnitpc) + buffer;
            // TrimCost=( trimsFinance_with_percentage/100)+total;
            fabricView.getCurrentRow().setAttribute("CostPerPcs", total);
            fabricView.getCurrentRow().setAttribute("FinalCostPerPcs", total);


            AdfFacesContext.getCurrentInstance().addPartialTarget(costPerPcs);


        }
        
        
        
        
    public void refreshQueryKeepingCurrentRow(ViewObject viewObject )  {
         
         
          Row currentRow;
          Key currentRowKey;
          
          // added on 7.jan.18 to handle exception if view object has no current row
         try{
            currentRow = viewObject.getCurrentRow();
            currentRowKey = currentRow.getKey();
         }
         catch(Exception e){
             return;
         }     
        int rangePosOfCurrentRow = viewObject.getRangeIndexOf(currentRow);
        int rangeStartBeforeQuery = viewObject.getRangeStart();
        viewObject.executeQuery();
        viewObject.setRangeStart(rangeStartBeforeQuery);
        /*
         * In 10.1.2, there is this convenience method we could use
         * instead of the remaining lines of code
         *
         *  findAndSetCurrentRowByKey(currentRowKey,rangePosOfCurrentRow);
         *  
         */
        
          
        Row[] rows = viewObject.findByKey(currentRowKey, 1);
        if (rows != null && rows.length == 1)
        {
           viewObject.scrollRangeTo(rows[0], rangePosOfCurrentRow);
           viewObject.setCurrentRowAtRangeIndex(viewObject.getRangeIndexOf(rows[0]));
        }
        
                
      }
        
}
