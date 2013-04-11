package jbolt.android.webservice.dto;

/**
 * Component:
 * Description:
 * User: feng.xie
 * Date: 18/06/11
 */
public class ServiceResponse {

    private String resultJson;
    private String errorDesc;
    private String resultType;
    private String resultGenericType;

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        if (errorDesc != null) {
            this.errorDesc = errorDesc;
        } else {
            this.errorDesc = NullPointerException.class.getName();
        }
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultGenericType() {
        return resultGenericType;
    }

    public void setResultGenericType(String resultGenericType) {
        this.resultGenericType = resultGenericType;
    }
}
