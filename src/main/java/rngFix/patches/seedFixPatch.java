package rngFix.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import rngFix.RngFix;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.floorNum;


public class seedFixPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateSeeds"
    )

    public static class generatePatch {
        public static void Postfix(){

            RngFix.logger.info("Generating unique seeds..."); // seed: 7 proves uniqueness
            Random gen = new Random(Settings.seed);

            AbstractDungeon.monsterRng = new Random(gen.random.nextLong());
            AbstractDungeon.eventRng = new Random(gen.random.nextLong());
            AbstractDungeon.merchantRng = new Random(gen.random.nextLong());
            AbstractDungeon.cardRng = new Random(gen.random.nextLong());
            AbstractDungeon.treasureRng = new Random(gen.random.nextLong());
            AbstractDungeon.relicRng = new Random(gen.random.nextLong());
            AbstractDungeon.potionRng = new Random(gen.random.nextLong());

            AbstractDungeon.monsterHpRng = new Random(gen.random.nextLong());
            AbstractDungeon.aiRng = new Random(gen.random.nextLong());
            AbstractDungeon.shuffleRng = new Random(gen.random.nextLong());
            AbstractDungeon.cardRandomRng = new Random(gen.random.nextLong());
            AbstractDungeon.miscRng = new Random(gen.random.nextLong());

            /*
            RngFix.logger.info("new seeds:\n");

            RngFix.logger.info(AbstractDungeon.monsterRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.eventRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.merchantRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.cardRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.treasureRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.relicRng.random() + "\n");
            RngFix.logger.info(AbstractDungeon.potionRng.random() + "\n");
            */
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

            AbstractDungeon.monsterRng = new Random(gen.random.nextLong(), save.monster_seed_count);
            AbstractDungeon.eventRng = new Random(gen.random.nextLong(), save.event_seed_count);
            AbstractDungeon.merchantRng = new Random(gen.random.nextLong(), save.merchant_seed_count);
            AbstractDungeon.cardRng = new Random(gen.random.nextLong(), save.card_random_seed_count);
            AbstractDungeon.treasureRng = new Random(gen.random.nextLong(), save.treasure_seed_count);
            AbstractDungeon.relicRng = new Random(gen.random.nextLong(), save.relic_seed_count);
            AbstractDungeon.potionRng = new Random(gen.random.nextLong(), save.potion_seed_count);
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
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(SaveFile.class, "post_combat");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
