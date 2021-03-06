package test.com.harrycodeman.compression.colorspaces;

import com.harrycodeman.compression.colorspaces.BuildHistogramImageProcessingStage;
import com.harrycodeman.compression.colorspaces.Image;
import com.harrycodeman.compression.colorspaces.ThreeComponentPixelBlock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.nCopies;
import static junit.framework.Assert.assertEquals;

public class BuildHistogramImageProcessingStageTest {
    // TODO: refactoring needs
    @Test
    public void testExecuteFor() throws Exception {
        assertEquals(
                new Image(259, 100, getBlocksForExecuteFor()),
                new BuildHistogramImageProcessingStage().executeFor(
                        new Image(2, 3,
                                new ThreeComponentPixelBlock(0, 0, 0),
                                new ThreeComponentPixelBlock(254, 0, 0),
                                new ThreeComponentPixelBlock(0, 0, 0),
                                new ThreeComponentPixelBlock(0, 0, 0),
                                new ThreeComponentPixelBlock(254, 0, 0),
                                new ThreeComponentPixelBlock(254, 0, 0)
                        )
                )
        );
    }

    private Collection<ThreeComponentPixelBlock> getBlocksForExecuteFor() {
        List<ThreeComponentPixelBlock> result = new ArrayList<ThreeComponentPixelBlock>(
                nCopies(25900, BuildHistogramImageProcessingStage.BACK_COLOR)
        );
        result.set(259*97, new ThreeComponentPixelBlock(0, 0, 0));
        result.set(259*97 + 1, new ThreeComponentPixelBlock(254, 0, 0));
        result.set(259*98, new ThreeComponentPixelBlock(0, 0, 0));
        result.set(259*98 + 1, new ThreeComponentPixelBlock(0, 0, 0));
        result.set(259*99, new ThreeComponentPixelBlock(254, 0, 0));
        result.set(259*99 + 1, new ThreeComponentPixelBlock(254, 0, 0));
        for (int i = 0; i < 100; i++) {
            result.set(i*259 + 3, BuildHistogramImageProcessingStage.FORE_COLOR);
            result.set(i*259 + 257, BuildHistogramImageProcessingStage.FORE_COLOR);
        }
        return result;
    }

    @Test
    public void testForPixelsInterdependency() throws Exception {
        Image histogram = new BuildHistogramImageProcessingStage().executeFor(
                new Image(3, 1,
                        new ThreeComponentPixelBlock(0, 0, 0),
                        new ThreeComponentPixelBlock(0, 0, 0),
                        new ThreeComponentPixelBlock(0, 0, 0)
                )
        );
        for (int i = 0; i < histogram.size(); i++) {
            if (i%2 == 0) {
                histogram.set(i, new ThreeComponentPixelBlock(0, 0, 0));
            }
            else {
                histogram.set(i, new ThreeComponentPixelBlock(255, 255, 255));
            }
        }
        assertEquals(
                new Image(260, 100, getBlocksForPixelsInterdependency()),
                histogram
        );
    }

    private List<ThreeComponentPixelBlock> getBlocksForPixelsInterdependency() {
        List<ThreeComponentPixelBlock> result = new ArrayList<ThreeComponentPixelBlock>(26000);
        for (int i = 0; i < 26000; i++) {
            if (i%2 == 0) {
                result.add(new ThreeComponentPixelBlock(0, 0, 0));
            }
            else {
                result.add(new ThreeComponentPixelBlock(255, 255, 255));
            }
        }
        return result;
    }
}
