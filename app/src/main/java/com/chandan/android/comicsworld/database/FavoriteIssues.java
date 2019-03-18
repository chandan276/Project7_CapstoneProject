package com.chandan.android.comicsworld.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteissues")
public class FavoriteIssues {

    @PrimaryKey(autoGenerate = false)
    private int issueId;
    private String issuesName;
    private String issuesAddedDate;
    private String imagesUrlStr;
    private String issuesNumber;

    public FavoriteIssues(int issueId, String issuesName, String issuesAddedDate, String imagesUrlStr, String issuesNumber) {
        this.issueId = issueId;
        this.issuesName = issuesName;
        this.issuesAddedDate = issuesAddedDate;
        this.imagesUrlStr = imagesUrlStr;
        this.issuesNumber = issuesNumber;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getIssuesName() {
        return issuesName;
    }

    public void setIssuesName(String issuesName) {
        this.issuesName = issuesName;
    }

    public String getIssuesAddedDate() {
        return issuesAddedDate;
    }

    public void setIssuesAddedDate(String issuesAddedDate) {
        this.issuesAddedDate = issuesAddedDate;
    }

    public String getImagesUrlStr() {
        return imagesUrlStr;
    }

    public void setImagesUrlStr(String imagesUrlStr) {
        this.imagesUrlStr = imagesUrlStr;
    }

    public String getIssuesNumber() {
        return issuesNumber;
    }

    public void setIssuesNumber(String issuesNumber) {
        this.issuesNumber = issuesNumber;
    }
}
