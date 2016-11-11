package com.vibeosys.paymybill.presenter;

import android.app.Activity;

import com.vibeosys.paymybill.activities.AddFriendActivity;
import com.vibeosys.paymybill.data.databasedto.FriendDbDTO;
import com.vibeosys.paymybill.database.DbRepository;

/**
 * Created by akshay on 09-11-2016.
 */
public class AddFriendPresenter {

    AddFriendActivity addFriendActivity;
    DbRepository dbRepository;

    public AddFriendPresenter(AddFriendActivity addFriendActivity, DbRepository dbRepository) {
        this.addFriendActivity = addFriendActivity;
        this.dbRepository = dbRepository;
    }


    public int insertFriend(FriendDbDTO friendDbDTO) {
        return dbRepository.insertFriend(friendDbDTO);
    }
}
