package net.siji.dao;

import net.siji.model.Customer;

import org.json.JSONObject;

public class CustomerUtils {
    private Customer customer;

    public Customer createFromJSONObject(JSONObject jsonObject) {
        customer = new Customer();
        return customer;
    }
}
