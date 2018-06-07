package ph.newsim.mobile.newsim.database;

import java.util.ArrayList;
import java.util.List;

import ph.newsim.mobile.newsim.model.Branch;
import ph.newsim.mobile.newsim.model.Rank;

public class RankDataSource {

    private int[] mId = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 37, 39, 40,
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 74, 75};

    private String[] mRankCode = {"1/E", "1AE", "2/E", "2/M", "3/E", "3/M", "4/E", "A/B", "Bosun", "C/E", "C/M", "Capt.", "CCK", "CSO", "D/C", "DR", "E/C", "Elect.",
    "Engr.", "ER", "FOM", "FTR", "MACH", "Mjr Patron", "MM", "MSM", "O/S", "OIC", "OLR", "OTH", "RO", "STM", "WPR", "OIC-EW", "CK", "OIC-NW", "STWD", "2AE", "AB", "MTRMN",
    "1ST COOK", "STWRD", "HW", "PMP", "HKM", "SSK", "FRM", "SSA", "P", "WAITER", "MT", "CC", "GSA", "BCR", "SC", "CD", "SPA M", "GP/E", "AE", "3AE", "1A/E", "CD2", "GPT",
    "WD", "AG", "BC", "SK", "WTRS", "ARE", "AM", "RN", "Dr."};

    private String[] mRankDescription = {"First Engineer", "First Asst. Engineer", "Second Engineer", "Second Mate", "Third Engineer", "Third Mate", "Fourth Engineer", "Able Seaman",
    "Bosun", "Chief Engineer", "Chief Mate", "Captain", "Chief Cook", "Company Security Officer", "Deck Cadet", "Deck Rating", "Engine Cadet", "Electrician", "Engineer", "Engine Rating",
    "Fleet Operation Manager", "Fitter", "Machinist", "Major Patron", "Master Mariner", "Messman", "Ordinary Seaman", "Officer In Charge", "Oiler", "N/A", "Radio Operator", "Safety Training Manager",
    "Wiper", "Officer In Charge In EW", "Cook", "Officer In Charge In NW", "Steward", "Second Assistant Engineer", "Able Seaman", "Motorman", "First Cook", "Steward", "Head Waitress", "Pump Man",
    "House Keeping Manager", "Senior Store Keeper", "Fireman", "State Room Service", "Plumber", "Waiter", "Mechanical Technician", "Cage Cashier", "General Staff Apprentice", "Butcher", "Service Crew",
    "Casino Dealer", "Spa Masseuse", "General Purpose Engine", "Apprentice Engineer", "Third Assistant Engineer", "First Apprentice Engineer", "Crane Driver", "General Purpose Technician", "Welder",
    "Arm Guard", "Boat Captain", "Store Keeper", "Waitress", "Assistant Reefer Engineer", "Apprentice Mate", "Registered Nurse", "Doctor"};

    private String[] mRankType = {"E", "E", "E", "D", "E", "D", "E", "D", "C", "E", "D", "D", "D", "C", "D", "D", "E", "E", "E", "E", "C", "E", "E", "D", "D", "C", "D", "C", "E", "C", "C", "C", "E",
    "E", "D", "D", "C", "E", "C", "E", "C", "C", "C", "D", "C", "C", "D", "E", "E", "C", "E", "C", "C", "C", "C", "C", "C", "E", "E", "E", "E", "C", "C", "C", "C", "D", "C", "C", "E", "D", "RN", "DR"};

    private String[] mRankLevel = {"Management", "Management", "Management", "Management", "Operational", "Operational", "Operational", "Operational", "Operational", "Management", "Management", "Management",
            "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Management", "Operational", "Management", "Operational", "Operational", "Management", "Management",
    "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Management", "Operational",
            "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Management", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational",
            "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Operational", "Management",
            "Operational", "Operational", "Operational", "Operational", "Operational", "Management"};

    public List<Rank> getRanks(){
        List<Rank> ranks = new ArrayList<>();
        for (int i =0; i < mId.length; i++){
            Rank rank = new Rank(mId[i], mRankCode[i], mRankDescription[i], mRankType[i], mRankLevel[i]);
            ranks.add(rank);
        }
        return ranks;
    }

    public String[] getRankCode() {
        return mRankCode;
    }

    public String[] getRankDescription() {
        return mRankDescription;
    }
}
