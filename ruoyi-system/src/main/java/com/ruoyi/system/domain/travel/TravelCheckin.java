package com.ruoyi.system.domain.travel;

import java.util.Date;
import java.util.List;

/**
 * 打卡记录对象 travel_checkin
 * 
 * @author 旅途
 */
public class TravelCheckin extends com.ruoyi.common.core.domain.BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 打卡ID */
    private Long checkinId;

    /** 用户ID */
    private Long userId;

    /** 景点ID */
    private Long spotId;

    /** 城市ID */
    private Long cityId;

    /** 打卡内容 */
    private String content;

    /** 图片URLs(JSON数组) */
    private String images;

    /** 打卡时纬度 */
    private String locationLat;

    /** 打卡时经度 */
    private String locationLng;

    /** 是否公开 */
    private String isPublic;

    /** 点赞数 */
    private Integer likeCount;

    /** 评论数 */
    private Integer commentCount;

    /** 状态 */
    private String status;

    /** 删除标志 */
    private String delFlag;

    /** 点赞状态(当前用户是否已点赞) */
    private Boolean isLiked;

    /** 用户信息(非数据库字段) */
    private String nickname;
    private String avatar;
    
    /** 景点信息 */
    private String spotName;
    private String spotCover;
    private String cityName;
    
    /** 图片列表(解析后的) */
    private List<String> imageList;

    /** 点赞列表 */
    private List<TravelCheckinLike> likeList;

    /** 评论列表 */
    private List<TravelCheckinComment> commentList;

    public Long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotCover() {
        return spotCover;
    }

    public void setSpotCover(String spotCover) {
        this.spotCover = spotCover;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<TravelCheckinLike> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<TravelCheckinLike> likeList) {
        this.likeList = likeList;
    }

    public List<TravelCheckinComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<TravelCheckinComment> commentList) {
        this.commentList = commentList;
    }
}
