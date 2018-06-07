package ph.newsim.mobile.newsim.model;

public class Rank {

    private int mId;
    private String mRankCode;
    private String mRankDescription;
    private String mRankType;
    private String mRankLevel;

    public Rank(int id, String rankCode, String rankDescription, String rankType, String rankLevel) {
        mId = id;
        mRankCode = rankCode;
        mRankDescription = rankDescription;
        mRankType = rankType;
        mRankLevel = rankLevel;
    }

    public Rank(String rankCode) {
        mRankCode = rankCode;
    }

    public int getId() {
        return mId;
    }

    public String getRankCode() {
        return mRankCode;
    }

    public String getRankDescription() {
        return mRankDescription;
    }

    public String getRankType() {
        return mRankType;
    }

    public String getRankLevel() {
        return mRankLevel;
    }
}
