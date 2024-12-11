package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.Period;

@EqualsAndHashCode
public class PuppyAgeInfo {

    private LocalDate birthdate;

    public PuppyAgeInfo(LocalDate birthdate) {
        if (birthdate == null ) {
            throw new PeautyException(PeautyResponseCode.WRONG_BIRTHDATE);
        }
        else if(birthdate.isAfter(LocalDate.now())){
            throw new PeautyException(PeautyResponseCode.INVALID_BIRTHDATE);
        }
        this.birthdate = birthdate;
    }

    //년 = O살
    public int getYears() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    // O년 OO개월
    public String getFormattedAge() {
        Period period = Period.between(birthdate, LocalDate.now());
        int years = period.getYears();
        int months = period.getMonths();

        StringBuilder formattedAge = new StringBuilder();
        if (years > 0) {
            formattedAge.append(years).append("년 ");
        }
        if (months > 0) {
            formattedAge.append(months).append("개월");
        }

        return formattedAge.length() > 0 ? formattedAge.toString().trim() : "0개월";
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void updateBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}