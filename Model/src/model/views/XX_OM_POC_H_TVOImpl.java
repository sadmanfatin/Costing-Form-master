package model.views;

import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 03 10:03:52 BDT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XX_OM_POC_H_TVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public XX_OM_POC_H_TVOImpl() {
    }


    /**
     * Returns the bind variable value for p_userId.
     * @return bind variable value for p_userId
     */
    public String getp_userId() {
        return (String)getNamedWhereClauseParam("p_userId");
    }

    /**
     * Sets <code>value</code> for bind variable p_userId.
     * @param value value to bind as p_userId
     */
    public void setp_userId(String value) {
        setNamedWhereClauseParam("p_userId", value);
    }


}