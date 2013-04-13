package printsim.factory;

import org.junit.Test;
import printsim.Clock;
import printsim.InkColor;
import printsim.PaperColor;
import printsim.Queue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class QueueFactoryTest {

    private Set<InkColor> inkColors = new HashSet<>();
    private Set<PaperColor> paperColors = new HashSet<>();

    @Test
    public void createQueues_createsAllCombinations() {
        //given
        generateColorSets();

        //when
        Queue[] result = printsim.factory.QueueFactory.createQueues();

        //then
        int i = 1;
        assertTrue(result.length == Clock.maxQueues());
        for (Queue q : result) {
            assertTrue("Color generated not in the ink color set",
                    inkColors.contains(q.getInkColor()));
            assertTrue("Color generated not in the paper color set",
                    paperColors.contains(q.getPaperColor()));
            assertTrue("IDs aren't generating properly!", q.getQueueID() == i++);
        }
    }

    private void generateColorSets() {
        inkColors.addAll(Arrays.asList(InkColor.values()));
        paperColors.addAll(Arrays.asList(PaperColor.values()));
    }

    @Test
    public void createQueues_createsUniqueQueueArray() {
        //given
        generateColorSets();

        //when
        Queue[] result1 = printsim.factory.QueueFactory.createQueues();
        Queue[] result2 = printsim.factory.QueueFactory.createQueues();

        //then
        assertSame(result2, result1);
    }
}
