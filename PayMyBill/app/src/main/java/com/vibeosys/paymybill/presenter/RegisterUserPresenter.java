package com.vibeosys.paymybill.presenter;


import com.vibeosys.paymybill.activities.UserRegisterActivity;
import com.vibeosys.paymybill.data.databasedto.UserRegisterDbDTO;
import com.vibeosys.paymybill.database.DbRepository;

/**
 * Created by shrinivas on 09-11-2016.
 */
public class RegisterUserPresenter {
    DbRepository dbRepository;
    UserRegisterActivity mContext;
    public RegisterUserPresenter(UserRegisterActivity mContext, DbRepository dbRepository)
    {
        this.mContext=mContext;
        this.dbRepository=dbRepository;
    }
    public int userRegistration(UserRegisterDbDTO UserRegisterDbDTO)
    {
        int returnVal=dbRepository.userRegister(UserRegisterDbDTO);
        return returnVal;
    }

}
