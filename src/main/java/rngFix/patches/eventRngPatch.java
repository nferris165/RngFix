package rngFix.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EventRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class eventRngPatch {

    @SpirePatch(
            clz = EventRoom.class,
            method = "onPlayerEntry"
    )

    public static class eventPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"eventRngDuplicate"}
        )
        public static void Insert(EventRoom __instance, @ByRef Random[] eventRngDuplicate){
            Random gen = new Random(Settings.seed, 1);
            eventRngDuplicate[0] = new Random(gen.random.nextLong(), AbstractDungeon.eventRng.counter);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(EventRoom.class, "event");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
