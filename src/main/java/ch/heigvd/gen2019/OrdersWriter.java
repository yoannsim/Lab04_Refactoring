package ch.heigvd.gen2019;

public class OrdersWriter {
    private Orders orders;
    private StringBuffer sb = new StringBuffer();
    public OrdersWriter(Orders orders) {
        this.orders = orders;
    }

    public String getContents() {
        sb.append("{");
        deleteSB("orders", orders);
        return sb.append("]}").toString();
    }

    private void addOrder(Order order) {
        sb.append("{");
        addChamp("id", order.getOrderId());
        deleteSB("products", order);
        sb.append("]}, ");
    }

    private void addTableau(String name, Object obj) {
        int max;

        max =((JSonTab) obj).getElementCount();
        sb.append("\"" + name + "\": [");
        for (int j = 0; j < max; j++) {
            Object tmp = ((JSonTab)obj).getElement(j);
            if (tmp instanceof Order) {
                addOrder((Order) tmp);
            } else {
                addProduct((Product) tmp);
            }
        }
    }

    private void addProduct(Product product) {
        sb.append("{");
        addChamp("code", product.getCode());
        addChamp("color", getColorFor(product));

        if (product.getSize() != Product.SIZE_NOT_APPLICABLE) {
            addChamp("size", getSizeFor(product));
        }

        addChamp("price", product.getPrice());
        addChamp("currency", product.getCurrency());
        sb.append("\"}, ");
    }

    private void addChamp(String nomChamp, Object valeur) {
        sb.append("\"" + nomChamp + "\": ");
        stringTreatment(valeur);
        sb.append(valeur);
        if (!nomChamp.equals("currency")) {
            stringTreatment(valeur);
            sb.append(", ");
        }
    }

    private void deleteSB(String s, Object obj) {
        addTableau(s, obj);
        if (((JSonTab)obj).getElementCount() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
    }

    private void stringTreatment(Object valeur) {
        if (valeur instanceof String) {
            sb.append("\"");
        }
    }

    private String getSizeFor(Product product) {
        switch (product.getSize()) {
            case 1:
                return "XS";
            case 2:
                return "S";
            case 3:
                return "M";
            case 4:
                return "L";
            case 5:
                return "XL";
            case 6:
                return "XXL";
            default:
                return "Invalid Size";
        }
    }

    private String getColorFor(Product product) {
        switch (product.getColor()) {
            case 1:
                return "blue";
            case 2:
                return "red";
            case 3:
                return "yellow";
            default:
                return "no color";
        }
    }

}