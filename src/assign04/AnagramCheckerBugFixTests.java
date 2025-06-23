package assign04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnagramCheckerBugFixTests {

    @Test
    void getLargestAnagramGroupSameWords() {
        String[] test = new String[] {"Poop", "Poop", "Poop","Poop","Poop","Poop","Poop","Poop","Poop","Poop"};
        assertEquals(10, AnagramChecker.getLargestAnagramGroup(test).length);
    }

    @Test
    void getLargestAnagramGroupTwoGroupsCaseDependent(){
        String[] test = new String[] {"MachinE", "MachinE","MachinE","MachinE","MachinE","LearNing","LearNing","LearNing","LearNing","LearNing","LearNing"};
        assertEquals(6, AnagramChecker.getLargestAnagramGroup(test).length);

    }

    @Test
    void getLargestAnagramGroupTwoGroupsNonCaseDependent(){
        String[] test = new String[] {"artificial","artificial","artificial","artificial","artificial","intelligence","intelligence","intelligence","intelligence","intelligence","intelligence"};
        assertEquals(6, AnagramChecker.getLargestAnagramGroup(test).length);
    }
}