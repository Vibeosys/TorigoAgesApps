package com.vibeosys.paymybill.data;

import com.vibeosys.paymybill.interfaces.SelectedFriendCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 20-08-2016.
 */
public class SelectedFriends implements SelectedFriendCriteria, Serializable {
    @Override
    public List<FriendsDTO> meetCriteria(List<FriendsDTO> friendsDTOList) {
        List<FriendsDTO> selectedFriends = new ArrayList<>();
        for (FriendsDTO friend : friendsDTOList) {
            if (friend.isFlagOwe()) {
                selectedFriends.add(friend);
            }
        }
        return selectedFriends;
    }


}
