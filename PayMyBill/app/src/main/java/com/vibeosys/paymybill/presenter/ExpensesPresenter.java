package com.vibeosys.paymybill.presenter;

import com.vibeosys.paymybill.activities.ExpensesActivity;
import com.vibeosys.paymybill.data.BillDetailsDTO;
import com.vibeosys.paymybill.database.DbRepository;

/**
 * Created by akshay on 11-11-2016.
 */
public class ExpensesPresenter {

    ExpensesActivity expensesActivity;
    DbRepository dbRepository;

    public ExpensesPresenter(ExpensesActivity expensesActivity, DbRepository dbRepository) {
        this.expensesActivity = expensesActivity;
        this.dbRepository = dbRepository;
    }

    public int insertMyExpances(BillDetailsDTO newBill) {
        return dbRepository.insertMyExpances(newBill);
    }

    public void deleteBillAndTransactions(BillDetailsDTO newBill) {
        dbRepository.deleteBillAndTransactions(newBill);
    }
}
