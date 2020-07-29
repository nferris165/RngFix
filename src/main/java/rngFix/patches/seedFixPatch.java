package rngFix.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import rngFix.RngFix;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;


public class seedFixPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateSeeds"
    )

    public static class generatePatch {
        public static void Postfix(){

            RngFix.logger.info("Generating unique seeds...");
            Random gen = new Random(Settings.seed);


            monsterRng = new Random(gen.random.nextLong());     // 0
            eventRng = new Random(gen.random.nextLong());       // 1
            merchantRng = new Random(gen.random.nextLong());    // 2
            cardRng = new Random(gen.random.nextLong());        // 3
            treasureRng = new Random(gen.random.nextLong());    // 4
            relicRng = new Random(gen.random.nextLong());       // 5
            potionRng = new Random(gen.random.nextLong());      // 6

            monsterHpRng = new Random(gen.random.nextLong());   // 7
            aiRng = new Random(gen.random.nextLong());          // 8
            shuffleRng = new Random(gen.random.nextLong());     // 9
            cardRandomRng = new Random(gen.random.nextLong());  // 10
            miscRng = new Random(gen.random.nextLong());        // 11


//            RngFix.logger.info("new seeds:\n");
//
//            RngFix.logger.info(monsterRng.random.seed0 + "\n");
//            RngFix.logger.info(AbstractDungeon.eventRng.random() + "\n");
//            RngFix.logger.info(AbstractDungeon.merchantRng.random() + "\n");
//            RngFix.logger.info(AbstractDungeon.cardRng.random() + "\n");
//            RngFix.logger.info(AbstractDungeon.treasureRng.random() + "\n");
//            RngFix.logger.info(AbstractDungeon.relicRng.random() + "\n");
//            RngFix.logger.info(AbstractDungeon.potionRng.random() + "\n");

        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "loadSeeds"
    )

    public static class loadPatch {
        public static void Postfix(SaveFile save){

            RngFix.logger.info("Generating unique seeds...");
            Random gen = new Random(Settings.seed);

            monsterRng = new Random(gen.random.nextLong(), save.monster_seed_count);
            eventRng = new Random(gen.random.nextLong(), save.event_seed_count);
            merchantRng = new Random(gen.random.nextLong(), save.merchant_seed_count);
            cardRng = new Random(gen.random.nextLong(), save.card_seed_count);
            treasureRng = new Random(gen.random.nextLong(), save.treasure_seed_count);
            relicRng = new Random(gen.random.nextLong(), save.relic_seed_count);
            potionRng = new Random(gen.random.nextLong(), save.potion_seed_count);
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransition",
            paramtypez = {
                    SaveFile.class
            }
    )

    public static class roomTransPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractDungeon __instance, SaveFile save){

            Random gen = new Random(Settings.seed);

            //to queue to the correct seed count
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            //to queue to the correct seed count

            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong() + (long)floorNum);
            AbstractDungeon.aiRng = new Random(gen.random.nextLong() + (long)floorNum);
            AbstractDungeon.shuffleRng = new Random(gen.random.nextLong() + (long)floorNum);
            AbstractDungeon.cardRandomRng = new Random(gen.random.nextLong() + (long)floorNum);
            AbstractDungeon.miscRng = new Random(gen.random.nextLong() + (long)floorNum);
        }

        @SpireInsertPatch(
                locator = Locator2.class,
                localvars = {"eventRngDuplicate"}
        )
        public static void Insert2(AbstractDungeon __instance, SaveFile save, @ByRef Random[] eventRngDuplicate){

            Random gen = new Random(Settings.seed, 1);
            eventRngDuplicate[0] = new Random(gen.random.nextLong(), eventRng.counter);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(SaveFile.class, "post_combat");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    private static class Locator2 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(EventHelper.class, "roll");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
