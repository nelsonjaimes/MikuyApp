package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

@SuppressWarnings("ALL")
public class ReservationRequestEntity {

    private final String emailuser;
    private final float amount;
    private final List<Plate> platesList;

    public ReservationRequestEntity(String emailuser, float amount, List<Plate> platesList) {
        this.emailuser = emailuser;
        this.amount = amount;
        this.platesList = platesList;
    }
    /*
    * "emailuser":"nelsonjaimesgonzales@gmail.com",
	"amount":20.5,
	"platesList":[
		{
		"code":"A001",
		"acount":1
		},{
		  "code":"A002",
		  "acount" :3
		}
		]
    * */
}
