package model.entity;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 23 12:19:24 BDT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XX_OM_POC_D2_TEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Id {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getId();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setId((Number)value);
            }
        }
        ,
        DetailId {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getDetailId();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setDetailId((Number)value);
            }
        }
        ,
        FobId {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getFobId();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setFobId((Number)value);
            }
        }
        ,
        ProcessId {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getProcessId();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setProcessId((Number)value);
            }
        }
        ,
        ProcessName {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getProcessName();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setProcessName((String)value);
            }
        }
        ,
        CostAmount {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getCostAmount();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setCostAmount((Number)value);
            }
        }
        ,
        Profit {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getProfit();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setProfit((Number)value);
            }
        }
        ,
        ManualPrice {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getManualPrice();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setManualPrice((Number)value);
            }
        }
        ,
        Total {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getTotal();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setTotal((Number)value);
            }
        }
        ,
        CostUom {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getCostUom();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setCostUom((String)value);
            }
        }
        ,
        CreatedBy {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setCreatedBy((Number)value);
            }
        }
        ,
        CreationDate {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getCreationDate();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setCreationDate((Date)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setLastUpdatedBy((Number)value);
            }
        }
        ,
        LastUpdatedDate {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getLastUpdatedDate();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setLastUpdatedDate((Date)value);
            }
        }
        ,
        XX_OM_POC_L_TEO {
            public Object get(XX_OM_POC_D2_TEOImpl obj) {
                return obj.getXX_OM_POC_L_TEO();
            }

            public void put(XX_OM_POC_D2_TEOImpl obj, Object value) {
                obj.setXX_OM_POC_L_TEO((XX_OM_POC_L_TEOImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(XX_OM_POC_D2_TEOImpl object);

        public abstract void put(XX_OM_POC_D2_TEOImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int ID = AttributesEnum.Id.index();
    public static final int DETAILID = AttributesEnum.DetailId.index();
    public static final int FOBID = AttributesEnum.FobId.index();
    public static final int PROCESSID = AttributesEnum.ProcessId.index();
    public static final int PROCESSNAME = AttributesEnum.ProcessName.index();
    public static final int COSTAMOUNT = AttributesEnum.CostAmount.index();
    public static final int PROFIT = AttributesEnum.Profit.index();
    public static final int MANUALPRICE = AttributesEnum.ManualPrice.index();
    public static final int TOTAL = AttributesEnum.Total.index();
    public static final int COSTUOM = AttributesEnum.CostUom.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int LASTUPDATEDDATE = AttributesEnum.LastUpdatedDate.index();
    public static final int XX_OM_POC_L_TEO = AttributesEnum.XX_OM_POC_L_TEO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XX_OM_POC_D2_TEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("model.entity.XX_OM_POC_D2_TEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for Id, using the alias name Id.
     * @return the Id
     */
    public Number getId() {
        return (Number)getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Id.
     * @param value value to set the Id
     */
    public void setId(Number value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for DetailId, using the alias name DetailId.
     * @return the DetailId
     */
    public Number getDetailId() {
        return (Number)getAttributeInternal(DETAILID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DetailId.
     * @param value value to set the DetailId
     */
    public void setDetailId(Number value) {
        setAttributeInternal(DETAILID, value);
    }

    /**
     * Gets the attribute value for FobId, using the alias name FobId.
     * @return the FobId
     */
    public Number getFobId() {
        return (Number)getAttributeInternal(FOBID);
    }

    /**
     * Sets <code>value</code> as the attribute value for FobId.
     * @param value value to set the FobId
     */
    public void setFobId(Number value) {
        setAttributeInternal(FOBID, value);
    }

    /**
     * Gets the attribute value for ProcessId, using the alias name ProcessId.
     * @return the ProcessId
     */
    public Number getProcessId() {
        return (Number)getAttributeInternal(PROCESSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProcessId.
     * @param value value to set the ProcessId
     */
    public void setProcessId(Number value) {
        setAttributeInternal(PROCESSID, value);
    }

    /**
     * Gets the attribute value for ProcessName, using the alias name ProcessName.
     * @return the ProcessName
     */
    public String getProcessName() {
        return (String)getAttributeInternal(PROCESSNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for ProcessName.
     * @param value value to set the ProcessName
     */
    public void setProcessName(String value) {
        setAttributeInternal(PROCESSNAME, value);
    }

    /**
     * Gets the attribute value for CostAmount, using the alias name CostAmount.
     * @return the CostAmount
     */
    public Number getCostAmount() {
        return (Number)getAttributeInternal(COSTAMOUNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for CostAmount.
     * @param value value to set the CostAmount
     */
    public void setCostAmount(Number value) {
        setAttributeInternal(COSTAMOUNT, value);
    }

    /**
     * Gets the attribute value for Profit, using the alias name Profit.
     * @return the Profit
     */
    public Number getProfit() {
        return (Number)getAttributeInternal(PROFIT);
    }

    /**
     * Sets <code>value</code> as the attribute value for Profit.
     * @param value value to set the Profit
     */
    public void setProfit(Number value) {
        setAttributeInternal(PROFIT, value);
    }

    /**
     * Gets the attribute value for ManualPrice, using the alias name ManualPrice.
     * @return the ManualPrice
     */
    public Number getManualPrice() {
        return (Number)getAttributeInternal(MANUALPRICE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ManualPrice.
     * @param value value to set the ManualPrice
     */
    public void setManualPrice(Number value) {
        setAttributeInternal(MANUALPRICE, value);
    }

    /**
     * Gets the attribute value for Total, using the alias name Total.
     * @return the Total
     */
    public Number getTotal() {
        return (Number)getAttributeInternal(TOTAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for Total.
     * @param value value to set the Total
     */
    public void setTotal(Number value) {
        setAttributeInternal(TOTAL, value);
    }

    /**
     * Gets the attribute value for CostUom, using the alias name CostUom.
     * @return the CostUom
     */
    public String getCostUom() {
        return (String)getAttributeInternal(COSTUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for CostUom.
     * @param value value to set the CostUom
     */
    public void setCostUom(String value) {
        setAttributeInternal(COSTUOM, value);
    }

    /**
     * Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     * @return the CreatedBy
     */
    public Number getCreatedBy() {
        return (Number)getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedBy.
     * @param value value to set the CreatedBy
     */
    public void setCreatedBy(Number value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CreationDate, using the alias name CreationDate.
     * @return the CreationDate
     */
    public Date getCreationDate() {
        return (Date)getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreationDate.
     * @param value value to set the CreationDate
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for LastUpdatedBy, using the alias name LastUpdatedBy.
     * @return the LastUpdatedBy
     */
    public Number getLastUpdatedBy() {
        return (Number)getAttributeInternal(LASTUPDATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdatedBy.
     * @param value value to set the LastUpdatedBy
     */
    public void setLastUpdatedBy(Number value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for LastUpdatedDate, using the alias name LastUpdatedDate.
     * @return the LastUpdatedDate
     */
    public Date getLastUpdatedDate() {
        return (Date)getAttributeInternal(LASTUPDATEDDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdatedDate.
     * @param value value to set the LastUpdatedDate
     */
    public void setLastUpdatedDate(Date value) {
        setAttributeInternal(LASTUPDATEDDATE, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }

    /**
     * @return the associated entity XX_OM_POC_L_TEOImpl.
     */
    public XX_OM_POC_L_TEOImpl getXX_OM_POC_L_TEO() {
        return (XX_OM_POC_L_TEOImpl)getAttributeInternal(XX_OM_POC_L_TEO);
    }

    /**
     * Sets <code>value</code> as the associated entity XX_OM_POC_L_TEOImpl.
     */
    public void setXX_OM_POC_L_TEO(XX_OM_POC_L_TEOImpl value) {
        setAttributeInternal(XX_OM_POC_L_TEO, value);
    }


    /**
     * @param id key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number id) {
        return new Key(new Object[]{id});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        super.create(attributeList);
        oracle.jbo.server.SequenceImpl s =new oracle.jbo.server.SequenceImpl("XX_OM_POC_D2_SEQ",getDBTransaction());
        oracle.jbo.domain.Number sVal = s.getSequenceNumber();
        setId(sVal);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}
