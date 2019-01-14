package au.com.liamgooch.tasker.data;

public class UserAccountData {
    private String uid;
    private Account_Type_Enum account_type;

    public UserAccountData(String uid){
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Account_Type_Enum getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Account_Type_Enum account_type) {
        this.account_type = account_type;
    }
}
