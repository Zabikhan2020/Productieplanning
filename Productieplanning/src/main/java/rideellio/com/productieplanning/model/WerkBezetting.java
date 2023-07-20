package rideellio.com.productieplanning.model;

import java.util.List;

public class WerkBezetting {
    private String datum;
    private List<String> productTypes;

    public WerkBezetting(String datum, List<String> productTypes) {
        this.datum = datum;
        this.productTypes = productTypes;
    }

    // Getters en setters

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public List<String> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<String> productTypes) {
        this.productTypes = productTypes;
    }

    @Override
    public String toString() {
        return "WerkBezetting{" +
                "datum='" + datum + '\'' +
                ", productTypes=" + productTypes +
                '}';
    }
}
