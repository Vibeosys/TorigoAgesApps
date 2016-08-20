package com.vibeosys.paymybill.interfaces;

import com.vibeosys.paymybill.data.FriendsDTO;

import java.util.List;

/**
 * Created by akshay on 20-08-2016.
 */
public interface SelectedFriendCriteria {

    public List<FriendsDTO> meetCriteria(List<FriendsDTO> friendsDTOList);
}
