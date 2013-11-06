package org.latlonproject.printsim;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobTest {

    @Test
    public void createJobWithUpdateTick_vanilla() {
        //given

        //when
        Job testObj = new Job(1, 1, 1, PaperColor.WHITE, InkColor.BLACK);
        testObj.update();

        //then
        assertFalse(testObj.isQueued());
        assertTrue(0 == testObj.getIdleTime());
        assertFalse(testObj.isPrinting());
        assertTrue(0 == testObj.getQueueNumber());
    }

    @Test
    public void createJobWithUpdateTick_queuedShouldUpdateIdle() {
        //given
        Job testObj = new Job(1, 1, 1, PaperColor.WHITE, InkColor.BLACK);
        testObj.setQueued();
        testObj.update();

        //when

        //then
        assertTrue(1 == testObj.getIdleTime());
        assertFalse(testObj.isPrinting());
        assertTrue(0 == testObj.getQueueNumber());
        assertTrue(3 == testObj.getDepartureTime());
        assertTrue(testObj.canPrint());
    }

}
