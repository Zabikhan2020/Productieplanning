package rideellio.com.productieplanning.model;

public class Bestelling {
    private String productType;
    private String beloofdeDagVanVerzending;

    public Bestelling(String productType, String beloofdeDagVanVerzending) {
        this.productType = productType;
        this.beloofdeDagVanVerzending = beloofdeDagVanVerzending;
    }

    // Getters en setters


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBeloofdeDagVanVerzending() {
        return beloofdeDagVanVerzending;
    }

    public void setBeloofdeDagVanVerzending(String beloofdeDagVanVerzending) {
        this.beloofdeDagVanVerzending = beloofdeDagVanVerzending;
    }

    @Override
    public String toString() {
        return "Bestelling{" +
                "productType='" + productType + '\'' +
                ", beloofdeDagVanVerzending='" + beloofdeDagVanVerzending + '\'' +
                '}';
    }
}
