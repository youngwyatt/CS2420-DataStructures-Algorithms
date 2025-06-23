package assign02;

import java.util.ArrayList;

/**
 * This class applies to university students who are also 2420 students.
 * Thus includes an email as well as lists of scores for each element of the class.
 *
 * @author Wyatt Young and Brian Keller
 * @version May 20, 2024
 */
public class CS2420Student extends UofUStudent
{
    // private instance variables
    private EmailAddress contactInfo;
    private ArrayList<Double> assignmentScores;
    private ArrayList<Double> examScores;
    private ArrayList<Double> labScores;
    private ArrayList<Double> quizScores;
    /**
     * Constructor for CS2420 class; creates a 2420 student from name, uID, and an email address
     * @param firstName
     * @param lastName
     * @param uNID
     * @param contactInfo
     */
    public CS2420Student(String firstName, String lastName, int uNID, EmailAddress contactInfo)
    {
        //initialize using super constructor from UofUStudent class
        super(firstName, lastName, uNID);
        this.contactInfo = contactInfo;
        //initialize ArrayLists for each scoring category
        this.assignmentScores = new ArrayList<>();
        this.examScores = new ArrayList<>();
        this.labScores = new ArrayList<>();
        this.quizScores = new ArrayList<>();

    }
    /**
     * retrieves a 2420 students email info
     * @return email
     */
    public EmailAddress getContactInfo()
    {
        return this.contactInfo;
    }
    /**
     * adds a score to a 2420 students scores
     * @param score
     * @param category
     */
    public void addScore(double score, String category)
    {
        //switch case to add score dynamically
        switch(category)
        {
            case "assignment":
                this.assignmentScores.add(score);
                break;
            case "exam":
                this.examScores.add(score);
                break;
            case "lab":
                this.labScores.add(score);
                break;
            case "quiz":
                this.quizScores.add(score);
                break;
            default:
                //category string doesn't match designated categories in constructor
                throw new IllegalArgumentException("Category must be exactly one of: assignment, quiz, lab, or exam");
        }
    }
    /**
     * computes and returns a students final score on a 2420 graded element
     * @return finalScore
     */
    public double computeFinalScore()
    {
        double examAvg;
        double quizAvg;
        double assignmentAvg;
        double labAvg;
        double sum = 0;
        double finalScore;
        // return 0.0 if one category is empty
        if(examScores.isEmpty() || labScores.isEmpty() || quizScores.isEmpty() || assignmentScores.isEmpty())
        {
            return 0.0;
        }
        // average of each categories arraylist
        for(double num : examScores)
        {
            sum += num;
        }
        examAvg = sum / examScores.size();
        sum = 0;
        for(double num : quizScores)
        {
            sum += num;
        }
        quizAvg = sum / quizScores.size();
        sum = 0;
        for(double num : assignmentScores)
        {
            sum += num;
        }
        assignmentAvg = sum / assignmentScores.size();
        sum = 0;
        for(double num : labScores)
        {
            sum += num;
        }
        labAvg = sum / labScores.size();
        // syllabus; assignm. 35%, exams 45%, labs 10%, quizzes 10% (drop lowest quiz grade)
        finalScore = (examAvg*0.45) + (quizAvg * 0.1) + (labAvg * 0.1) + (assignmentAvg * 0.35);
        return finalScore;
    }
    /**
     * computes and returns a students final grade in the 2420 class
     * @return finalGrade
     */
    public String computeFinalGrade()
    {
        // return N/A if one category is empty
        if(examScores.isEmpty() || labScores.isEmpty() || quizScores.isEmpty() || assignmentScores.isEmpty())
        {
            return "N/A";
        }
        // Call final score method to get double value for final grade
        double finalScore = computeFinalScore();
        // Determine the letter grade based on the final score
        if (finalScore >= 93) {
            return "A";
        } else if (finalScore >= 90) {
            return "A-";
        } else if (finalScore >= 87) {
            return "B+";
        } else if (finalScore >= 83) {
            return "B";
        } else if (finalScore >= 80) {
            return "B-";
        } else if (finalScore >= 77) {
            return "C+";
        } else if (finalScore >= 73) {
            return "C";
        } else if (finalScore >= 70) {
            return "C-";
        } else if (finalScore >= 67) {
            return "D+";
        } else if (finalScore >= 63) {
            return "D";
        } else if (finalScore >= 60) {
            return "D-";
        } else {
            return "E";
        }
    }
}
