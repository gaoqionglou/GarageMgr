package com.wyr.garage.data.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "user")
public class LoggedInUser {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int userId = 0;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "personal_id")
    private String personalId;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "RFI_number")
    private String RFINumber;

    @ColumnInfo(name = "monkey")
    private int monkey;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRFINumber() {
        return RFINumber;
    }

    public void setRFINumber(String RFINumber) {
        this.RFINumber = RFINumber;
    }

    public int getMonkey() {
        return monkey;
    }

    public void setMonkey(int monkey) {
        this.monkey = monkey;
    }

    public boolean hasEmptyItem(boolean considerPwd) {
        boolean empty = TextUtils.isEmpty(displayName)
                || TextUtils.isEmpty(gender)
                || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(personalId)
                || TextUtils.isEmpty(phoneNumber);
        if (considerPwd) {
            empty = empty || TextUtils.isEmpty(password);
        }
        return empty;
    }


    @NonNull
    @Override
    public String toString() {
        return displayName + ", " + password;
    }
}
