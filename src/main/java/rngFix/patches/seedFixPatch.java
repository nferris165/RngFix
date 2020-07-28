package rngFix.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import rngFix.RngFix;


public class seedFixPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateSeeds"
    )

    public static class generatePatch {
        public static void Postfix(){
            RngFix.logger.info("old seeds:\n");

            RngFix.logger.info(AbstractDungeon.monsterRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.eventRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.merchantRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.cardRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.treasureRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.relicRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.potionRng.random() + "\n");

            Random gen = new Random(Settings.seed);

            AbstractDungeon.monsterRng = new Random(gen.random.nextLong());
            AbstractDungeon.eventRng = new Random(gen.random.nextLong());
            AbstractDungeon.merchantRng = new Random(gen.random.nextLong());
            AbstractDungeon.cardRng = new Random(gen.random.nextLong());
            AbstractDungeon.treasureRng = new Random(gen.random.nextLong());
            AbstractDungeon.relicRng = new Random(gen.random.nextLong());
            AbstractDungeon.potionRng = new Random(gen.random.nextLong());

            RngFix.logger.info("new seeds:\n");

            RngFix.logger.info(AbstractDungeon.monsterRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.eventRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.merchantRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.cardRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.treasureRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.relicRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.potionRng.random() + "\n");

        }
    }
}
