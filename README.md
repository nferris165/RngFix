# Random Number Generator Fix

This mod was made after reading this essay on the randomness in Slay The Spire:

https://forgottenarbiter.github.io/Correlated-Randomness/

In summary:

"When you start a game of Slay the Spire, you are assigned (or choose) a 64-bit seed. This seed is used to initialize several random number generators, each in charge of a different aspect of the game. But many of these random number generators are initialized to the same state."

What this means is that the player can know a lot of information they shouldn't if the game were truly random. One such example is the player can know if their next event will be a monster room or not based on the first enemy they encounter. Another example is that by analyzing monster order (or the potion order, or the event order, etc), the player can predetermine the rarity of the chest they receive and its contents by calculating the correct number of fights before entering the chest room.

## THIS MOD IS ONLY FOR A VERY NICHE SUBSET OF PLAYERS

Most players will not bother to consider this information when playing, and that's fine. A small subset of players will be aware of these, and subconsciously or not, let that influence their play.

Fundamentally, the seeds of each of the major categories should not be the same. This mod alleviates that issue by using the map seed to generate a unique subset of seeds for each of the major RNG categories of the game. Each seed will still be the same for every player on that seed, but there is no longer any way to use the order of monster fights to predict any information about potion likelihood, or chest rarity.


## Plea to the Devs

I believe that this change should be part of the main game, but until this is fixed, this mod will suffice. I will end by reiterating the final message of the blog post:

"If [the devs] happen to see this... I would like to please request a fix for this bug. Luckily, the opportunities for exploiting the interaction between the different random number generators seem to be fairly limited, based on what I currently believe. However, I believe players should never be incentivized to exploit the predictability of rng in order to play optimally. Maximizing the benefit from this predictability requires intimate knowledge of implementation details of the game, and in my opinion, is tedious and unnatural. Even just having the knowledge in this post feels to me like an exploit that is always accidentally active. While I and many others will continue to play, consciously ignoring the existence of the bug, it would at least grant significant peace of mind to know that independent events are truly independent. I know I have reported many bugs for the game, but this is the one that I would currently most like to see fixed."
