//package org.latlonproject.printsim;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class QueueTest {
//
//    private Queue testQueue;
//    private Job testJob;
//
//
//    @Before
//    public void setUp() {
//        testQueue = new Queue(1, PaperColor.WHITE, InkColor.BLACK);
//        do {
//            testJob = new Job();
//        } while(!(testJob.getPaperColor().equals(testQueue.getPaperColor())
//                    && testJob.getInkColor().equals(testQueue.getInkColor())));
//        testQueue.enqueue(testJob);
//    }
//
//    @Test
//    public void testConstructor() {
//        assertTrue(testQueue.getQueueID() == 1);
//        assertTrue(testQueue.getPaperColor().equals("White"));
//        assertTrue(testQueue.getInkColor().equals("Black"));
//    }
//
//    @Test
//    public void testEnqueue() {
//        assertTrue(testQueue.peek(0).getInkColor().equals(testQueue.getInkColor()));
//        assertTrue(testQueue.peek(0).getPaperColor().equals(testQueue.getPaperColor()));
//    }
//
//    @Test
//    public void testDequeue() {
//        Job k;
//        k = testQueue.dequeue();
//        assertTrue(testQueue.getInkColor().equals(k.getInkColor()));
//        assertTrue(testQueue.getPaperColor().equals(k.getPaperColor()));
//    }
//
//    @Test
//    public void testUpdate() {
//        testQueue.update();
//        assertFalse(testQueue.isAttached());
//        for(int i = 0; i < testQueue.getSize(); i++) {
//            assertTrue(testQueue.peek(i).getIdleTime() > 0);
//        }
//    }
//
//    @Test
//    public void testAttach() {
//        assertTrue(testQueue.canSymlink());
//        testQueue.setAttached();
//        assertTrue(testQueue.isAttached());
//        assertFalse(testQueue.isSoftlinked());
//    }
//
//    @Test
//    public void testDetach() {
//        testQueue.setAttached();
//        assertTrue(testQueue.isAttached());
//        testQueue.detach();
//        assertFalse(testQueue.isAttached());
//        assertFalse(testQueue.isSoftlinked());
//    }
//}
