package ph.newsim.mobile.newsim.database;

import java.util.ArrayList;
import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Branch;

public class BranchesDataSource {

    public static final int BRANCH_BACOLOD = 1;
    public static final int BRANCH_CEBU = 2;
    public static final int BRANCH_DAVAO = 3;
    public static final int BRANCH_ILOILO = 4;
    public static final int BRANCH_MAKATI = 5;

    private String[] mBranchName = {
            "NEWSIM EDISON", "NEWSIM MARCONI", "NEWSIM TRAINING ACADEMY", "NEWSIM ILOILO",
            "NEWSIM BACOLOD", "NEWSIM CEBU", "NEWSIM DAVAO", "NEWSIM CEBU Annex"
    };

    private String[] mAddress = {
            "5F 2053 Bldg. Edison St. Brgy. San Isidro, Makati City 1234",
            "2323 NEWSIM Bldg. Marconi St. Brgy. San Isidro, Makati City 1234",
            "Monte Vista Beach Resort Brgy. Bignay 2 Sariaya Quezon 4322",
            "2F 402 Arguelles Bldg. E. Lopez St. Jaro, Iloilo City 5000",
            "3F LKC Bldg. Locsin-Burgos St. Brgy. 11 Bacolod City 6100",
            "6F 731 Bldg. Escario St. Brgy. Capitol Site, Cebu City 6000",
            "3F Court View Hotel, Pink Waltes Bldg. Quimpo Blvd. Matina Davao City 8000",
            "3F Capitol Square, Escario St. Brgy. Capitol Site, Cebu City 6000"
    };

    private String[] mTelephoneNo = {
            "(02)8432864", "(02)8882764", "(02)2451195 or 8884544", "(033)3203776",
            "(034)4350701", "(032)5203141 or 5203747", "(082)2850224 or +63(912)1084078", "(032)5203141 or 5203747"
    };

    private int[] mCoverPhoto = {
            R.drawable.cover_makati, R.drawable.cover_marconi, R.drawable.cover_nta, R.drawable.cover_iloilo,
            R.drawable.cover_bacolod, R.drawable.cover_cebu, R.drawable.cover_davao, R.drawable.cover_cebu
    };

    private String[] mLocationURI = {
            "geo:14.553635, 121.003397?q=New+Simulator+Center+Of+The+Phils.+Inc)",
            "geo:14.554677, 121.001053?q=NEWSIM+MARCONI",
            "geo:13.840336, 121.473373?q=Newsim+Training+Academy,+Quezon+Eco+Tourism+Road,+Sariaya,+Calabarzon",
            "geo:10.721861, 122.558733?q=NEWSIM+ILOILO",
            "geo:10.6692003, 122.9503259?q=NEWSIM+BACOLOD",
            "geo:10.316989, 123.892501?q=NEWSIM+CEBU",
            "geo:7.057441, 125.600810?q=NEWSIM+DAVAO",
            "geo:10.3169110, 123.8938355?q=10.3169110, 123.8938355(NEWSIM CEBU Annex)&z=18"
    };

    public List<Branch> getBranches(){
        List<Branch> branches = new ArrayList<>();
        for (int i = 0; i < mBranchName.length; i++){
            Branch branch = new Branch(mBranchName[i], mAddress[i], mTelephoneNo[i], mCoverPhoto[i], mLocationURI[i]);

            branches.add(branch);
        }

        return branches;
    }

    public Branch getBranchById(int branchId){
        switch (branchId){
            case 1:
                return new Branch(mBranchName[4], mAddress[4], mTelephoneNo[4], mCoverPhoto[4], mLocationURI[4]);
            case 2:
                return new Branch(mBranchName[5], mAddress[5], mTelephoneNo[5], mCoverPhoto[5], mLocationURI[5]);
            case 3:
                return new Branch(mBranchName[6], mAddress[6], mTelephoneNo[6], mCoverPhoto[6], mLocationURI[6]);
            case 4:
                return new Branch(mBranchName[3], mAddress[3], mTelephoneNo[3], mCoverPhoto[3], mLocationURI[3]);
            case 5:
                return new Branch(mBranchName[1], mAddress[1], mTelephoneNo[1], mCoverPhoto[1], mLocationURI[1]);
            default:
                return new Branch(mBranchName[1], mAddress[1], mTelephoneNo[1], mCoverPhoto[1], mLocationURI[1]);
        }
    }

    public Branch getBranchByCode(String branch){
        if (branch.equals("BACOLOD")){
            return new Branch(mBranchName[4], mAddress[4], mTelephoneNo[4], mCoverPhoto[4], mLocationURI[4]);
        }else if (branch.equals("CEBU")){
            return new Branch(mBranchName[5], mAddress[5], mTelephoneNo[5], mCoverPhoto[5], mLocationURI[5]);
        }else if (branch.equals("DAVAO")){
            return new Branch(mBranchName[6], mAddress[6], mTelephoneNo[6], mCoverPhoto[6], mLocationURI[6]);
        }else if (branch.equals("ILO-ILO")){
            return new Branch(mBranchName[3], mAddress[3], mTelephoneNo[3], mCoverPhoto[3], mLocationURI[3]);
        }else if (branch.equals("MAKATI")){
            return new Branch(mBranchName[1], mAddress[1], mTelephoneNo[1], mCoverPhoto[1], mLocationURI[1]);
        }else{
            return new Branch(mBranchName[1], mAddress[1], mTelephoneNo[1], mCoverPhoto[1], mLocationURI[1]);
        }
    }

    public static String getBranchCode(int branchId){
        switch (branchId){
            case 1:
                return "BACOLOD";
            case 2:
                return "CEBU";
            case 3:
                return "DAVAO";
            case 4:
                return "ILO-ILO";
            case 5:
                return "MAKATI";
            default:
                return "";
        }
    }
}
