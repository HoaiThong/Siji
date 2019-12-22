package net.siji.model;

import android.util.Log;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Locale;

public class Customer implements Serializable{
    //Gson serializable

    private Wallet wallet;
//    @Expose
    private int id;
//    @Expose
    private String idFacebook="";
//    @Expose
    private String idGoogle="";
//    @Expose
    private String idAccoutKit="";
//    @Expose
    private String nameFaceBook="";
//    @Expose
    private String nameGoogle="";
//    @Expose
    private String firstName="";
//    @Expose
    private String secondName="";
//    @Expose
    private String linkFacebook="";
//    @Expose
    private String phone="";
//    @Expose
    private String phoneFacebook="";
//    @Expose
    private String emailFacebook="";
//    @Expose
    private String emailGoogle="";
//    @Expose
    private String emailAccoutKit="";
//    @Expose
    private String address="";
//    @Expose
    private String sex="";
//    @Expose
    private String dateOfBirth="";
//    @Expose
    private String idTokenFcm="";
//    @Expose
    private String job="";

    private String locale;

    private String iconUrl="http://siji.asia/thumb/icon.jpg";
//    @Expose(serialize = false)
    private Timestamp createAt;
//    @Expose(serialize = false)//=@Expose(serialize = false, deserialize = true)
    private Timestamp updateAt;

    public Customer() {
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getIdAccoutKit() {
        return idAccoutKit;
    }

    public void setIdAccoutKit(String idAccoutKit) {
        this.idAccoutKit = idAccoutKit;
    }

    public String getEmailAccoutKit() {
        return emailAccoutKit;
    }

    public void setEmailAccoutKit(String emailAccoutKit) {
        this.emailAccoutKit = emailAccoutKit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getNameFaceBook() {
        return nameFaceBook;
    }

    public void setNameFaceBook(String nameFaceBook) {
        this.nameFaceBook = nameFaceBook;
    }

    public String getNameGoogle() {
        return nameGoogle;
    }

    public void setNameGoogle(String nameGoogle) {
        this.nameGoogle = nameGoogle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneFacebook() {
        return phoneFacebook;
    }

    public void setPhoneFacebook(String phoneFacebook) {
        this.phoneFacebook = phoneFacebook;
    }

    public String getEmailFacebook() {
        return emailFacebook;
    }

    public void setEmailFacebook(String emailFacebook) {
        this.emailFacebook = emailFacebook;
    }

    public String getEmailGoogle() {
        return emailGoogle;
    }

    public void setEmailGoogle(String emailGoogle) {
        this.emailGoogle = emailGoogle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdTokenFcm() {
        return idTokenFcm;
    }

    public void setIdTokenFcm(String idTokenFcm) {
        this.idTokenFcm = idTokenFcm;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocale() {

        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String toJSONNoId(){
        String json="{\"address\":\""+address+"\"," +
                "\"dateOfBirth\":\""+dateOfBirth+"\"," +
                "\"emailAccoutKit\":\""+emailAccoutKit+"\"," +
                "\"emailFacebook\":\""+emailFacebook+"\"," +
                "\"emailGoogle\":\""+emailGoogle+"\"," +
                "\"firstName\":\""+firstName+"\"," +
                "\"idAccoutKit\":\""+idAccoutKit+"\"," +
                "\"idFacebook\":\""+idFacebook+"\"," +
                "\"idGoogle\":\""+idGoogle+"\"," +
                "\"idTokenFcm\":\""+idTokenFcm+"\"," +
                "\"iconUrl\":\""+iconUrl+"\"," +
                "\"job\":\""+job+"\"," +
                "\"locale\":\""+locale+"\"," +
                "\"linkFacebook\":\""+linkFacebook+"\"," +
                "\"nameFaceBook\":\""+nameFaceBook+"\"," +
                "\"nameGoogle\":\""+nameGoogle+"\"," +
                "\"phone\":\""+phone+"\"," +
                "\"phoneFacebook\":\""+phoneFacebook+"\"," +
                "\"secondName\":\""+secondName+"\"," +
                "\"sex\":\""+sex+"\"}";
        return json;
    }
}
