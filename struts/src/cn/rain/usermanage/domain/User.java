package cn.rain.usermanage.domain;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Rain on 2016-12-17.
 */
public class User  implements Serializable{
    private Integer  userID;
    private String userName;
    private String logonName;
    private String logonPwd;
    private String sex;
    private String birthday;
    private String education;
    private String telephone;
    private String interest;
    private String path;
    private String filename;
    private String remark;
    private File upload;
    private String uploadFileName;
    private  String uploadContentType;

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogonName() {
        return logonName;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }

    public String getLogonPwd() {
        return logonPwd;
    }

    public void setLogonPwd(String logonPwd) {
        this.logonPwd = logonPwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", logonName='" + logonName + '\'' +
                ", logonPwd='" + logonPwd + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", education='" + education + '\'' +
                ", telephone='" + telephone + '\'' +
                ", interest='" + interest + '\'' +
                ", path='" + path + '\'' +
                ", filename='" + filename + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public User() {
        super();
    }
}
