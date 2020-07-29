package rngFix.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.random.Random;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class neowPatch {
    @SpirePatch(
            clz = NeowEvent.class,
            method = "blessing"
    )

    public static class blessingPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(NeowEvent __instance){
            Random gen = new Random(Settings.seed, 13);
            NeowEvent.rng = new Random(gen.random.nextLong());
        }
    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "dailyBlessing"
    )

    public static class dailyPatch {
        @SpireInsertPatch(
                locator = Locator2.class
        )
        public static void Insert(NeowEvent __instance){
            Random gen = new Random(Settings.seed, 13);
            NeowEvent.rng = new Random(gen.random.nextLong());
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "bossCount");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    private static class Locator2 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(NeowEvent.class, "dismissBubble");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }


}
