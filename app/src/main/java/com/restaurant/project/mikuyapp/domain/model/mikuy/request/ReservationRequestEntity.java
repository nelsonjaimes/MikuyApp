package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public class ReservationRequestEntity {

    private String emailuser;
    private float amount;
    private List<Plate> platesList;

    public ReservationRequestEntity(String emailuser, float amount, List<Plate> platesList) {
        this.emailuser = emailuser;
        this.amount = amount;
        this.platesList = platesList;
    }

    public float getAmount() {
        return amount;
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
