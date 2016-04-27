package com.vibeosys.lawyerdiary.Adapter;

/**
 * Created by shrinivas on 26-04-2016.
 */
public class ClientData {
    String custmerName;
    String typeOfLigitation;
    String categoryOfLegitation;
    int icon;
    public ClientData(String custmerName ,String typeOfLigitation ,String categoryOfLegitation,int icon)
    {
        this.custmerName = custmerName;
        this.typeOfLigitation = typeOfLigitation;
        this.categoryOfLegitation = categoryOfLegitation;
        this.icon = icon;
    }
    public String getCustmerName() {
        return custmerName;
    }

    public void setCustmerName(String custmerName) {
        this.custmerName = custmerName;
    }

    public String getTypeOfLigitation() {
        return typeOfLigitation;
    }

    public void setTypeOfLigitation(String typeOfLigitation) {
        this.typeOfLigitation = typeOfLigitation;
    }

    public String getCategoryOfLegitation() {
        return categoryOfLegitation;
    }

    public void setCategoryOfLegitation(String categoryOfLegitation) {
        this.categoryOfLegitation = categoryOfLegitation;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


}
