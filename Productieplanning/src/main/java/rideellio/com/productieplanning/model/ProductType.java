package rideellio.com.productieplanning.model;

public class ProductType {
    private String type;
    private int productieTijd;

    public ProductType() {
        this.type = "";
        this.productieTijd = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductieTijd() {
        return productieTijd;
    }

    public void setProductieTijd(int productieTijd) {
        this.productieTijd = productieTijd;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "type='" + type + '\'' +
                ", productieTijd=" + productieTijd +
                '}';
    }
}
