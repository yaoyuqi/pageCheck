package Ywk.PageChecker;

import Ywk.Data.Keyword.DoubleCombineGenerator;
import Ywk.Data.Keyword.SingleGenerator;
import Ywk.Data.Keyword.TripleCombineGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class TestGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TestGenerator.class);

    @Test
    public void testSingle() {
        var arr1 = new String[]{"main1", "main2", "main3", "main4"};

        var generator = new SingleGenerator(arr1);
        assertEquals(4, generator.count());

        while (generator.hasNext()) {
            logger.debug("{}", generator.next());
        }


    }

    @Test
    public void testDouble() {
        var arr1 = new String[]{"main1", "main2", "main3", "main4"};
        var arr2 = new String[]{"pref1", "pref2", "pref3"};

        var generator = new DoubleCombineGenerator(arr2, arr1);

        assertEquals(12, generator.count());

        while (generator.hasNext()) {
            logger.debug("{}", generator.next());
        }

    }

    @Test
    public void testTriple() {
        var arr1 = new String[]{"main1", "main2", "main3", "main4"};
        var arr2 = new String[]{"pref1", "pref2", "pref3"};
        var arr3 = new String[]{"suf1", "suf2"};

        var generator = new TripleCombineGenerator(arr2, arr1, arr3);

        assertEquals(24, generator.count());

        while (generator.hasNext()) {
            logger.debug("{}", generator.next());
        }

    }
}
