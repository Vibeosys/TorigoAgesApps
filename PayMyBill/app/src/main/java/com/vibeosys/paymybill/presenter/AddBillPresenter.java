package com.vibeosys.paymybill.presenter;

import com.vibeosys.paymybill.activities.AddBillActivity;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.database.DbRepository;

import java.util.ArrayList;

/**
 * Created by akshay on 09-11-2016.
 */
public class AddBillPresenter {

    AddBillActivity addBillActivity;
    DbRepository dbRepository;

    public AddBillPresenter(AddBillActivity addBillActivity, DbRepository dbRepository) {
        this.addBillActivity = addBillActivity;
        this.dbRepository = dbRepository;
    }


    public ArrayList<FriendsDTO> getFriendList(String userFriendId) {
        return dbRepository.getFriendList(userFriendId);
    }
}
