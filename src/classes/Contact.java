package classes;

public class Contact
{
    private String ContactID;

    private String contactName;
    private String phoneNumberList;
    private String countryCode;
    private String tag;
    private String emailList;
    private String userID;

    public String getContactID()
    {
        return ContactID;
    }

    public void setContactID(String contactID)
    {
        ContactID = contactID;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getPhoneNumberList()
    {
        return phoneNumberList;
    }

    public void setPhoneNumberList(String phoneNumberList)
    {
        this.phoneNumberList = phoneNumberList;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getEmailList()
    {
        return emailList;
    }

    public void setEmailList(String emailList)
    {
        this.emailList = emailList;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
