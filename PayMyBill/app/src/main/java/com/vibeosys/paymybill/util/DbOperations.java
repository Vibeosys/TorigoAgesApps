package com.vibeosys.paymybill.util;

import android.util.Log;


import com.vibeosys.paymybill.database.DbRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 23-01-2016.
 */
public class DbOperations {

    private DbRepository dbRepository;

    public DbOperations(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

  /*  public boolean addOrUpdateBills(ArrayList<String> jsonInsertList, ArrayList<String> updateJsonList) {
        List<BillDbDTO> billInserts = BillDbDTO.deserializeBill(jsonInsertList);
        List<BillDbDTO> billUpdates = BillDbDTO.deserializeBill(updateJsonList);

        boolean isInserted = dbRepository.insertBills(billInserts);
        boolean isUpdated = dbRepository.updateBills(billUpdates);
        return isInserted & isUpdated;
    }*/

}
