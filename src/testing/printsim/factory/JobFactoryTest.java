package printsim.factory;

import org.junit.Test;
import printsim.Clock;
import printsim.InkColor;
import printsim.Job;
import printsim.factory.JobFactory;
import printsim.PaperColor;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class JobFactoryTest {

    @Test
    public void generateJobs_unbiased() {
        //given
        Clock c = new Clock();
        boolean biased = false;

        //when
        Job[] jobs = JobFactory.generateJobs(c, biased);

        //then
        assertTrue("No jobs generated?  You sure about that?", 1 <= jobs.length);
        assertTrue("ID isn't generated properly",
                jobs[0].getJobNumber() == 1);
        for(Job j : jobs) {
            System.out.println(j);
        }
    }

    @Test
    public void generateJobs_biased() {
        //given
        Clock c = new Clock();
        boolean biased = true;

        //when
        Job[] jobs = JobFactory.generateJobs(c, biased);

        //then
        assertTrue("No jobs generated?  You sure about that?", 1 <= jobs.length);
        assertTrue("ID isn't generated properly",
                jobs[0].getJobNumber() == 1);
        for(Job j : jobs) {
            System.out.println(j);
        }
    }

    @Test
    public void generateJobs_generateALotOfJobsBiased() {
        //given
        Clock c = new Clock(1000, 1, 1);
        boolean biased = true;

        //when
        Job[] jobs = JobFactory.generateJobs(c, biased);
        Set<InkColor> generatedJobInkColors = new HashSet<>();
        Set<PaperColor> generatedJobPaperColors = new HashSet<>();

        for(Job j : jobs) {
            generatedJobInkColors.add(j.getInkColor());
            generatedJobPaperColors.add(j.getPaperColor());
        }

        //then
        assertTrue("No jobs generated?  You sure about that?", 1 <= jobs.length);
        assertTrue("ID isn't generated properly",
                          jobs[0].getJobNumber() == 1);
        assertTrue(generatedJobInkColors.size() == InkColor.values().length);
        assertTrue(generatedJobPaperColors.size() == PaperColor.values()
                                                             .length);
    }

    @Test
    public void generateJobs_generateALotOfJobsUnbiased() {
        //given
        Clock c = new Clock(1000, 1, 1);
        boolean biased = false;

        //when
        Job[] jobs = JobFactory.generateJobs(c, biased);
        Set<InkColor> generatedJobInkColors = new HashSet<>();
        Set<PaperColor> generatedJobPaperColors = new HashSet<>();

        for(Job j : jobs) {
            generatedJobInkColors.add(j.getInkColor());
            generatedJobPaperColors.add(j.getPaperColor());
        }

        //then
        assertTrue("No jobs generated?  You sure about that?", 1 <= jobs.length);
        assertTrue("ID isn't generated properly",
                          jobs[0].getJobNumber() == 1);
        assertTrue(generatedJobInkColors.size() == InkColor.values().length);
        assertTrue(generatedJobPaperColors.size() == PaperColor.values()
                                                             .length);
    }
}
