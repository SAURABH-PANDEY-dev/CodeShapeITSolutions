package app.dao;

import app.model.Sale;

import java.util.ArrayList;
import java.util.List;

public class SalesDAO {
    private List<Sale> salesList = new ArrayList<>();

    public void recordSale(Sale sale) {
        salesList.add(sale);
        System.out.println("✅ Sale recorded: " + sale.getProductName());
    }

    public List<Sale> getAllSales() {
        return salesList;
    }
}
