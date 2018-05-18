package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public class ReservationRequestEntity {

    private String emailuser;
    private float amount;
    private List<Plate> platesList;

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public List<Plate> getPlatesList() {
        return platesList;
    }

    public void setPlatesList(List<Plate> platesList) {
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
